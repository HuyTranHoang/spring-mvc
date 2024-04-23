package com.huy.crm.dao;

import com.huy.crm.dto.UserParams;
import com.huy.crm.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    List<UserEntity> getAllUsers(UserParams userParams);

    int getUsersCount(UserParams userParams);

    UserEntity getUserById(long id);

    UserEntity findByUserName(String userName);

    UserEntity findByEmail(String email);

    String getHashedPassword(long id);

    void saveUser(UserEntity userEntity);

    void deleteUser(long id);
}
