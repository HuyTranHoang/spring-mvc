package com.huy.crm.service;

import com.huy.crm.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();

    void saveCustomer(Customer customer);
}
