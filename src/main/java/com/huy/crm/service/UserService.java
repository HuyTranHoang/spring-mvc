package com.huy.crm.service;

import com.huy.crm.dto.UserDto;
import com.huy.crm.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers();

    Optional<UserEntity> findByUserName(String username);

    Optional<UserEntity> findByEmail(String email);

    void saveUser(UserDto userDto);

    UserDto convertToDto(UserEntity userEntity);

    UserEntity convertToEntity(UserDto userDto);
}
