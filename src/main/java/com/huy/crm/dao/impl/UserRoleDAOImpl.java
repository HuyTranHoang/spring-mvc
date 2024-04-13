package com.huy.crm.dao.impl;

import com.huy.crm.dao.UserRoleDAO;
import com.huy.crm.entity.UserRole;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleDAOImpl implements UserRoleDAO {
    private final SessionFactory sessionFactory;

    public UserRoleDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveUserRole(UserRole userRole) {
        sessionFactory.getCurrentSession().save(userRole);
    }

    @Override
    public List<UserRole> findUserRoleByUserId(int userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from UserRole where userEntity.id = :userId", UserRole.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
