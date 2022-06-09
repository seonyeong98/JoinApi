package com.example.joinapi.notice.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseResult<T> {

    T result;

    public static <T> ResponseResult of(T value) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setResult(value);
        return responseResult;
    }
}
