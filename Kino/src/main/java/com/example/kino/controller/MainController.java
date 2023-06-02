package com.example.kino.controller;

import com.example.kino.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {
    public MainController() {
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        boolean isAuthorized = true;
        model.addAttribute("isAuthorized", isAuthorized);

        model.addAttribute("title", "главная страница");

        return "home";
    }

    @ModelAttribute("isAuthorized")
    public String isAuthorized(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        return user != null ? "/" : null;
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        return user != null && user.hasRole("ADMIN");
    }
}


