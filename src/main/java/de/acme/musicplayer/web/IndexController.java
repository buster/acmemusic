package de.acme.musicplayer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String tenantId) {
        model.addAttribute("greeting", "Hello World!");
        if (isNotBlank(tenantId)) {
            model.addAttribute("tenantId", tenantId);
        }
        return "index.html";
    }
}
