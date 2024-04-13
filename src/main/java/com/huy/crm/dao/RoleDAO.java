package com.huy.crm.dao;

import com.huy.crm.entity.Role;

public interface RoleDAO {
    Role findRoleByName(String roleName);
    void saveRole(Role role);
}
