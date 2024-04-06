package com.huy.crm.service;

import com.huy.crm.dto.CustomerParams;
import com.huy.crm.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getCustomers(CustomerParams customerParams);

    int getCustomersCount(CustomerParams customerParams);

    Optional<Customer> getCustomerById(int id);

    Optional<Customer> getCustomerByEmail(String email);

    void saveCustomer(Customer customer);

    void deleteCustomer(int id);
}
