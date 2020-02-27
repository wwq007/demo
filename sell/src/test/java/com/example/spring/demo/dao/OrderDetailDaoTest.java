package com.example.spring.demo.dao;

import com.example.spring.demo.entity.OrderDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.SystemProfileValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderDetailDaoTest {
@Autowired
OrderDetailDao orderDetailDao;
    @Test
    void findByOrderId() {
        System.out.println(orderDetailDao.findByOrderId("123456"));

    }

    @Test
    public  void saveTest(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setOrderId("123456");
        orderDetail.setDetailId("1");
        orderDetail.setProductIcon("http.xxxx.jsp");
        orderDetail.setProductId("1111");
        orderDetail.setProductName("水果");
        orderDetail.setProductPrice(new BigDecimal(12));
        orderDetail.setProductQuantity(2);
        orderDetailDao.save(orderDetail);
    }
}