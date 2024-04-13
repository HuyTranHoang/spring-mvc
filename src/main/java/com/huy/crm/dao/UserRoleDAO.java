package com.huy.crm.dao;

import com.huy.crm.entity.UserRole;

import java.util.List;

public interface UserRoleDAO {
    void saveUserRole(UserRole userRole);
    List<UserRole> findUserRoleByUserId(int userId);
}
