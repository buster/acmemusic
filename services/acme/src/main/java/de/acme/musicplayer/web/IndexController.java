package de.acme.musicplayer.web;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(@RequestParam(required = false, defaultValue = "WEB") String tenantId, HttpServletResponse response) {
        response.addCookie(new Cookie("tenantId", tenantId));
        return "index.html";
    }

    @HxRequest
    @GetMapping("/adminpage")
    public String adminpage() {
        return "htmx-responses/adminpage.html";
    }
}
