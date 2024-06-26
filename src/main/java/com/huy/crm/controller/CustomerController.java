package com.huy.crm.controller;

import com.huy.crm.dto.CustomerDto;
import com.huy.crm.dto.CustomerParams;
import com.huy.crm.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/customer", produces = "text/plain; charset=UTF-8")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ModelAttribute("currentUrl")
    public String getCurrentUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/list")
    public String listCustomers(@ModelAttribute CustomerParams customerParams, Model model) {
        List<CustomerDto> customers = customerService.getCustomers(customerParams);
        model.addAttribute("customerParams", customerParams);
        model.addAttribute("customers", customers);

        int customersCount = customerService.getCustomersCount(customerParams);
        int totalPages = (int) Math.ceil((double) customersCount / customerParams.getPageSize());
        model.addAttribute("totalCount", customersCount);
        model.addAttribute("totalPages", totalPages);

        return "customer/list-customers";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("customerDto", customerDto);
        return "customer/customer-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, RedirectAttributes ra, Model model) {
        Optional<CustomerDto> customerOptional = customerService.getCustomerById(id);

        if (!customerOptional.isPresent()) {
            setNotification(ra, "Error", "Customer not found!", "error");
            return "redirect:/customer/list";
        }

        CustomerDto customerDto = customerOptional.get();
        model.addAttribute("customerDto", customerDto);
        return "customer/customer-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable long id, RedirectAttributes ra) {
        Optional<CustomerDto> customerOptional = customerService.getCustomerById(id);

        if (!customerOptional.isPresent()) {
            setNotification(ra, "Error", "Customer not found!", "error");
            return "redirect:/customer/list";
        }

        customerService.deleteCustomer(id);
        setNotification(ra, "Success", "Customer deleted successfully!", "success");

        return "redirect:/customer/list";
    }

    @PostMapping(value = "/save")
    public String saveCustomer(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                               BindingResult result,
                               RedirectAttributes ra,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customerDto", customerDto);
            return "customer/customer-form";
        }

        Optional<CustomerDto> customerOptional = customerService.getCustomerByEmail(customerDto.getEmail());

        if (customerOptional.isPresent() && customerDto.getId() != customerOptional.get().getId()) {
            model.addAttribute("customer", customerDto);
            result.rejectValue("email", "customer.email", "Email is already in use!");
            return "customer/customer-form";
        }

        if (customerDto.getId() != 0) {
            setNotification(ra, "Success", "Customer updated successfully!", "success");
        } else {
            setNotification(ra, "Success", "Customer added successfully!", "success");
        }

        customerService.saveCustomer(customerDto);

        return "redirect:/customer/list";
    }

    private void setNotification(RedirectAttributes ra, String title, String message, String icon) {
        ra.addFlashAttribute("title", title);
        ra.addFlashAttribute("message", message);
        ra.addFlashAttribute("icon", icon);
    }
}