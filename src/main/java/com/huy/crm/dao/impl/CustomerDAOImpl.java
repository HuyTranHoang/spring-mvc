package com.huy.crm.dao.impl;

import com.huy.crm.constant.SortCustomerColumn;
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


        if (search != null && !search.trim().isEmpty()) {
            Predicate firstNamePredicate = builder.like(
                    builder.lower(root.get("firstName")), "%" + search.toLowerCase() + "%"
            );
            Predicate lastNamePredicate = builder.like(
                    builder.lower(root.get("lastName")), "%" + search.toLowerCase() + "%"
            );

            query.where(builder.or(firstNamePredicate, lastNamePredicate));
        }


        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case SortCustomerColumn.FIRST_NAME_ASC:
                    query.orderBy(builder.asc(root.get("firstName")));
                    break;
                case SortCustomerColumn.LAST_NAME_ASC:
                    query.orderBy(builder.desc(root.get("lastName")));
                    break;
                case SortCustomerColumn.EMAIL_ASC:
                    query.orderBy(builder.desc(root.get("email")));
                    break;
                default:
                    query.orderBy(builder.asc(root.get("id")));
                    break;
            }
        }

        int page = customerParams.getPage();
        int pageSize = customerParams.getPageSize();
        int firstResult = (page - 1) * pageSize;

        return sessionFactory.getCurrentSession()
                .createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public int getCustomersCount(CustomerParams customerParams) {
        String search = customerParams.getSearch();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Customer> root = query.from(Customer.class);

        query.select(builder.count(root));

        List<Predicate> predicates = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            Predicate firstNamePredicate = builder.like(
                    builder.lower(root.get("firstName")), "%" + search.toLowerCase() + "%"
            );
            Predicate lastNamePredicate = builder.like(
                    builder.lower(root.get("lastName")), "%" + search.toLowerCase() + "%"
            );

            predicates.add(builder.or(firstNamePredicate, lastNamePredicate));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return Math.toIntExact(sessionFactory.getCurrentSession()
                .createQuery(query)
                .getSingleResult());
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
