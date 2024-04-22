package com.huy.crm.dao;

import com.huy.crm.entity.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> getAllRoles();

    Role findRoleByName(String roleName);

    List<Role> findRoleByUserId(long userId);

    void saveRole(Role role);
}
