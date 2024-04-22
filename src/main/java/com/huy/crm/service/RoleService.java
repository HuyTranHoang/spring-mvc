package com.huy.crm.service;

import com.huy.crm.dto.RoleDto;
import com.huy.crm.entity.Role;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAllRoles();

    RoleDto convertToDto(Role role);
    Role convertToEntity(RoleDto roleDto);

}
