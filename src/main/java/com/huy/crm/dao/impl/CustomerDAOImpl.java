package com.huy.crm.dao.impl;

import com.huy.crm.dao.CustomerDAO;
import com.huy.crm.dto.CustomerParams;
import com.huy.crm.entity.Customer;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private final SessionFactory sessionFactory;

    public CustomerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Customer> getCustomers(CustomerParams customerParams) {
        String search = customerParams.getSearch();
        String sort = customerParams.getSort();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();

        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);

        Root<Customer> root = query.from(Customer.class);

        // Tạo danh sách Predicate để thêm điều kiện
        List<Predicate> predicates = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            Predicate firstNamePredicate = builder.like(builder.lower(root.get("firstName")), "%" + search.toLowerCase() + "%");
            Predicate lastNamePredicate = builder.like(builder.lower(root.get("lastName")), "%" + search.toLowerCase() + "%");

            predicates.add(builder.or(firstNamePredicate, lastNamePredicate));
        }

        // Thêm tất cả các điều kiện vào CriteriaQuery
        query.where(predicates.toArray(new Predicate[0]));

        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "firstNameDesc":
                    query.orderBy(builder.asc(root.get("firstName")));
                    break;
                case "lastNameAsc":
                    query.orderBy(builder.desc(root.get("lastName")));
                    break;
                case "emailAsc":
                    query.orderBy(builder.desc(root.get("email")));
                    break;
                case "emailDesc":
                    query.orderBy(builder.asc(root.get("email")));
                    break;
                default:
                    query.orderBy(builder.asc(root.get("id")));
                    break;
            }
        }

        return sessionFactory.getCurrentSession()
                .createQuery(query)
                .getResultList();
    }

    @Override
    public Customer getCustomerById(int id) {
        return sessionFactory.getCurrentSession()
                .get(Customer.class, id);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Customer where email = :email", Customer.class)
                .setParameter("email", email)
                .uniqueResult();
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
