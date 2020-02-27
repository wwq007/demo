package com.example.spring.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**类目表
 * create by 浦江
 * 2020/2/17
 */

@Entity
@Data
@DynamicUpdate
public class ProductCategory {
    //类目id
    @Id

    private Integer categoryId;
    //类目名字
    private String categoryName;
    //类目编号
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;

    public ProductCategory(){

    }

}
