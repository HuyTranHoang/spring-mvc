package com.huy.crm.service.impl;

import com.huy.crm.dao.CustomerDAO;
import com.huy.crm.dto.CustomerParams;
import com.huy.crm.entity.Customer;
import com.huy.crm.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    @Transactional
    public List<Customer> getCustomers(CustomerParams customerParams) {
        return customerDAO.getCustomers(customerParams);
    }

    @Override
    @Transactional
    public Optional<Customer> getCustomerById(int id) {
        Customer customer = customerDAO.getCustomerById(id);

        if (customer != null) {
            return Optional.of(customer);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Customer> getCustomerByEmail(String email) {
        Customer customer = customerDAO.getCustomerByEmail(email);

        if (customer != null) {
            return Optional.of(customer);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(int id) {
        customerDAO.deleteCustomer(id);
    }
}
