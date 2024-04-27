package com.genesys.usermanangement.dto;

import com.genesys.usermanangement.constants.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * UserDto created for interacting with the application, rather than directly exposing User Entity
 *
 * Basic inbuilt jakarta validation checked are applied
 *
 * For more fine-grained control on validation regex or custom constraints can be applied
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserDto {

    private Long id;

    @NotEmpty(message = AppConstants.ERROR_NAME_BLANK)
    private String name;

    @NotEmpty(message = AppConstants.ERROR_EMAIL_BLANK)
    @Email(message = AppConstants.ERROR_EMAIL_FORMAT)
    private String email;

    @NotEmpty(message = AppConstants.ERROR_PASSWORD_BLANK)
    @Size(min = 6, max = 16, message = AppConstants.ERROR_PASSWORD_SIZE)
    private String password;

    private LocalDate lastLogin;

    private String role;
}
