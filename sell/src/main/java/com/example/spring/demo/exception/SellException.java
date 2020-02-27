package com.example.spring.demo.exception;

import com.example.spring.demo.enums.ResultEnum;

/**
 * @Author: wwq
 * @Date: 2020/2/22 0:11
 * @Description: xxxxx
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String defaultMessage) {
        super(defaultMessage);
        this.code=code;
    }
}
