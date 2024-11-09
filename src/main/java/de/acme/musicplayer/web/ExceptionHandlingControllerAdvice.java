package de.acme.musicplayer.web;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(Exception.class)
    public HtmxResponse handleError(Exception ex) {
        return HtmxResponse.builder()
                .retarget("#toast-container")
                .reswap(HtmxReswap.innerHtml())
                .view(new ModelAndView("htmx-responses/error-toast.html", Map.of("message", ex.getMessage())))
                .build();
    }
}
