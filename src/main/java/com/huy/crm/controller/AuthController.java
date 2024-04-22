package com.huy.crm.controller;

import com.huy.crm.dto.UserDto;
import com.huy.crm.entity.UserEntity;
import com.huy.crm.security.SecurityUtil;
import com.huy.crm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;
    private final ServletContext servletContext;

    public AuthController(UserService userService, ServletContext servletContext) {
        this.userService = userService;
        this.servletContext = servletContext;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserDto userDTO = new UserDto();
        model.addAttribute("userDTO", userDTO);
        return "auth/register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute UserDto userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
            return "auth/register";
        }

        Optional<UserEntity> userOptional = userService.findByUserName(userDTO.getUsername());
        if (userOptional.isPresent()) {
            model.addAttribute("userDTO", userDTO);
            result.rejectValue("username", "userDTO.username", "Username already exists!");
            return "auth/register";
        }

        userOptional = userService.findByEmail(userDTO.getEmail());
        if (userOptional.isPresent()) {
            model.addAttribute("userDTO", userDTO);
            result.rejectValue("email", "userDTO.email", "Email already exists!");
            return "auth/register";
        }

        userService.saveUser(userDTO);
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

    @PostMapping(value = "/profile/save")
    public String updateProfile(@Valid @ModelAttribute UserDto userDto,
                                BindingResult result,
                                RedirectAttributes ra,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "auth/profile";
        }

        String username = SecurityUtil.getSessionUser();
        Optional<UserEntity> user = userService.findByUserName(username);

        if (!user.isPresent()) {
            return "redirect:/login";
        }

        MultipartFile image = userDto.getImage();

        try {
            if (image != null && !image.isEmpty()) {
                String uploadDir = "/WEB-INF/resources/images/user-photos/";
                String realUploadDir = servletContext.getRealPath(uploadDir);

                Path uploadPath = Paths.get(realUploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String filename =  System.currentTimeMillis() + "-" + image.getOriginalFilename();

                Path filePath = uploadPath.resolve(filename);
                image.transferTo(filePath.toFile());

                userDto.setImageUrl(filename);
            }
        } catch (IOException e) {
            model.addAttribute("userDto", userDto);
            result.rejectValue("image", "userDto.image", "Error saving image!");
            return "auth/profile";
        }

        userService.saveUser(userDto);

        ra.addFlashAttribute("success", true);

        return "redirect:/profile?success=true";
    }


}
