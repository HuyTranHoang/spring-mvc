package com.huy.crm.dto;

import com.huy.crm.validation.FieldMatch;
import com.huy.crm.validation.OnUpdate;
import com.huy.crm.validation.ValidEmail;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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

    @NotEmpty(message = "Username is required", groups = {OnUpdate.class})
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Confirm password is required")
    private String confirmPassword;

    @NotEmpty(message = "Email is required", groups = {OnUpdate.class})
    @ValidEmail(groups = {OnUpdate.class})
    private String email;

    private boolean enabled = true;

    private List<String> roles;

    private MultipartFile image;

    private String imageUrl = "default.jpg";
}