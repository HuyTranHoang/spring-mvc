package com.huy.crm.controller;

import com.huy.crm.dto.RoleDto;
import com.huy.crm.dto.UserDto;
import com.huy.crm.service.RoleService;
import com.huy.crm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/user", produces = "text/plain; charset=UTF-8")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ModelAttribute("currentUrl")
    public String getCurrentUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/list-users";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);

        List<RoleDto> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        return "user/user-form";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute UserDto userDto,
                           BindingResult result,
                           RedirectAttributes ra,
                           Model model) {

        userService.saveUser(userDto);
        return "redirect:/user/list";
    }
}
