package com.huy.crm.controller;

import com.huy.crm.entity.Customer;
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
@RequestMapping("/customer")
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
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);
        return "customer/list-customers";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer/customer-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Customer> customerOptional = customerService.getCustomer(id);

        if (!customerOptional.isPresent()) {
            return "redirect:/customer/list";
        }

        Customer customer = customerOptional.get();
        model.addAttribute("customer", customer);
        return "customer/customer-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id, RedirectAttributes ra) {
        Optional<Customer> customerOptional = customerService.getCustomer(id);

        if (!customerOptional.isPresent()) {
            setNotification(ra, "Error", "Customer not found!", "error");
            return "redirect:/customer/list";
        }

        customerService.deleteCustomer(id);
        setNotification(ra, "Success", "Customer deleted successfully!", "success");

        return "redirect:/customer/list";
    }

    @PostMapping("/save")
    public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer,
                               BindingResult result,
                               RedirectAttributes ra,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            return "customer/customer-form";
        }

        customerService.saveCustomer(customer);

        if (customer.getId() == 0) {
            setNotification(ra, "Success", "Customer added successfully!", "success");
        } else {
            setNotification(ra, "Success", "Customer updated successfully!", "success");
        }

        return "redirect:/customer/list";
    }

    private void setNotification(RedirectAttributes ra, String title, String message, String icon) {
        ra.addFlashAttribute("title", title);
        ra.addFlashAttribute("message", message);
        ra.addFlashAttribute("icon", icon);
    }
}