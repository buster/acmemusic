package de.acme.musicplayer.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false, defaultValue = "WEB") String tenantId, HttpServletResponse response) {
        response.addCookie(new Cookie("tenantId", tenantId));
        return "index.html";
    }
}
