package com.huy.crm.dao.impl;

import com.huy.crm.dao.RoleDAO;
import com.huy.crm.entity.Role;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl extends AbstractJpaDAO<Role> implements RoleDAO {
    @Override
    public Role findRoleByName(String roleName) {
        return entityManager.createQuery("from Role where name = :roleName", Role.class)
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
