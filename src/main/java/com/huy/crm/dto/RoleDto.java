package com.huy.crm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class RoleDto {

    private long id;

    @NotEmpty(message = "Role name is required")
    private String name;
}
