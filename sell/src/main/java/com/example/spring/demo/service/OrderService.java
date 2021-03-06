package com.example.spring.demo.service;

import com.example.spring.demo.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    //创建订单
    OrderDTO create(OrderDTO orderDTO);
    //查询订单详情
    OrderDTO findByOrderId(String orderId);
    //查询订单列表
    Page<OrderDTO> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);
    //完结订单
    OrderDTO finish(OrderDTO orderDTO);
    //支付订单
    OrderDTO paid(OrderDTO orderDTO);
    //查询所有订单
    Page<OrderDTO> findAll(Pageable pageable);
}
