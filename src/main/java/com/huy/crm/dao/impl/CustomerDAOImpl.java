package com.huy.crm.dao.impl;

import com.huy.crm.dao.CustomerDAO;
import com.huy.crm.entity.Customer;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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
    public void saveCustomer(Customer customer) {
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
    }
}
