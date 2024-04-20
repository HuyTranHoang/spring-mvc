package com.huy.crm.service;

import com.huy.crm.dto.CustomerDto;
import com.huy.crm.dto.CustomerParams;
import com.huy.crm.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDto> getCustomers(CustomerParams customerParams);

    int getCustomersCount(CustomerParams customerParams);

    Optional<CustomerDto> getCustomerById(long id);

    Optional<CustomerDto> getCustomerByEmail(String email);

    void saveCustomer(CustomerDto customerDto);

    void deleteCustomer(long id);

    CustomerDto convertToDto(Customer customer);

    Customer convertToEntity(CustomerDto customerDto);
}
