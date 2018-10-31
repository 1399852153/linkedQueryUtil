package com.xiongyx.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xiongyx
 * @Create 2018/10/31.
 *
 * 关联查询工具类
 */

public class LinkedQueryUtil {

    /**
     * 一对一连接 :  beanKeyName <---> dataKeyName 作为连接条件
     *
     * @param beanList 需要被存放数据的beanList(主体)
     * @param beanKeyName   beanList中连接字段key的名字
     * @param beanModelName  beanList中用来存放匹配到的数据value的属性
     * @param dataList  需要被关联的data列表
     * @param dataKeyName 需要被关联的data中连接字段key的名字
     *
     * @throws Exception
     */
    public static void oneToOneLinked(List beanList, String beanKeyName, String beanModelName, List dataList, String dataKeyName) throws Exception {
        //:::如果不需要转换,直接返回
        if(!needTrans(beanList,dataList)){
            return;
        }

        //:::将被关联的数据列表,以需要连接的字段为key,转换成map,加快查询的速度
        Map<String,Object> dataMap = beanListToMap(dataList,dataKeyName);

        //:::进行数据匹配链接
        matchedDataToBeanList(beanList,beanKeyName,beanModelName,dataMap);
    }

    /**
     * 一对多连接 :  oneKeyName <---> manyKeyName 作为连接条件
     *
     * @param oneDataList       '一方' 数据列表
     * @param oneKeyName        '一方' 连接字段key的名字
     * @param oneModelName      '一方' 用于存放 '多方'数据的列表属性名
     * @param manyDataList      '多方' 数据列表
     * @param manyKeyName       '多方' 连接字段key的名字
     *
     *  注意:  '一方' 存放 '多方'数据的属性类型必须为List
     *
     * @throws Exception
     */
    public static void oneToManyLinked(List oneDataList,String oneKeyName,String oneModelName,List manyDataList,String manyKeyName) throws Exception {
        if(!needTrans(oneDataList,manyDataList)){
            return;
        }

        //:::将'一方'数据,以连接字段为key,转成map,便于查询
        Map<String,Object> oneDataMap = beanListToMap(oneDataList,oneKeyName);

        //:::获得'一方'存放 '多方'数据字段的get方法名
        String oneDataModelGetMethodName = makeGetMethodName(oneModelName);
        //:::获得'一方'存放 '多方'数据字段的set方法名
        String oneDataModelSetMethodName = makeSetMethodName(oneModelName);

        //:::获得'多方'连接字段的get方法名
        String manyDataKeyGetMethodName = makeGetMethodName(manyKeyName);

        try {
            //:::遍历'多方'列表
            for (Object manyDataItem : manyDataList) {
                //:::'多方'对象连接key的值
                String manyDataItemKey;

                //:::判断当前'多方'对象的类型是否是 hashMap
                if(manyDataItem.getClass() == HashMap.class){
                    //:::如果是hashMap类型的,先转为Map对象
                    Map manyDataItemMap = (Map)manyDataItem;

                    //:::通过参数key 直接获取对象key连接字段的值
                    manyDataItemKey = (String)manyDataItemMap.get(manyKeyName);
                }else{
                    //:::如果是普通的pojo对象,则通过反射获得get方法来获取key连接字段的值

                    //:::获得'多方'数据中key的method对象
                    Method manyDataKeyGetMethod = manyDataItem.getClass().getMethod(manyDataKeyGetMethodName);

                    //:::调用'多方'数据的get方法获得当前'多方'数据连接字段key的值
                    manyDataItemKey = (String) manyDataKeyGetMethod.invoke(manyDataItem);
                }

                //:::通过'多方'的连接字段key从 '一方' map集合中查找出连接key相同的 '一方'数据对象
                Object matchedOneData = oneDataMap.get(manyDataItemKey);

                //:::如果匹配到了数据,才进行操作
                if(matchedOneData != null){
                    //:::将当前迭代的 '多方'数据 放入 '一方' 的对应的列表中
                    setManyDataToOne(matchedOneData,manyDataItem,oneDataModelGetMethodName,oneDataModelSetMethodName);
                }
            }
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    /**
     * 将javaBean组成的list去重 转为map, key为bean中指定的一个属性
     *
     * @param beanList list 本身
     * @param keyName 生成的map中的key
     * @return
     * @throws Exception
     */
    public static Map<String,Object> beanListToMap(List beanList,String keyName) throws Exception{
        //:::创建一个map
        Map<String,Object> map = new HashMap<>();

        //:::由keyName获得对应的get方法字符串
        String getMethodName = makeGetMethodName(keyName);

        try {
            //:::遍历beanList
            for(Object obj : beanList){
                //:::如果当前数据是hashMap类型
                if(obj.getClass() == HashMap.class){
                    Map currentMap = (Map)obj;

                    //:::使用keyName从map中获得对应的key
                    String result = (String)currentMap.get(keyName);

                    //:::放入map中(如果key一样,则会被覆盖去重)
                    map.put(result,currentMap);
                }else{
                    //:::否则默认是pojo对象
                    //:::获得get方法
                    Method getMethod = obj.getClass().getMethod(getMethodName);

                    //:::通过get方法从bean对象中得到数据key
                    String result = (String)getMethod.invoke(obj);

                    //:::放入map中(如果key一样,则会被覆盖去重)
                    map.put(result,obj);
                }
            }
        }catch(Exception e){
            throw new Exception(e);
        }

        //:::返回结果
        return map;
    }

    //=================================================================辅助函数===========================================================
    /***
     * 将通过keyName获得对应的bean对象的get方法名称的字符串
     * @param keyName 属性名
     * @return  返回get方法名称的字符串
     */
    private static String makeGetMethodName(String keyName){
        //:::将第一个字母转为大写
        String newKeyName = transFirstCharUpperCase(keyName);

        return "get" + newKeyName;
    }

    /***
     * 将通过keyName获得对应的bean对象的set方法名称的字符串
     * @param keyName 属性名
     * @return  返回set方法名称的字符串
     */
    private static String makeSetMethodName(String keyName){
        //:::将第一个字母转为大写
        String newKeyName = transFirstCharUpperCase(keyName);

        return "set" + newKeyName;
    }

    /**
     * 将字符串的第一个字母转为大写
     * @param str 需要被转变的字符串
     * @return 返回转变之后的字符串
     */
    private static String transFirstCharUpperCase(String str){
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }

    /**
     * 判断当前的数据是否需要被转换
     *
     * 两个列表存在一个为空,则不需要转换
     * @return 不需要转换返回 false,需要返回 true
     * */
    private static boolean needTrans(List beanList,List dataList){
        if(listIsEmpty(beanList) || listIsEmpty(dataList)){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 列表是否为空
     * */
    private static boolean listIsEmpty(List list){
        if(list == null || list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 将批量查询出来的数据集合,组装到对应的beanList之中
     * @param beanList 需要被存放数据的beanList(主体)
     * @param beanKeyName   beanList中用来匹配数据的属性
     * @param beanModelName  beanList中用来存放匹配到的数据的属性
     * @param dataMap  data结果集以某一字段作为key对应的map
     * @throws Exception
     */
    private static void matchedDataToBeanList(List beanList, String beanKeyName, String beanModelName, Map<String,Object> dataMap) throws Exception {
        //:::获得beanList中存放对象的key的get方法名
        String beanGetMethodName = makeGetMethodName(beanKeyName);
        //:::获得beanList中存放对象的model的set方法名
        String beanSetMethodName = makeSetMethodName(beanModelName);

        try{
            //:::遍历整个beanList
            for(Object bean : beanList){
                //:::获得bean中key的method对象
                Method beanGetMethod = bean.getClass().getMethod(beanGetMethodName);

                //:::调用获得当前的key
                String currentBeanKey = (String)beanGetMethod.invoke(bean);

                //:::从被关联的数据集map中找到匹配的数据
                Object matchedData = dataMap.get(currentBeanKey);

                //:::如果找到了匹配的对象
                if(matchedData != null){
                    //:::获得bean中对应model的set方法
                    Class clazz = matchedData.getClass();

                    //:::如果匹配到的数据是hashMap
                    if(clazz == HashMap.class){
                        //:::转为父类map class用来调用set方法
                        clazz = Map.class;
                    }

                    //:::获得主体bean用于存放被关联对象的set方法
                    Method beanSetMethod = bean.getClass().getMethod(beanSetMethodName,clazz);

                    //:::执行set方法,将匹配到的数据放入主体数据对应的model属性中
                    beanSetMethod.invoke(bean,matchedData);
                }
            }
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    /**
     * 将 '多方' 数据存入 '一方' 列表中
     * @param oneData 匹配到的'一方'数据
     * @param manyDataItem  当前迭代的 '多方数据'
     * @param oneDataModelGetMethodName 一方列表的get方法名
     * @param oneDataModelSetMethodName 一方列表的set方法名
     * @throws Exception
     */
    private static void setManyDataToOne(Object oneData,Object manyDataItem,String oneDataModelGetMethodName,String oneDataModelSetMethodName) throws Exception {
        //:::获得 '一方' 数据中存放'多方'数据属性的get方法
        Method oneDataModelGetMethod = oneData.getClass().getMethod(oneDataModelGetMethodName);

        //::: '一方' 数据中存放'多方'数据属性的set方法
        Method oneDataModelSetMethod;
        try {
            //::: '一方' set方法对象
            oneDataModelSetMethod = oneData.getClass().getMethod(oneDataModelSetMethodName,List.class);
        }catch(NoSuchMethodException e){
            throw new Exception("未找到满足条件的'一方'set方法");
        }

        //:::获得存放'多方'数据get方法返回值类型
        Class modelType = oneDataModelGetMethod.getReturnType();

        //::: get方法返回值必须是List
        if(modelType.equals(List.class)){
            //:::调用get方法,获得数据列表
            List modelList = (List)oneDataModelGetMethod.invoke(oneData);

            //:::如果当前成员变量为null
            if(modelList == null){
                //:::创建一个新的List
                List newList = new ArrayList<>();

                //:::将当前的'多方'数据存入list
                newList.add(manyDataItem);

                //:::将这个新创建出的List赋值给 '一方'的对象
                oneDataModelSetMethod.invoke(oneData,newList);
            }else{
                //:::如果已经存在了List

                //:::直接将'多方'数据存入list
                modelList.add(manyDataItem);
            }

        }else{
            throw new Exception("一对多连接时,一方指定的model对象必须是list类型");
        }
    }
}
