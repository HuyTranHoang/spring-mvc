package com.huy.crm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users_roles")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserRoleId.class)
public class UserRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
class UserRoleId implements Serializable {
    private UserEntity userEntity;
    private Role role;
}