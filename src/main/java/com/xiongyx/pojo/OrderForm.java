package com.xiongyx.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiongyx
 * @Create 2018/10/31.
 *
 * 订单类
 */

public class OrderForm {

    /**
     * 主键id
     * */
    private String id;

    /**
     * 所属门店id
     * */
    private String shopID;

    /**
     * 关联的顾客id
     * */
    private String customerID;

    /**
     * 关联的顾客model
     * */
    private Customer customer;

    /**
     * 关联的顾客map
     * */
    private Map<String,Object> customerMap;

    public OrderForm(String id, String customerID) {
        this.id = id;
        this.customerID = customerID;
    }

    public OrderForm(String id, String shopID, String customerID) {
        this.id = id;
        this.shopID = shopID;
        this.customerID = customerID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<String, Object> getCustomerMap() {
        return customerMap;
    }

    public void setCustomerMap(Map<String, Object> customerMap) {
        this.customerMap = customerMap;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();

        map.put("id",this.id);
        map.put("shopID",this.shopID);
        map.put("customerID",this.customerID);

        return map;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "id='" + id + '\'' +
                ", shopID='" + shopID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", customer=" + customer +
                ", customerMap=" + customerMap +
                '}';
    }
}
