package com.example.spring.demo.controller;

import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: wwq
 * @Date: 2020/2/27 16:55
 * @Description: 买家端订单
 */
//@Controller
    @RestController
@RequestMapping(value = "/seller/order")
public class SellerOrderController {
    @Autowired
    OrderServiceImpl orderService;

    /**
     * 订单列表
     * @param page 第几页，从第一页开始
     * @param size 每页几条数据
     * @param map
     * @return
     */
    @GetMapping (value = "/list")
    public ModelAndView  findAll(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "size",defaultValue = "10")Integer size,
                                Map<String,Object> map){
        PageRequest pageRequest =PageRequest.of(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findAll(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
       return new ModelAndView("order/list",map);
    }
}
