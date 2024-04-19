package com.huy.crm.dao.impl;

import com.huy.crm.dao.UserDAO;
import com.huy.crm.entity.UserEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class UserDaoImpl extends AbstractJpaDAO<UserEntity> implements UserDAO {
    @Override
    @Transactional
    public UserEntity findByUserName(String userName) {
        return entityManager.createQuery("from UserEntity where username = :userName", UserEntity.class)
                .setParameter("userName", userName)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return entityManager.createQuery("from UserEntity where email = :email", UserEntity.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        if (userEntity.getId() == 0) {
            create(userEntity);
        } else {
            update(userEntity);
        }
    }
}
