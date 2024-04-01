package com.huy.crm.dao;

import com.huy.crm.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getCustomers();

    void saveCustomer(Customer customer);
}
