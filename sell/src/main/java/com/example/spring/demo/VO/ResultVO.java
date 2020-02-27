package com.example.spring.demo.VO;

import lombok.Data;

/**
 * 最外层的数据
 * @param <T>
 */
@Data
public class ResultVO<T> {
    private String code;
    private String msg;
    private T data;
}
