package de.acme.musicplayer.applications.users.adapters.web;

import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final BenutzerRegistrierenUsecase benutzerRegistrierenUsecase;

    public UserController(BenutzerRegistrierenUsecase benutzerRegistrierenUsecase) {
        this.benutzerRegistrierenUsecase = benutzerRegistrierenUsecase;
    }

    @HxRequest
    @GetMapping("/registration-form")
    public String register(Model model) {
        model.addAttribute("greeting", "Hello World!");
        return "htmx-responses/user-registration-form.html";
    }

    @HxRequest
    @PostMapping("/register-user")
    public String registerUser(Model model, HttpServletResponse response, String username, String email, String password, @CookieValue(value = "tenantId") String tenantId) {
        Benutzer.Id id = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(
                new Benutzer.Name(username),
                new Benutzer.Passwort(password),
                new Benutzer.Email(email),
                new TenantId(tenantId)));

        Cookie userId = new Cookie("userId", id.Id());
        userId.setHttpOnly(true);
        response.addCookie(userId);

        model.addAttribute("userId", id.Id());
        model.addAttribute("userName", username);
        return "htmx-responses/user-registration-successfull-toast.html";
    }

}
