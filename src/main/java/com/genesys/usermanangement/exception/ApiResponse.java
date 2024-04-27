package com.genesys.usermanangement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ApiResponse created for having symmetric response structures
 * More fields can be added if required
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApiResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

}
