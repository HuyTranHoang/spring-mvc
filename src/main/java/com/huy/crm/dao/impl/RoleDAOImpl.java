package com.huy.crm.dao.impl;

import com.huy.crm.dao.RoleDAO;
import com.huy.crm.entity.Role;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class RoleDAOImpl implements RoleDAO {
    private final SessionFactory sessionFactory;

    public RoleDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role findRoleByName(String roleName) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Role where name = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }

    @Override
    public void saveRole(Role role) {
        sessionFactory.getCurrentSession()
                .save(role);
    }
}
