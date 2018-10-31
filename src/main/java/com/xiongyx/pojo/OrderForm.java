package com.xiongyx.pojo;

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
     * 关联的顾客id
     * */
    private String customerID;

    /**
     * 关联的顾客model
     * */
    private Customer customer;

    public OrderForm(String id, String customerID) {
        this.id = id;
        this.customerID = customerID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "OrderForm{" +
                "id='" + id + '\'' +
                ", customerID='" + customerID + '\'' +
                ", customer=" + customer +
                '}';
    }
}