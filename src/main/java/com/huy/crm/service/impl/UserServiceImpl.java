package com.huy.crm.service.impl;


import com.huy.crm.dao.RoleDAO;
import com.huy.crm.dao.UserDAO;
import com.huy.crm.dto.UserDto;
import com.huy.crm.entity.Role;
import com.huy.crm.entity.UserEntity;
import com.huy.crm.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntities = userDAO.getAllUsers();

        return userEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<UserEntity> findByUserName(String username) {
        UserEntity userEntity = userDAO.findByUserName(username);

        if (userEntity != null) {
            return Optional.of(userEntity);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserEntity> findByEmail(String email) {
        UserEntity userEntity = userDAO.findByEmail(email);

        if (userEntity != null) {
            return Optional.of(userEntity);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public void saveUser(UserDto userDto) {
        UserEntity userEntity = convertToEntity(userDto);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        if (userEntity.getRoles() == null) {
            List<Role> roles = new ArrayList<>();
            roles.add(roleDAO.findRoleByName("ROLE_USER"));
            userEntity.setRoles(roles);
        }

        userDAO.saveUser(userEntity);
    }

    @Override
    public UserDto convertToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .imageUrl(userEntity.getImageUrl())
                .roles(userEntity.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .enabled(userEntity.isEnabled())
                .build();
    }

    @Override
    public UserEntity convertToEntity(UserDto userDto) {

        List<Role> roles;

        if (!userDto.getRoles().isEmpty()) {
            roles = userDto.getRoles().stream()
                    .map(roleDAO::findRoleByName)
                    .collect(Collectors.toList());
        } else {
            roles = roleDAO.findRoleByUserId(userDto.getId());
            if (roles.isEmpty()) {
                roles.add(roleDAO.findRoleByName("ROLE_USER"));
            }
        }

        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .imageUrl(userDto.getImageUrl())
                .roles(roles)
                .enabled(true)
                .build();
    }
}
