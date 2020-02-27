package com.example.spring.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @Author: wwq
 * @Date: 2020/2/24 13:20
 * @Description: xxxxx
 */
@RestController
@RequestMapping(value="/weixin")
@Slf4j
public class WeixinController {
    @GetMapping(value="/auth")
    public void test(@RequestParam("code")String code){
        log.info("路径测试");
        log.info("code={}",code);
       String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx24e401c147ff77f4&secret=fd9abb89f170c9f94a4343db8fef1c7d&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String restsult = restTemplate.getForObject(url,String.class);
        log.info("url={}",restsult);
    }
}
