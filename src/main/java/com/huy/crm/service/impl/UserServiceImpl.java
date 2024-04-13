package com.huy.crm.service.impl;

import com.huy.crm.dao.RoleDAO;
import com.huy.crm.dao.UserDAO;
import com.huy.crm.dao.UserRoleDAO;
import com.huy.crm.entity.Role;
import com.huy.crm.entity.UserEntity;
import com.huy.crm.entity.UserRole;
import com.huy.crm.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final UserRoleDAO userRoleDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, UserRoleDAO userRoleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.userRoleDAO = userRoleDAO;
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
        if (userEntity == null) {
            throw new IllegalArgumentException("UserEntity cannot be null");
        }

        Role role = roleDAO.findRoleByName("USER");
        UserRole userRole = new UserRole(userEntity, role);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        // Save the UserEntity first
        userDAO.saveUser(userEntity);

        // Then save the UserRole
        userRoleDAO.saveUserRole(userRole);

        List<UserRole> userRoles = userRoleDAO.findUserRoleByUserId(userEntity.getId());

        userEntity.setUserRoles(userRoles);

        // Update the UserEntity with the new UserRole
        userDAO.saveUser(userEntity);
    }
}
