package com.huy.crm.service.impl;

import com.huy.crm.dao.CustomerDAO;
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
    public List<Customer> getCustomers(String search) {
        return customerDAO.getCustomers(search);
    }

    @Override
    @Transactional
    public Optional<Customer> getCustomer(int id) {
        Customer customer = customerDAO.getCustomer(id);

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
