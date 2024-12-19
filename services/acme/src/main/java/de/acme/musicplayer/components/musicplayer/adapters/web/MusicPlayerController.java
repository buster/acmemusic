package de.acme.musicplayer.components.musicplayer.adapters.web;

import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.components.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.components.musicplayer.usecases.LiederAuflistenUsecase;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxReselect;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

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
    public ResponseEntity<InputStreamResource> liedAbspielen(@RequestParam(required = false) String liedId, @CookieValue(value = "tenantId") String tenantId) {
        InputStream inputStream = liedAbspielenUseCase.liedStreamen(new LiedId(liedId), new TenantId(tenantId));
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @HxRequest
    @PostMapping("/uploadSong")
    public String uploadSong(Model model, @CookieValue(name = "userId") String userId, String titel, @RequestParam("file") MultipartFile file, @CookieValue(value = "tenantId") String tenantId) throws IOException {
        LiedId liedId = liedHochladenUseCase.liedHochladen(new BenutzerId(userId), new Lied.Titel(titel), file.getInputStream(), new TenantId(tenantId));
        model.addAttribute("titel", titel);
        model.addAttribute("file", file.getOriginalFilename());
        model.addAttribute("songId", liedId.id());
        return "htmx-responses/song-upload-successfull-toast.html";
    }

    @HxRequest
    @GetMapping("/fileUploadForm")
    public String fileUploadForm() {
        return "htmx-responses/song-upload.html";
    }

    // HxRequest nicht, damit das Fragment auch im Browser sichtbar wird
    @HxReselect("#songlist-content")
    @GetMapping("/songlist")
    public String songList(Model model, @CookieValue(value = "tenantId") String tenantId, @CookieValue(value = "userId") String benutzerId) {
        Collection<Lied> lieder = liederAuflistenUseCase.liederAuflisten(new TenantId(tenantId), new BenutzerId(benutzerId));
        model.addAttribute("lieder", lieder);
        model.addAttribute("userId", benutzerId);
        model.addAttribute("tenantId", tenantId);
        return "htmx-responses/songlist.html";
    }
}
