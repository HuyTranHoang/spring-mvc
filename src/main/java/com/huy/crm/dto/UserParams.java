package com.huy.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserParams extends PaginationParams {
    private String search;
    private String sort;
}
