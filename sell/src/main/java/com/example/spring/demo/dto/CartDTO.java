package com.example.spring.demo.dto;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: wwq
 * @Date: 2020/2/22 12:19
 * @Description: 购物车
 */
@Data
public class CartDTO {
    //商品id
    private String productId;
    //商品数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
