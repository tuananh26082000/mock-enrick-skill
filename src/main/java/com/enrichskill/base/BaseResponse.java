package com.enrichskill.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseResponse<T> {
    private int code;
    private T data;

    private BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> BaseResponse<T> ofSuccess(T data) {
        return new BaseResponse<>(200, data);
    }

    public static <T> BaseResponse<T> ofSuccess(HttpStatus status, T data) {
        return new BaseResponse<>(status.value(), data);
    }

    public static BaseResponse<ErrorResponse> ofFailed(ErrorResponse errorResponse) {
        return new BaseResponse<>(errorResponse.getStatus().value(), errorResponse);
    }
}
