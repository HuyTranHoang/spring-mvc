package com.huy.crm.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerParams extends PaginationParams {
    private String search;
    private String sort;
}
