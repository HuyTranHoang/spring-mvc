package com.huy.crm.service.impl;

import com.huy.crm.dao.RoleDAO;
import com.huy.crm.dao.UserDAO;
import com.huy.crm.dto.UserDto;
import com.huy.crm.dto.UserParams;
import com.huy.crm.entity.Role;
import com.huy.crm.entity.UserEntity;
import com.huy.crm.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public List<UserDto> getAllUsers(UserParams userParams) {
        List<UserEntity> userEntities = userDAO.getAllUsers(userParams);

        return userEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int getUsersCount(UserParams userParams) {
        return userDAO.getUsersCount(userParams);
    }

    @Override
    @Transactional
    public Optional<UserDto> getUserById(long id) {
        UserEntity userEntity = userDAO.getUserById(id);

        if (userEntity != null) {
            UserDto userDto = convertToDto(userEntity);
            return Optional.of(userDto);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserDto> findByUserName(String username) {
        UserEntity userEntity = userDAO.findByUserName(username);

        if (userEntity != null) {
            UserDto userDto = convertToDto(userEntity);
            return Optional.of(userDto);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserDto> findByEmail(String email) {
        UserEntity userEntity = userDAO.findByEmail(email);

        if (userEntity != null) {
            UserDto userDto = convertToDto(userEntity);
            return Optional.of(userDto);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public void saveUser(UserDto userDto) {
        UserEntity userEntity = convertToEntity(userDto);

        if (userEntity.getId() != 0) {
            String hashedPassword = userDAO.getHashedPassword(userEntity.getId());
            userEntity.setPassword(hashedPassword);
        } else {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }

        userDAO.saveUser(userEntity);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        UserEntity userEntity = userDAO.getUserById(id);

        if (userEntity != null) {
            userEntity.getRoles().clear();
            userDAO.saveUser(userEntity);
            userDAO.deleteUser(id);
        }
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
        List<Role> roles = roleDAO.findRoleByUserId(userDto.getId());

        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            roles = userDto.getRoles().stream()
                    .map(roleDAO::findRoleByName)
                    .collect(Collectors.toList());
        }

        if (roles.isEmpty()) {
            roles.add(roleDAO.findRoleByName("ROLE_USER"));
        }

        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .imageUrl(userDto.getImageUrl())
                .roles(roles)
                .enabled(userDto.isEnabled())
                .build();
    }
}
