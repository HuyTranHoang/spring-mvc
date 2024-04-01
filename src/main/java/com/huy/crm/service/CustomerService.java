package com.huy.crm.service;

import com.huy.crm.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getCustomers();

    Optional<Customer> getCustomer(int id);

    void saveCustomer(Customer customer);

    void deleteCustomer(int id);
}
