package com.yunhalee.flo.common.exceptions;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String message) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
            "status=" + status +
            ", message='" + message + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
