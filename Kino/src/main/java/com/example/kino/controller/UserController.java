package com.example.kino.controller;

import com.example.kino.dto.UserDto;
import com.example.kino.exception.MainException;
import com.example.kino.model.User;
import com.example.kino.service.RoleService;
import com.example.kino.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping
@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * Регистрация
     */
    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("error") String error, Model model) {
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("error", error);

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("newUser") User newUser,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttrs,
                               Model model) {

        // Создание нового пользователя произошло на странице регистрации
        // и записанные данные передались в качестве атрибута
        try {
            // Присвоение роли пользователю
            /*Role userRole = roleService.findByName("USER");
            newUser.setRoles(Collections.singleton(userRole));*/

            userService.saveUser(newUser);
        } catch (MainException e) {
            redirectAttrs.addAttribute("error", "Such user already exists. Try again");
            return "redirect:/registration";
        }

        // Перенаправление на страницу входа после успешной регистрации
        return "redirect:/login";
    }

    /**
     * Вход
     */
    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute("error") String error, Model model) {
        model.addAttribute("oldUser", new UserDto());
        model.addAttribute("error", error);

        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("oldUser") UserDto oldUser,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttrs) {

        // Проверка введенных данных на странице на соответствие пользователя в базе данных
        if(userService.isValidUser(oldUser.getUsername(), oldUser.getPassword())) {
            UserDto userDto = userService.getUserDtoByUsername(oldUser.getUsername());
            request.getSession().setAttribute("user", userDto);
            // Перенаправление на главную страницу после успешного входа
            return "redirect:/";
        } else {
            // Перенаправление на страницу входа с сообщением об ошибке
            redirectAttrs.addAttribute("error", "Invalid username or password. Try again");

            return "redirect:/login";
        }
    }

    /**
     * Выход
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/catalog";
    }

    @ModelAttribute("isAuthorized")
    public String isAuthorized(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        return user != null ? "/" : null;
    }
}
