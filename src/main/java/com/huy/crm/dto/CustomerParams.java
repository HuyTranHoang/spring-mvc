package com.huy.crm.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerParams {
    private String search;
    private String sort;
}
