package com.huy.crm.controller;

import com.huy.crm.entity.UserEntity;
import com.huy.crm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserEntity userEntity = new UserEntity();
        model.addAttribute("userEntity", userEntity);
        return "auth/register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute UserEntity userEntity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userEntity", userEntity);
            return "auth/register";
        }

        Optional<UserEntity> userOptional = userService.findByUserName(userEntity.getUsername());
        if (userOptional.isPresent()) {
            model.addAttribute("userEntity", userEntity);
            model.addAttribute("error", "User already exists!");
            return "auth/register";
        }

        userOptional = userService.findByEmail(userEntity.getEmail());
        if (userOptional.isPresent()) {
            model.addAttribute("userEntity", userEntity);
            model.addAttribute("error", "Email already exists!");
            return "auth/register";
        }

        userService.saveUser(userEntity);
        return "redirect:/login?success=true";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "auth/access-denied";
    }
}
