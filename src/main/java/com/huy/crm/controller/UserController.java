package com.huy.crm.controller;

import com.huy.crm.dto.RoleDto;
import com.huy.crm.dto.UserDto;
import com.huy.crm.dto.UserParams;
import com.huy.crm.service.RoleService;
import com.huy.crm.service.UserService;
import com.huy.crm.validation.OnUpdate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public String listUsers(@ModelAttribute UserParams userParams, Model model) {
        List<UserDto> users = userService.getAllUsers(userParams);
        model.addAttribute("users", users);
        model.addAttribute("userParams", userParams);

        int usersCount = userService.getUsersCount(userParams);
        int totalPages = (int) Math.ceil((double) usersCount / userParams.getPageSize());
        model.addAttribute("totalCount", usersCount);
        model.addAttribute("totalPages", totalPages);
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

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, RedirectAttributes ra, Model model) {
        Optional<UserDto> userDtoOptional = userService.getUserById(id);

        if (!userDtoOptional.isPresent()) {
            setNotification(ra, "Error", "User not found!", "error");
            return "redirect:/customer/list";
        }

        UserDto userDto = userDtoOptional.get();
        model.addAttribute("userDto", userDto);

        List<RoleDto> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "user/user-form";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute UserDto userDto,
                             BindingResult result,
                             RedirectAttributes ra,
                             Model model) {

        boolean isError = checkError(userDto, result, model);
        if (isError) return "user/user-form";

        userService.saveUser(userDto);
        setNotification(ra, "Success", "User saved successfully", "success");
        return "redirect:/user/list";
    }

    @PostMapping("/update")
    public String updateUser(@Validated(OnUpdate.class) @ModelAttribute UserDto userDto,
                             BindingResult result,
                             RedirectAttributes ra,
                             Model model) {

        boolean isError = checkError(userDto, result, model);
        if (isError) return "user/user-form";

        userService.saveUser(userDto);
        setNotification(ra, "Success", "User updated successfully", "success");
        return "redirect:/user/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id, RedirectAttributes ra) {
        Optional<UserDto> userDtoOptional = userService.getUserById(id);

        if (!userDtoOptional.isPresent()) {
            setNotification(ra, "Error", "Customer not found!", "error");
            return "redirect:/customer/list";
        }

        userService.deleteUser(id);
        setNotification(ra, "Success", "Customer deleted successfully!", "success");

        return "redirect:/user/list";
    }

    private boolean checkError(UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<RoleDto> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("userDto", userDto);
            return true;
        }

        Optional<UserDto> existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser.isPresent() && existingUser.get().getId() != userDto.getId()) {
            List<RoleDto> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("userDto", userDto);
            result.rejectValue("email", "userDto.email", "There is already a user registered with the email provided");
            return true;
        }

        existingUser = userService.findByUserName(userDto.getUsername());
        if (existingUser.isPresent() && existingUser.get().getId() != userDto.getId()) {
            List<RoleDto> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("userDto", userDto);
            result.rejectValue("username", "userDto.username", "There is already a user registered with the username provided");
            return true;
        }

        return false;
    }


    private void setNotification(RedirectAttributes ra, String title, String message, String icon) {
        ra.addFlashAttribute("title", title);
        ra.addFlashAttribute("message", message);
        ra.addFlashAttribute("icon", icon);
    }


}
