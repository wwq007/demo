package com.example.spring.demo.utils;

/**
 * @Author: wwq
 * @Date: 2020/2/26 17:57
 * @Description: xxxxx
 */
public class MathUtil {
    private static final Double MONEY_RANGE=0.01;
    public static boolean equals(Double b1,Double b2){
       Double result = Math.abs(b1-b2);
       if(result< MONEY_RANGE){
           return  true;
       }else{
           return  false;
       }
    }
}
