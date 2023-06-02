package com.example.kino.controller;

import com.example.kino.dto.UserDto;
import com.example.kino.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Список пользователей
     */
    @GetMapping("/users")
    public String userList(Model model) {
        boolean isAuthorized = true;
        model.addAttribute("isAuthorized", isAuthorized);

        //boolean isAdmin = true;
        //model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("users", userService.allUsers());

        return "users";
    }

    @ModelAttribute("isAuthorized")
    public boolean isAuthorized(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        return user != null;
    }

/*    @ModelAttribute("isAdmin")
    public boolean isAdmin(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        return user != null && user.hasRole("ADMIN");
    }*/
/*
    @PostMapping("/users")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/users";
    }
*/
}
