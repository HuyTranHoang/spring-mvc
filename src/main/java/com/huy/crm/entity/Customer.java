package com.huy.crm.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", length = 50)
    @NotEmpty(message = "First name is required")
    private String firstName;

    @Column(name = "last_name", length = 50)
    @NotEmpty(message = "Last name is required")
    private String lastName;

    @Column(name = "email", length = 250)
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
