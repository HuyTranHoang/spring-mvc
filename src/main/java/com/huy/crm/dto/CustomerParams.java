package com.huy.crm.dto;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerParams extends PaginationParams {
    private String search;
    private String sort;
}
