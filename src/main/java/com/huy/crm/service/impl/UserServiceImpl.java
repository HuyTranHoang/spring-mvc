package com.huy.crm.service.impl;


import com.huy.crm.dao.RoleDAO;
import com.huy.crm.dao.UserDAO;
import com.huy.crm.entity.Role;
import com.huy.crm.entity.UserEntity;
import com.huy.crm.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void saveUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        List<Role> roles = new ArrayList<>();
        roles.add(roleDAO.findRoleByName("ROLE_USER"));

        userEntity.setRoles(roles);

        userDAO.saveUser(userEntity);
    }
}
