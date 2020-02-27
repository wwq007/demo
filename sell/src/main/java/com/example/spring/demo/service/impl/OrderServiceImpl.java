package com.example.spring.demo.service.impl;

import com.example.spring.demo.converter.OrderMaster2OrderDTOConverter;
import com.example.spring.demo.dao.OrderDetailDao;
import com.example.spring.demo.dao.OrderMasterDao;
import com.example.spring.demo.dto.CartDTO;
import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.entity.OrderDetail;
import com.example.spring.demo.entity.OrderMaster;
import com.example.spring.demo.entity.ProductInfo;
import com.example.spring.demo.enums.OrderStatusEnum;
import com.example.spring.demo.enums.PayStatusEnum;
import com.example.spring.demo.enums.ResultEnum;
import com.example.spring.demo.exception.SellException;
import com.example.spring.demo.service.OrderService;
import com.example.spring.demo.service.ProductInfoService;
import com.example.spring.demo.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wwq
 * @Date: 2020/2/21 23:36
 * @Description: xxxxx
 */
@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMasterDao orderMasterDao;
    @Autowired
    ProductInfoService productInfoService;
    @Autowired
    OrderDetailDao orderDetailDao;
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //1.查询商品.
        String orderId = KeyUtil.genUninqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findByProductId(orderDetail.getProductId());
            if(productInfo ==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            //2.计算金额.
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //写入详情表
            orderDetail.setDetailId(KeyUtil.genUninqueKey());
            orderDetail.setOrderId(orderId);
            orderDetail.setProductName(productInfo.getProductName());
            orderDetail.setProductPrice(productInfo.getProductPrice());
            orderDetail.setProductIcon(productInfo.getProductIcon());
//            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailDao.save(orderDetail);
        }
        //写入订单表
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.UNPAY.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMasterDao.save(orderMaster);
        //扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findByOrderId(String orderId) {
        OrderMaster orderMaster  = orderMasterDao.findByOrderId(orderId);
        if(orderMaster == null){
            log.error("【查询订单】找不到订单.orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findByBuyerOpenid(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();
        Integer orderStatus = orderDTO.getOrderStatus();
        String orderId=orderDTO.getOrderId();
        //判断订单状态,新下单的才能取消
        if(!orderStatus.equals(OrderStatusEnum.NEW.getCode())){
           log.error("【取消订单失败】orderId={},orderStatus={}",orderId,orderStatus);
           throw new SellException(ResultEnum.ORDER_CANCEL_ERROE);
        }

        //取消订单
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult == null){
            log.error("【取消订单】订单更新失败 orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        //返库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中没有商品 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_ERROR);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        Integer orderStatus = orderDTO.getOrderStatus();
        if(!orderStatus.equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结】订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATU_ERROR);
        }
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMasterDao.save(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态.
        Integer orderStatus = orderDTO.getOrderStatus();
        if(!orderStatus.equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单支付】订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATU_ERROR);
        }
        //判断支付状态.
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.UNPAY.getCode())){
            log.error("【订单支付】订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_STATU_ERROR);
        }
        //修改订单支付状态为支付完成.
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult == null) {
            log.error("【订单支付】 更新失败 orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
       Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }
}
