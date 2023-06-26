package com.simpleAccountService.dto.error;

import com.simpleAccountService.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private ErrorCode errorCode;
    private String errorMessage;

}
