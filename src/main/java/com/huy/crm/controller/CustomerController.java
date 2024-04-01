package com.huy.crm.controller;

import com.huy.crm.entity.Customer;
import com.huy.crm.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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
        customerService.deleteCustomer(id);

        ra.addFlashAttribute("message", "Customer deleted successfully!");

        return "redirect:/customer/list";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes ra) {
        customerService.saveCustomer(customer);

        if (customer.getId() == 0) {
            ra.addFlashAttribute("message", "Customer added successfully!");
        } else {
            ra.addFlashAttribute("message", "Customer updated successfully!");
        }

        return "redirect:/customer/list";
    }
}