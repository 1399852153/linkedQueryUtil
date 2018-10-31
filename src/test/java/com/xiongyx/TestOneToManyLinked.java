package com.xiongyx;

import com.xiongyx.pojo.OrderForm;
import com.xiongyx.pojo.Shop;
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
 * 一对多连接测试
 */

public class TestOneToManyLinked {

    public static void main(String[] args) throws Exception {
        //:::pojo 一对一连接测试
        testPojoOneToManyLinked();

        //:::map 一对一连接测试
        testMapOneToManyLinked();
    }

    private static void testPojoOneToManyLinked() throws Exception {
        System.out.println("pojo 一对多连接");

        //:::获得门店列表
        List<Shop> shopList = getShopList();
        System.out.println("初始的门店列表");
        CommonUtil.showList(shopList);

        //:::获得订单列表
        List<OrderForm> orderFormList = getOrderFormList();
        System.out.println("初始的订单列表");
        CommonUtil.showList(orderFormList);

        //:::将"门店"和"订单" 进行一对多的连接(门店为主体,订单被关联 modelName="orderFormList")
        LinkedQueryUtil.oneToManyLinked(shopList,"id","orderFormList",orderFormList,"shopID");
        System.out.println("关联之后的门店列表");
        CommonUtil.showList(shopList);
    }

    private static void testMapOneToManyLinked() throws Exception {
        System.out.println("map 一对多连接");

        //:::获得门店列表
        List<Shop> shopList = getShopList();
        System.out.println("初始的门店列表");
        CommonUtil.showList(shopList);

        //:::获得订单列表
        List<Map<String,Object>> orderFormMapList = getOrderFormMapList();
        System.out.println("初始的订单map列表");
        CommonUtil.showList(orderFormMapList);

        //:::将"门店"和"订单" 进行一对多的连接(门店为主体,订单被关联 modelName="orderFormMapList")
        LinkedQueryUtil.oneToManyLinked(shopList,"id","orderFormMapList",orderFormMapList,"shopID");
        System.out.println("关联之后的门店列表");
        CommonUtil.showList(shopList);
    }

    private static List<Shop> getShopList(){
        List<Shop> shopList = new ArrayList<>();

        shopList.add(new Shop("shopID11111","门店1"));
        shopList.add(new Shop("shopID22222","门店2"));
        shopList.add(new Shop("shopID33333","门店3"));
        shopList.add(new Shop("shopID44444","门店4"));
        shopList.add(new Shop("shopID55555","门店5"));

        return shopList;
    }

    private static List<OrderForm> getOrderFormList(){
        List<OrderForm> orderFormList = new ArrayList<>();

        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"shopID11111","12345"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"shopID22222","67890"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"shopID33333","32131"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"shopID11111","32142"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"shopID33333","32131"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"shopID22222","32131"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"shopID33333","11111"));
        orderFormList.add(new OrderForm(CommonUtil.getUUID(),"shopID55555","53421"));

        return orderFormList;
    }

    private static List<Map<String,Object>> getOrderFormMapList(){
        List<OrderForm> customerList = getOrderFormList();

        return customerList.stream().map(OrderForm::toMap).collect(Collectors.toList());
    }
}
