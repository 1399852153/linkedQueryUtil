import com.xiongyx.pojo.Customer;
import com.xiongyx.pojo.OrderForm;
import com.xiongyx.util.LinkedQueryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author xiongyx
 * @Create 2018/10/31.
 */

public class TestOneToOneLinked {

    public static void main(String[] args) throws Exception {
        //:::获得订单列表
        List<OrderForm> orderFormList = getOrderFormList();
        System.out.println("初始的订单列表");
        showList(orderFormList);

        //:::获得顾客列表
        List<Customer> customerList = getCustomerList();
        System.out.println("初始的顾客列表");
        showList(customerList);

        //:::将"订单"和"顾客" 进行一对一的连接(订单为主体,顾客被关联)
        LinkedQueryUtil.oneToOneLinked(orderFormList,"customerID","customer",customerList,"id");
        System.out.println("关联之后的订单列表");
        showList(orderFormList);
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

        orderFormList.add(new OrderForm(UUID.randomUUID().toString(),"11111"));
        orderFormList.add(new OrderForm(UUID.randomUUID().toString(),"22222"));
        orderFormList.add(new OrderForm(UUID.randomUUID().toString(),"33333"));
        orderFormList.add(new OrderForm(UUID.randomUUID().toString(),"11111"));
        orderFormList.add(new OrderForm(UUID.randomUUID().toString(),"33333"));
        orderFormList.add(new OrderForm(UUID.randomUUID().toString(),"22222"));
        orderFormList.add(new OrderForm(UUID.randomUUID().toString(),"33333"));
        orderFormList.add(new OrderForm(UUID.randomUUID().toString(),"55555"));

        return orderFormList;
    }

    /**
     * 打印list
     * */
    private static <T> void showList(List<T> list){
        list.forEach(System.out::println);
    }
}
