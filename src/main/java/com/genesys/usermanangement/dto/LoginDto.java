package com.genesys.usermanangement.dto;

import com.genesys.usermanangement.constants.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This DTO used for login purpose
 * Appropriate validations given
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LoginDto {

    @NotEmpty(message = AppConstants.ERROR_EMAIL_BLANK)
    @Email(message = AppConstants.ERROR_EMAIL_FORMAT)
    private String email;

    @NotEmpty(message = AppConstants.ERROR_PASSWORD_BLANK)
    @Size(min = 6, max = 16, message = AppConstants.ERROR_PASSWORD_SIZE)
    private String password;
}