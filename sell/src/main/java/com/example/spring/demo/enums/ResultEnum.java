package com.example.spring.demo.enums;

import lombok.Getter;

/**
 * @Author: wwq
 * @Date: 2020/2/22 0:12
 * @Description: xxxxx
 */
@Getter
public enum ResultEnum {
    FORM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIT(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDER_CANCEL_ERROE(13,"取消订单失败"),
    ORDER_UPDATE_ERROR(14,"订单更新失败"),
    ORDER_DETAIL_ERROR(15,"订单详情为空"),
    ORDER_STATU_ERROR(16,"订单状态不正确"),
    CHART_ERROR(17,"购物车为空"),
    OPENID_ERROR(18,"用户的openid不正确"),
    WECHAT_MP_ERROR(19,"获取access_token出错"),
    PAY_AMOUNT_NOT_EQUAL(20,"异步通知，支付金额与系统金额不一致"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
