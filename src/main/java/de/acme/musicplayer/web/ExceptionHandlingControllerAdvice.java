package de.acme.musicplayer.web;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxReswap;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRetarget;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxSwapType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(Exception.class)
    @HxReswap(HxSwapType.INNER_HTML)
    @HxRetarget("#toast-container")
    public ModelAndView handleError(Exception ex) {
        return new ModelAndView("htmx-responses/error-toast.html", Map.of("message", ex.getMessage()));
    }
}
