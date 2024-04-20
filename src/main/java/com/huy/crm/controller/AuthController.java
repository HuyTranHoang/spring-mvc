package com.huy.crm.controller;

import com.huy.crm.dto.UserDto;
import com.huy.crm.entity.UserEntity;
import com.huy.crm.security.SecurityUtil;
import com.huy.crm.service.UserService;
import org.springframework.security.core.userdetails.User;
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
        UserDto userDTO = new UserDto();
        model.addAttribute("registerDTO", userDTO);
        return "auth/register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute UserDto userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registerDTO", userDTO);
            return "auth/register";
        }

        Optional<UserEntity> userOptional = userService.findByUserName(userDTO.getUsername());
        if (userOptional.isPresent()) {
            model.addAttribute("registerDTO", userDTO);
            result.rejectValue("username", "registerDTO.username", "Username already exists!");
            return "auth/register";
        }

        userOptional = userService.findByEmail(userDTO.getEmail());
        if (userOptional.isPresent()) {
            model.addAttribute("registerDTO", userDTO);
            result.rejectValue("email", "registerDTO.email", "Email already exists!");
            return "auth/register";
        }

        UserEntity userEntity = userService.convertToEntity(userDTO);

        userService.saveUser(userEntity);
        return "redirect:/login?success=true";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "auth/access-denied";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        String username = SecurityUtil.getSessionUser();
        Optional<UserEntity> user = userService.findByUserName(username);

        if (!user.isPresent()) {
            return "redirect:/login";
        }

        UserDto userDto = userService.convertToDto(user.get());
        model.addAttribute("userDto", userDto);
        return "auth/profile";
    }
}
