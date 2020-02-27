package com.example.spring.demo.controller;


import com.example.spring.demo.VO.ResultVO;
import com.example.spring.demo.converter.OrderForm2OrderDTO;
import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.enums.ResultEnum;
import com.example.spring.demo.exception.SellException;
import com.example.spring.demo.form.OrderForm;
import com.example.spring.demo.service.OrderService;
import com.example.spring.demo.service.impl.BuyerServiceImpl;
import com.example.spring.demo.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wwq
 * @Date: 2020/2/23 10:26
 * @Description: xxxxx
 */
@RestController
@Slf4j
@RequestMapping(value="/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    BuyerServiceImpl buyerService;
    //创建订单
    @PostMapping(value = "/create")
    public ResultVO<Map<String,Object>> create(@Valid OrderForm orderForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确 orderForm={}",orderForm);
            throw new SellException(ResultEnum.FORM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTO.converter(orderForm);
        if(orderDTO.getOrderDetailList().isEmpty()){
            log.error("【创建订单】失败，购物车不能为空");
            throw new SellException(ResultEnum.CHART_ERROR);
        }

        OrderDTO createOrderDTO = orderService.create(orderDTO);
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",createOrderDTO.getOrderId());

        return ResultVOUtil.success(map);
    }

    //订单列表查询
    @GetMapping(value = "/list")
    public ResultVO<List<OrderDTO>> findOrderListByOpenid(@RequestParam("openid")String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                        @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【订单列表查询】openid不能为空");
            throw new SellException(ResultEnum.FORM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page,size);

        Page<OrderDTO> orderDTOPage = orderService.findByBuyerOpenid(openid,pageRequest);
        List<OrderDTO> orderDTOList = orderDTOPage.getContent();


        return ResultVOUtil.success(orderDTOList);
    }
    //订单详情
    @GetMapping(value="/detail")
    public ResultVO<OrderDTO> detail(@RequestParam(value="openid") String openid,
                                    @RequestParam(value="orderId") String orderId){
        OrderDTO orderDTO = buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping(value = "/cancel")
    public ResultVO cancel(@RequestParam(value="openid") String openid,
                           @RequestParam(value="orderId") String orderId){
//       OrderDTO orderDTO = orderService.findByOrderId(orderId);
//        orderService.cancel(orderDTO);
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
