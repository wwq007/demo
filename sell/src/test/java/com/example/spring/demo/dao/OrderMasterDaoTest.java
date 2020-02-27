package com.example.spring.demo.dao;

import com.example.spring.demo.entity.OrderMaster;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderMasterDaoTest {
@Autowired
OrderMasterDao orderMasterDao;
    @Test
    void findByBuyerOpenid() {
        PageRequest pageRequest=PageRequest.of(0,2);
        Page<OrderMaster> result=orderMasterDao.findByBuyerOpenid("1101",pageRequest);
        System.out.print(result.getTotalElements());
    }
    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("小敏");
        orderMaster.setBuyerPhone("12345678901");
        orderMaster.setBuyerAddress("月球");
        orderMaster.setBuyerOpenid("abcd");
        orderMaster.setOrderAmount(new BigDecimal(10));
        orderMasterDao.save(orderMaster);
    }
}