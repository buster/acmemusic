package de.acme.musicplayer.web;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.domain.model.Benutzer.Email;
import de.acme.musicplayer.applications.users.domain.model.Benutzer.Name;
import de.acme.musicplayer.applications.users.domain.model.Benutzer.Passwort;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiederAuflistenUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Controller
public class LiedAbspielenController {

    private final LiedAbspielenUsecase liedAbspielenUseCase;

    private final BenutzerRegistrierenUsecase benutzerRegistrierenUsecase;
    private final LiedHochladenUsecase liedHochladenUseCase;
    private final LiederAuflistenUsecase liederAuflistenUseCase;

    public LiedAbspielenController(LiedAbspielenUsecase liedAbspielenUseCase, BenutzerRegistrierenUsecase benutzerRegistrierenUsecase, LiedHochladenUsecase liedHochladenUseCase, LiederAuflistenUsecase liederAuflistenUseCase) {
        this.liedAbspielenUseCase = liedAbspielenUseCase;
        this.benutzerRegistrierenUsecase = benutzerRegistrierenUsecase;
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

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String tenantId) {
        model.addAttribute("greeting", "Hello World!");
        if (isNotBlank(tenantId)) {
            model.addAttribute("tenantId", tenantId);
        }
        return "index.html";
    }

    @HxRequest
    @GetMapping("/registration-form")
    public String register(Model model) {
        model.addAttribute("greeting", "Hello World!");
        return "htmx-responses/registration-form.html";
    }

    @HxRequest
    @PostMapping("/register-user")
    public String registerUser(Model model, String username, String email, String password, String tenantId) {
        Benutzer.Id id = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenCommand(
                new Name(username),
                new Passwort(password),
                new Email(email),
                new TenantId(tenantId)));
        model.addAttribute("userId", id.Id());
        model.addAttribute("userName", username);
        return "htmx-responses/user-registration-successfull-toast.html";
    }

    @HxRequest
    @PostMapping("/uploadSong")
    public String uploadSong(Model model, String titel, @RequestParam("file") MultipartFile file, String tenantId) throws IOException {
        Lied.Id id = liedHochladenUseCase.liedHochladen(new Lied.Titel(titel), file.getInputStream(), new TenantId(tenantId));
        model.addAttribute("titel", titel);
        model.addAttribute("file", file.getOriginalFilename());
        model.addAttribute("songId", id.id());
        return "htmx-responses/file-upload-successfull-toast.html";
    }

    @HxRequest
    @GetMapping("/fileUploadForm")
    public String fileUploadForm(Model model) {
        return "htmx-responses/file-upload.html";
    }

    @HxRequest
    @GetMapping("/songlist")
    public String songList(Model model, @RequestParam("tenantId") String tenantId,  @RequestParam(value = "benutzerId", required = false) String benutzerId) {
        Collection<Lied> lieder = liederAuflistenUseCase.liederAuflisten(new TenantId(tenantId));
        model.addAttribute("lieder", lieder);
        model.addAttribute("userId", benutzerId);
        model.addAttribute("tenantId", tenantId);

        return "htmx-responses/songlist.html";
    }
}
