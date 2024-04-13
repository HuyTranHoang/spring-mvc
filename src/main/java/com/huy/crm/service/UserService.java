package com.huy.crm.service;

import com.huy.crm.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByUserName(String username);
    Optional<UserEntity> findByEmail(String email);
    void saveUser(UserEntity userEntity);
}