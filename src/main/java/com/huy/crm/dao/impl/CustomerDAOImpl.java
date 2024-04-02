package com.huy.crm.dao.impl;

import com.huy.crm.dao.CustomerDAO;
import com.huy.crm.entity.Customer;
import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private final SessionFactory sessionFactory;

    public CustomerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Customer> getCustomers() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Customer", Customer.class)
                .getResultList();
    }

    @Override
    public Customer getCustomer(int id) {
        return sessionFactory.getCurrentSession()
                .get(Customer.class, id);
    }


    @Override
    public void saveCustomer(Customer customer) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(customer);
    }

    @Override
    public void deleteCustomer(int customerId) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from Customer where id = :customerId")
                .setParameter("customerId", customerId)
                .executeUpdate();
    }
}
