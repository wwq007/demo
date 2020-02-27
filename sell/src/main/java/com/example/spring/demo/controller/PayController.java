package com.example.spring.demo.controller;

import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.enums.ResultEnum;
import com.example.spring.demo.exception.SellException;
import com.example.spring.demo.service.OrderService;
import com.example.spring.demo.service.impl.PayServiceImpl;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: wwq
 * @Date: 2020/2/26 12:14
 * @Description: xxxxx
 */
@RestController
@RequestMapping(value="/pay")
@Slf4j
public class PayController {
    @Autowired
    OrderService orderService;
    @Autowired
    PayServiceImpl payService;
    @GetMapping(value="/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map<String,Object> map){
        //查询订单是否存在
       OrderDTO orderDTO = orderService.findByOrderId(orderId);
       if(orderDTO == null){
           log.error("【订单支付】,订单不存在 orderDTO={}",orderDTO);
           throw new SellException(ResultEnum.ORDER_NOT_EXIST);
       }
       //支付
        PayResponse payResponse = payService.create(orderDTO);
      map.put("payResponse",payResponse);
      map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create",map);
    }

    //异步通知
    @PostMapping(value = "/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);

        //返回给微信处理结果
       return new ModelAndView("pay/success");
    }

}
