package com.xiongyx.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiongyx
 * @Create 2018/10/31.
 *
 * 顾客类
 */

public class Customer {

    /**
     * 主键id
     * */
    private String id;

    /**
     * 姓名
     * */
    private String userName;

    public Customer(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();

        map.put("id",this.id);
        map.put("userName",this.userName);

        return map;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
