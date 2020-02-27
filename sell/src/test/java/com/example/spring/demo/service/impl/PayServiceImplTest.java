package com.example.spring.demo.service.impl;

import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.service.OrderService;
import com.example.spring.demo.service.PayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: wwq
 * @Date: 2020/2/26 13:55
 * @Description: xxxxx
 */
@SpringBootTest
class PayServiceImplTest {
@Autowired
    PayService payService;
@Autowired
    OrderService orderService;
    @Test
    void create() {
        OrderDTO orderDTO = orderService.findByOrderId("1582351455788580778");
        payService.create(orderDTO);
    }
}