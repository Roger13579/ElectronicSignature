package com.myl.electronicsignatureservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for web pages
 */
@Controller
public class WebController {

    /**
     * Redirect root URL to the index.html page
     * @return redirect to index.html
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }
}