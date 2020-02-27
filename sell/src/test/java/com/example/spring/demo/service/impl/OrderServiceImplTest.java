package com.example.spring.demo.service.impl;

import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.logging.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: wwq
 * @Date: 2020/2/22 13:34
 * @Description: xxxxx
 */
@SpringBootTest
@Slf4j
class OrderServiceImplTest {
    @Autowired
    OrderServiceImpl orderService;
    private  final String BUYER_OPENID="110110";
    private final String ORDER_ID="1582351455788580778";
    @Test
    void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("王倩");
        orderDTO.setBuyerAddress("北京中山路17号");
        orderDTO.setBuyerPhone("12345678901");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("123456");
        orderDetail1.setProductQuantity(2);
        orderDetailList.add(orderDetail1);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("123458");
        orderDetail2.setProductQuantity(3);
        orderDTO.setOrderDetailList(orderDetailList);
        orderDetailList.add(orderDetail2);
       OrderDTO result = orderService.create(orderDTO);
        log.info("[ 创建订单] result={}",result);
    }

    @Test
    void findByOrderId() {

        orderService.findByOrderId(ORDER_ID);
    }

    @Test
    void findByBuyerOpenid() {
        PageRequest pageRequest = PageRequest.of(0,2);
       Page<OrderDTO> orderDTOPage = orderService.findByBuyerOpenid(BUYER_OPENID,pageRequest);
        log.info(""+orderDTOPage);
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDER_ID);
        orderService.cancel(orderDTO);
    }

    @Test
    void finish() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDER_ID);
        orderService.finish(orderDTO);
    }

    @Test
    void paid() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDER_ID);
        orderService.paid(orderDTO);
    }

    @Test
    void findAll(){
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<OrderDTO> orderDTOPage = orderService.findAll(pageRequest);
        log.info("【查询列表】orderDTOPage={}",orderDTOPage);
    }

}