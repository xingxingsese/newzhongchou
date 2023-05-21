package com.lsc.demo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lsc.bean.MenuBean;
import com.lsc.bean.UiBean;
import com.lsc.utils.JunitBusiUtil;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author lisc
 * @Description: com.lsc.tools.demo
 * @date 2022/9/23 16:56
 */
public class DemoOne {

    public static void main(String[] args) throws IOException {
        // UiBean uiBean = new UiBean();
        com.lsc.bean.MenuBean menuBean = new com.lsc.bean.MenuBean();
        menuBean.setName("test1");
        UiBean uiBean = new UiBean();
        uiBean.setActivation(true);
        uiBean.setBtnType(com.lsc.tools.enums.BtnTypeEnum.J_BUTTON);
        uiBean.setMenuName("test1");
        menuBean.setUiBeanList(Arrays.asList(uiBean));
        // StringBuildUtils.classJsonString(uiBean,"D:\\Code\\TestCode\\newzhongchou\\everyday-tools\\src\\main\\resources\\com.lsc.tools.btn.do\\");

        System.out.println("WriteMapNullValue = " + JSONObject.toJSONString(menuBean, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));

        MenuBean object = JunitBusiUtil.parseObject("/com/lsc/tools/btn/do/UiBean.json", new TypeReference<MenuBean>() {
        });

        System.out.println(JSONObject.toJSONString(object));

    }

    @Test
    public void test() throws IllegalAccessException, InstantiationException {

        Class<MenuBean> menuBeanClass = MenuBean.class;
        MenuBean newInstance = menuBeanClass.newInstance();
        // 获取当前类所有属性
        Field[] declaredFields = newInstance.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            Class<?> type = field.getType();
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] types = parameterizedType.getActualTypeArguments();
                for (Type type1 : types) {
                    if (!type1.getTypeName().contains("java.lang")) {
                        System.out.println("泛型有:" + type1);
                    }
                }
            }
            System.out.println("字段类型:" + type);
        }

    }

    @Test
    public void test02() throws Exception {
        Class<MenuBean> menuBeanClass = MenuBean.class;
        MenuBean newInstance = menuBeanClass.newInstance();

        Class<? extends MenuBean> newInstanceClass = newInstance.getClass();

        Method[] methods = newInstanceClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().contains("set")) {

            }
            method.invoke(newInstance, method.getDefaultValue());
        }
        // 获取当前类所有属性
        Field[] fields = newInstanceClass.getFields();
    }

    @Test
    public void test03() {
        String paths = "D:\\Code\\TestCode\\newzhongchou\\everyday-tools\\src\\main\\java\\com\\lsc\\tools\\listener\\BtnEncryListener.java" +
                "D:\\Code\\TestCode\\newzhongchou\\everyday-tools\\src\\main\\java\\com\\lsc\\tools\\listener\\GsEncryView.java" +
                "D:\\Code\\TestCode\\newzhongchou\\everyday-tools\\src\\main\\java\\com\\lsc\\tools\\listener\\GuiListener.java";

        String[] split = paths.split("\r\n");
        List<String> asList = Arrays.asList(split);
        System.out.println(asList);
        System.out.println("asList.size() = " + asList.size());
        System.out.println("System.lineSeparator() = " + System.lineSeparator());
    }

    @Test
    public void test04() throws IOException {
        String url = "D:\\Code\\TestCode\\newzhongchou\\everyday-tools\\src\\main\\resources\\demo.txt";
        List<String> list = com.lsc.common.FileUtils.readFileAllLinesReturnList(url);

        String split1 = "========";
        String split2 = "----";
      /*  ArrayList<HashMap<Integer, String>> strings = new ArrayList<>();
        HashMap<Integer, String> map = null;
        int j = 1;
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).contains(split1)) {
                if (null == map) {
                    map = new HashMap<>();
                    continue;
                }
                strings.add(map);
                map = new HashMap<>();
                j = 1;
                continue;
            }
            if (list.get(i).contains(split2)) continue;
            map.put(j, list.get(i));
            j++;
        }
        System.out.println("JSONObject.toJSONString(listMap) = " + JSONObject.toJSONString(strings));*/


    /*    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        String keyStr = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(split1) || list.get(i).contains(split2)) {
                continue;
            }
            if (split2.contains(list.get(i + 1)) && split2.contains(list.get(i - 1))) {
                keyStr = list.get(i);
                continue;
            }
            stringBuffer.append(list.get(i));
            if (split2.contains(list.get(i + 1))) {
                HashMap<String, String> jsonMap = new HashMap<>();

                jsonMap.put(keyStr, stringBuffer.toString());
                arrayList.add(jsonMap);
                keyStr=null;
            }

        }
       // System.out.println("JSONObject.toJSONString(arrayList) = " + JSONObject.toJSONString(arrayList, SerializerFeature.PrettyFormat));
        System.out.println("JSONObject.toJSONString(arrayList) = " + JSONObject.toJSONString(arrayList));*/


        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        HashMap<String, String> jsonMap = null;
        String keyStr = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(split1) || list.get(i).equals(split2)) {
                continue;
            }

            if (split2.contains(list.get(i + 1)) && split2.equals(list.get(i - 1))) {
                jsonMap = new HashMap<>();
                jsonMap.put(String.valueOf(jsonMap.size()), list.get(i));
                continue;
            }
            stringBuffer.append(list.get(i));
            if (split2.equals(list.get(i + 1))) {
                jsonMap.put(String.valueOf(jsonMap.size()), stringBuffer.toString());
                arrayList.add(jsonMap);

            }

        }
        // System.out.println("JSONObject.toJSONString(arrayList) = " + JSONObject.toJSONString(arrayList, SerializerFeature.PrettyFormat));
        System.out.println("JSONObject.toJSONString(arrayList) = " + JSONObject.toJSONString(arrayList));
    }

    // https://www.cnblogs.com/byerichas/p/15629371.html  Java--ClassLoader 类加载机制与重写类加


    /**
     * 正则判断
     *
     * @param name    内容
     * @param pattern 正则表达式
     * @return
     */
    public boolean panduanZZBDS(String name, String pattern) {
        return Pattern.compile(pattern).matcher(name).matches();
    }
}
