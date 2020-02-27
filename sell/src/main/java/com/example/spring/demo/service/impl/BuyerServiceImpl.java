package com.example.spring.demo.service.impl;

import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.enums.ResultEnum;
import com.example.spring.demo.exception.SellException;
import com.example.spring.demo.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wwq
 * @Date: 2020/2/23 19:06
 * @Description: xxxxx
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    OrderServiceImpl orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {


        return checkOwnerUser(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
       OrderDTO orderDTO = checkOwnerUser(openid,orderId);
      return orderService.cancel(orderDTO);
    }

    public OrderDTO checkOwnerUser(String openid, String orderId){
        OrderDTO orderDTO = orderService.findByOrderId(orderId);
        if(orderDTO == null){
            return null;
        }
        //判断是否是自己的订单
        if(!orderDTO.getBuyerOpenid().equals(openid)){
            log.error("【查询订单详情】openid不一致. openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnum.OPENID_ERROR);
        }

        return orderDTO;
    }
}
