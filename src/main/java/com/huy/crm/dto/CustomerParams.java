package com.huy.crm.dto;


import lombok.*;

@Setter
@Getter
public class CustomerParams extends PaginationParams {
    private String search;
    private String sort;
}
