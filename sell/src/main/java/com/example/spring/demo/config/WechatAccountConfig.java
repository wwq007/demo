package com.example.spring.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: wwq
 * @Date: 2020/2/24 19:23
 * @Description: xxxxx
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    private String mpAppId;
    private String mpAppSecret;

    //商户号
    private String mchId;
    //商户秘钥
    private String mchKey;
    //商户证书路径
    private String KeyPath;
    //微信支付异步通知
    private String notifyUrl;
}
