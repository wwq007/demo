package com.example.spring.demo.service.impl;

import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.enums.ResultEnum;
import com.example.spring.demo.exception.SellException;
import com.example.spring.demo.service.OrderService;
import com.example.spring.demo.service.PayService;
import com.example.spring.demo.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: wwq
 * @Date: 2020/2/26 12:23
 * @Description: xxxxx
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {
    @Autowired
    BestPayServiceImpl bestPayService;
    @Autowired
    OrderService orderService;
    private  static final String ORDER_NAME="微信点餐支付订单";
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付payRequest={}",payRequest);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付 payResponse={}",payResponse);
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名
        //2.支付状态
        //3.支付金额
        //4.支付人（下单人==支付人）
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);//验证签名、支付状态
        log.info("【微信支付】异步通知 payResponse={}",payResponse);
        OrderDTO orderDTO = orderService.findByOrderId(payResponse.getOrderId());
        if(orderDTO == null){
            log.error("【微信支付】异步通知,订单不存在orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){//验证支付金额0.10  0.1
            log.error("【微信支付】异步通知，订单金额跟支付金额不一致，orderId={}，异步通知金额={}，系统金额={}",
                    orderDTO.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw  new SellException(ResultEnum.PAY_AMOUNT_NOT_EQUAL);
        }
        orderService.paid(orderDTO);

        return payResponse;
    }
}
