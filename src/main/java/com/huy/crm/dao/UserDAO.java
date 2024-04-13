package com.huy.crm.dao;

import com.huy.crm.entity.UserEntity;

public interface UserDAO {
    UserEntity findByUserName(String userName);
    UserEntity findByEmail(String email);
    void saveUser(UserEntity userEntity);
}
