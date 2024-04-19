package com.huy.crm.dto;

import com.huy.crm.validation.FieldMatch;
import com.huy.crm.validation.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})
@Getter
@Setter
public class RegisterDTO {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    private String confirmPassword;

    @NotEmpty(message = "Email is required")
    @ValidEmail
    private String email;
}
