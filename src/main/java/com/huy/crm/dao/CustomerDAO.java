package com.huy.crm.dao;

import com.huy.crm.dto.CustomerParams;
import com.huy.crm.entity.Customer;

import java.util.List;


public interface CustomerDAO {
    List<Customer> getCustomers(CustomerParams customerParams);
    Customer getCustomerById(int id);
    Customer getCustomerByEmail(String email);
    void saveCustomer(Customer customer);
    void deleteCustomer(int id);
}
