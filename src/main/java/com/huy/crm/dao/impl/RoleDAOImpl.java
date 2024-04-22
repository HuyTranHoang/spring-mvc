package com.huy.crm.dao.impl;

import com.huy.crm.dao.RoleDAO;
import com.huy.crm.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class RoleDAOImpl extends AbstractJpaDAO<Role> implements RoleDAO {

    public RoleDAOImpl() {
        setClazz(Role.class);
    }

    @Override
    public List<Role> getAllRoles() {
        return findAll();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return entityManager.createQuery("select r from Role r where name = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Role> findRoleByUserId(long userId) {
        return entityManager.createQuery("select r from Role r join r.users u where u.id = :userId", Role.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void saveRole(Role role) {
        if (role.getId() == 0) {
            create(role);
        } else {
            update(role);
        }
    }
}
