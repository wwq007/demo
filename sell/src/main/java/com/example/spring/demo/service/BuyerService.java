package com.example.spring.demo.service;

import com.example.spring.demo.dto.OrderDTO;

/**
 * @Author: wwq
 * @Date: 2020/2/23 19:02
 * @Description: xxxxx
 */
public interface BuyerService {
    //通过orderId查询订单详情
    OrderDTO findOrderOne(String openid,String orderId);
    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);
}
