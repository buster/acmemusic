package de.acme.musicplayer.applications.musicplayer.adapters.web;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiederAuflistenUsecase;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

@Controller
public class MusicPlayerController {

    private final LiedAbspielenUsecase liedAbspielenUseCase;

    private final LiedHochladenUsecase liedHochladenUseCase;
    private final LiederAuflistenUsecase liederAuflistenUseCase;

    public MusicPlayerController(LiedAbspielenUsecase liedAbspielenUseCase, LiedHochladenUsecase liedHochladenUseCase, LiederAuflistenUsecase liederAuflistenUseCase) {
        this.liedAbspielenUseCase = liedAbspielenUseCase;
        this.liedHochladenUseCase = liedHochladenUseCase;
        this.liederAuflistenUseCase = liederAuflistenUseCase;
    }

    @GetMapping(value = "/streamSong", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> liedAbspielen(@RequestParam(required = false) String benutzerId, @RequestParam(required = false) String liedId, @RequestParam(required = false)String tenantId) throws IOException {
        InputStream inputStream = liedAbspielenUseCase.liedStreamen(new Lied.Id(liedId), new TenantId(tenantId));
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @HxRequest
    @PostMapping("/uploadSong")
    public HtmxResponse uploadSong(Model model, @CookieValue(name = "userId") String userId, String titel, @RequestParam("file") MultipartFile file, String tenantId) throws IOException {
        Lied.Id id = liedHochladenUseCase.liedHochladen(new Benutzer.Id(userId), new Lied.Titel(titel), file.getInputStream(), new TenantId(tenantId));
        model.addAttribute("titel", titel);
        model.addAttribute("file", file.getOriginalFilename());
        model.addAttribute("songId", id.id());
        return HtmxResponse.builder().view("htmx-responses/song-upload-successfull-toast.html").build();
    }

    @HxRequest
    @GetMapping("/fileUploadForm")
    public String fileUploadForm(Model model) {
        return "htmx-responses/song-upload.html";
    }

    @HxRequest
    @GetMapping("/songlist")
    public String songList(Model model, @RequestParam("tenantId") String tenantId,  @CookieValue(value = "userId") String benutzerId) {
        Collection<Lied> lieder = liederAuflistenUseCase.liederAuflisten(new TenantId(tenantId), new Benutzer.Id(benutzerId));
        model.addAttribute("lieder", lieder);
        model.addAttribute("userId", benutzerId);
        model.addAttribute("tenantId", tenantId);

        return "htmx-responses/songlist.html";
    }

    @ExceptionHandler(Exception.class)
    public HtmxResponse handleError(Exception ex) {
        return HtmxResponse.builder()
                .retarget("#toast-container")
                .reswap(HtmxReswap.innerHtml())
                .view(new ModelAndView("htmx-responses/error-toast.html", Map.of("message", ex.getMessage())))
                .build();
    }
}
