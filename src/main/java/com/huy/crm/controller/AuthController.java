package com.huy.crm.controller;

import com.huy.crm.dto.RegisterDTO;
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
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("registerDTO", registerDTO);
        return "auth/register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute RegisterDTO registerDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registerDTO", registerDTO);
            return "auth/register";
        }

        Optional<UserEntity> userOptional = userService.findByUserName(registerDTO.getUsername());
        if (userOptional.isPresent()) {
            model.addAttribute("registerDTO", registerDTO);
            result.rejectValue("username", "registerDTO.username", "Username already exists!");
            return "auth/register";
        }

        userOptional = userService.findByEmail(registerDTO.getEmail());
        if (userOptional.isPresent()) {
            model.addAttribute("registerDTO", registerDTO);
            result.rejectValue("email", "userEntity.email", "Email already exists!");
            return "auth/register";
        }

        UserEntity userEntity = UserEntity.builder()
                .username(registerDTO.getUsername())
                .password(registerDTO.getPassword())
                .email(registerDTO.getEmail())
                .enabled(true)
                .build();

        userService.saveUser(userEntity);
        return "redirect:/login?success=true";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "auth/access-denied";
    }
}
