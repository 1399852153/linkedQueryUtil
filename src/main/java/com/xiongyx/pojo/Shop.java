package com.xiongyx.pojo;

import java.util.List;
import java.util.Map;

/**
 * @Author xiongyx
 * @Create 2018/10/31.
 *
 * 门店类
 */

public class Shop {

    /**
     * 主键id
     * */
    private String id;

    /**
     * 门店名
     * */
    private String shopName;

    /**
     * 顾客列表 (一个门店关联N个订单 一对多)
     * */
    private List<OrderForm> orderFormList;

    /**
     * 顾客map列表 (一个门店关联N个订单 一对多)
     * */
    private List<Map<String,Object>> orderFormMapList;

    public Shop(String id, String shopName) {
        this.id = id;
        this.shopName = shopName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<OrderForm> getOrderFormList() {
        return orderFormList;
    }

    public void setOrderFormList(List<OrderForm> orderFormList) {
        this.orderFormList = orderFormList;
    }

    public List<Map<String, Object>> getOrderFormMapList() {
        return orderFormMapList;
    }

    public void setOrderFormMapList(List<Map<String, Object>> orderFormMapList) {
        this.orderFormMapList = orderFormMapList;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id='" + id + '\'' +
                ", shopName='" + shopName + '\'' +
                ", orderFormList=" + orderFormList +
                ", orderFormMapList=" + orderFormMapList +
                '}';
    }
}
