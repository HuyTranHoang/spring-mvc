package com.huy.crm.service.impl;

import com.huy.crm.dao.RoleDAO;
import com.huy.crm.dto.RoleDto;
import com.huy.crm.entity.Role;
import com.huy.crm.service.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    @Transactional
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleDAO.getAllRoles();

        return roles.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto convertToDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    @Override
    public Role convertToEntity(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .build();
    }
}
