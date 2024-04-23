package com.huy.crm.dao.impl;

import com.huy.crm.dao.RoleDAO;
import com.huy.crm.entity.Role;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private final SessionFactory sessionFactory;

    public RoleDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Role> getAllRoles() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Role", Role.class)
                .getResultList();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Role where name = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .uniqueResult();
    }

    @Override
    public List<Role> findRoleByUserId(long userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("select r from Role r join r.users u where u.id = :userId", Role.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void saveRole(Role role) {
        sessionFactory.getCurrentSession().saveOrUpdate(role);
    }
}
