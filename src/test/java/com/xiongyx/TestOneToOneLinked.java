package com.xiongyx;

import com.xiongyx.pojo.Customer;
import com.xiongyx.pojo.OrderForm;
import com.xiongyx.util.CommonUtil;
import com.xiongyx.util.LinkedQueryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author xiongyx
 * @Create 2018/10/31.
 *
 * 一对一连接测试
 */

public class TestOneToOneLinked {

    public static void main(String[] args) throws Exception {
        //:::pojo 一对一连接测试
        testPojoOneToOneLinked();

        //:::map 一对一连接测试
        testMapOneToOneLinked();
    }

    private static void testPojoOneToOneLinked() throws Exception {
        System.out.println("pojo 一对一连接");

        //:::获得订单列表
        List<OrderForm> orderFormList = getOrderFormList();
        System.out.println("初始的订单列表");
        CommonUtil.showList(orderFormList);

        //:::获得顾客列表
        List<Customer> customerList = getCustomerList();
        System.out.println("初始的顾客列表");
        CommonUtil.showList(customerList);

        //:::将"订单"和"顾客" 进行一对一的连接(订单为主体,顾客被关联 modelName="customer")
        LinkedQueryUtil.oneToOneLinked(orderFormList,"customerID","customer",customerList,"id");
        System.out.println("关联之后的订单列表");
        CommonUtil.showList(orderFormList);
    }

    private static void testMapOneToOneLinked() throws Exception {
        System.out.println("map 一对一连接");

        //:::获得订单列表
        List<OrderForm> orderFormList = getOrderFormList();
        System.out.println("初始的订单列表");
        CommonUtil.showList(orderFormList);

        //:::获得顾客列表
        List<Map<String,Object>> customerMapList = getCustomerMapList();
        System.out.println("初始的顾客列表(map)");
        CommonUtil.showList(customerMapList);

        //:::将"订单"和"顾客" 进行一对一的连接(订单为主体,顾客被关联 modelName="customerMap")
        LinkedQueryUtil.oneToOneLinked(orderFormList,"customerID","customerMap",customerMapList,"id");
        System.out.println("关联之后的订单列表");
        CommonUtil.showList(orderFormList);
    }

    private static List<Customer> getCustomerList(){
        List<Customer> customerList = new ArrayList<>();

        customerList.add(new Customer("11111","小明"));
        customerList.add(new Customer("22222","小红"));
        customerList.add(new Customer("33333","小刚"));

        return customerList;
    }

    private static List<OrderForm> getOrderFormList(){
        List<OrderForm> orderFormList = new ArrayList<>();

        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"11111"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"22222"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"33333"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"11111"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"33333"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"22222"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"33333"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"55555"));

        return orderFormList;
    }

    private static List<Map<String,Object>> getCustomerMapList(){
        List<Customer> customerList = getCustomerList();

        return customerList.stream().map(Customer::toMap).collect(Collectors.toList());
    }
}
