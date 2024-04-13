package com.huy.crm.dao.impl;

import com.huy.crm.dao.UserDAO;
import com.huy.crm.entity.UserEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class UserDaoImpl implements UserDAO {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public UserEntity findByUserName(String userName) {
        return sessionFactory.getCurrentSession()
                .createQuery("from UserEntity where username = :userName", UserEntity.class)
                .setParameter("userName", userName)
                .uniqueResult();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("from UserEntity where email = :email", UserEntity.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        sessionFactory.getCurrentSession()
                .save(userEntity);
    }
}
