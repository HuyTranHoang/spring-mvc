package com.huy.crm.dao.impl;

import com.huy.crm.dao.RoleDAO;
import com.huy.crm.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl extends AbstractJpaDAO<Role> implements RoleDAO {

    public RoleDAOImpl() {
        setClazz(Role.class);
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
    public void saveRole(Role role) {
        if (role.getId() == 0) {
            create(role);
        } else {
            update(role);
        }
    }
}
