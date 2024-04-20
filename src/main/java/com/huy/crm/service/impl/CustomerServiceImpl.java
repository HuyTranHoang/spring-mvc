package com.huy.crm.service.impl;

import com.huy.crm.dao.CustomerDAO;
import com.huy.crm.dto.CustomerDto;
import com.huy.crm.dto.CustomerParams;
import com.huy.crm.entity.Customer;
import com.huy.crm.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    @Transactional
    public List<CustomerDto> getCustomers(CustomerParams customerParams) {
        return customerDAO.getCustomers(customerParams)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int getCustomersCount(CustomerParams customerParams) {
        return customerDAO.getCustomersCount(customerParams);
    }

    @Override
    @Transactional
    public Optional<CustomerDto> getCustomerById(long id) {
        Customer customer = customerDAO.getCustomerById(id);

        if (customer != null) {
            return Optional.of(convertToDto(customer));
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<CustomerDto> getCustomerByEmail(String email) {
        Customer customer = customerDAO.getCustomerByEmail(email);

        if (customer != null) {
            return Optional.of(convertToDto(customer));
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public void saveCustomer(CustomerDto customerDto) {
        Customer customer = convertToEntity(customerDto);
        customerDAO.saveCustomer(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(long id) {
        customerDAO.deleteCustomer(id);
    }

    @Override
    public CustomerDto convertToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public Customer convertToEntity(CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .build();
    }
}
