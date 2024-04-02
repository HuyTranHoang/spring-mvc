package com.huy.crm.dao;

import com.huy.crm.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> getCustomers(String search);

    Customer getCustomer(int id);

    void saveCustomer(Customer customer);

    void deleteCustomer(int id);
}
