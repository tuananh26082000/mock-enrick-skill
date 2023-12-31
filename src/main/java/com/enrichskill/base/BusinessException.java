package com.enrichskill.base;

import lombok.Getter;
import lombok.Setter;

public final class BusinessException extends RuntimeException {
    @Getter
    @Setter
    private transient ErrorResponse errorResponse;

    public BusinessException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
