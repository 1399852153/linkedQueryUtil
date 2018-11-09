package com.xiongyx.util;

import java.util.List;
import java.util.UUID;

/**
 * @Author xiongyx
 * @Create 2018/10/31.
 */

public class CommonUtil {

    /**
     * 打印list
     * */
    public static <T> void showList(List<T> list){
        list.forEach(System.out::println);

        System.out.println();
    }

    public static String getUUID(){
        long uuid;
        do {
            uuid = UUID.randomUUID().getMostSignificantBits();
        } while(uuid <= 0L);

        return uuid + "";
    }
}
