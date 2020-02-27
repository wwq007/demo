package com.example.spring.demo.converter;

import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.entity.OrderDetail;
import com.example.spring.demo.enums.ResultEnum;
import com.example.spring.demo.exception.SellException;
import com.example.spring.demo.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wwq
 * @Date: 2020/2/23 11:53
 * @Description: xxxxx
 */
@Slf4j
public class OrderForm2OrderDTO {
    public static OrderDTO converter(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        Gson gson = new Gson();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try{
           orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【类型转换】错误 items={}",orderForm.getItems());
            throw  new SellException(ResultEnum.FORM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return  orderDTO;
    }
}
