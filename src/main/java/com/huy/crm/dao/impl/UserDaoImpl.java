package com.huy.crm.dao.impl;

import com.huy.crm.dao.UserDAO;
import com.huy.crm.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl extends AbstractJpaDAO<UserEntity> implements UserDAO {

    public UserDaoImpl() {
        setClazz(UserEntity.class);
    }

    @Override
    @Transactional
    public List<UserEntity> getAllUsers() {
        return this.findAll();
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return entityManager.createQuery("select u from UserEntity u where username = :userName", UserEntity.class)
                .setParameter("userName", userName)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return entityManager.createQuery("select u from UserEntity u where email = :email", UserEntity.class)
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
