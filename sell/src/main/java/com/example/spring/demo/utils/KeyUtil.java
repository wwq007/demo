package com.example.spring.demo.utils;

import java.util.Random;

/**
 * @Author: wwq
 * @Date: 2020/2/22 11:10
 * @Description:
 */
public class KeyUtil {
    /**
     * 生成唯一主键
     * 格式：时间毫秒数+随机数
     * @return
     */
    public static synchronized String genUninqueKey(){
        Random random = new Random();

        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
