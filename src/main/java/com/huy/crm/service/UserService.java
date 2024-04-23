package com.huy.crm.service;

import com.huy.crm.dto.UserDto;
import com.huy.crm.dto.UserParams;
import com.huy.crm.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers(UserParams userParams);

    int getUsersCount(UserParams userParams);

    Optional<UserDto> getUserById(long id);

    Optional<UserDto> findByUserName(String username);

    Optional<UserDto> findByEmail(String email);

    void saveUser(UserDto userDto);

    void deleteUser(long id);

    UserDto convertToDto(UserEntity userEntity);

    UserEntity convertToEntity(UserDto userDto);
}
