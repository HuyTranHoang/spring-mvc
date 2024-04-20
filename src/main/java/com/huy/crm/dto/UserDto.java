package com.huy.crm.dto;

import com.huy.crm.validation.FieldMatch;
import com.huy.crm.validation.ValidEmail;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Confirm password is required")
    private String confirmPassword;

    @NotEmpty(message = "Email is required")
    @ValidEmail
    private String email;

    private MultipartFile image;

    private String imageUrl = "default.jpg";
}
