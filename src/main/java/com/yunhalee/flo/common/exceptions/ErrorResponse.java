package com.yunhalee.flo.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String message;
    private long timestamp;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message) {
        this.message = message;
        this.status = status;
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
