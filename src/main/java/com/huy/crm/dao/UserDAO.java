package com.huy.crm.dao;

import com.huy.crm.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    List<UserEntity> getAllUsers();

    UserEntity findByUserName(String userName);

    UserEntity findByEmail(String email);

    void saveUser(UserEntity userEntity);
}
