package com.example.spring.demo.service;

import com.example.spring.demo.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

/**
 * @Author: wwq
 * @Date: 2020/2/26 12:22
 * @Description: xxxxx
 */
public interface PayService {
     PayResponse create(OrderDTO orderDTO);
     PayResponse notify(String notifyData);
}
