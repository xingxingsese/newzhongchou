package com.lsc.freemarker.facade;

import com.lsc.freemarker.base.FreeMarkerDataModel;
import com.lsc.freemarker.base.FreeMarkerServiceTemplate;
import com.lsc.freemarker.core.CustomClassLoader;
import com.lsc.freemarker.core.DataBaseTableToJava;
import com.lsc.freemarker.core.FreeMarkerGenerator;
import com.lsc.freemarker.entity.FreeMarkerDataBean;
import com.lsc.freemarker.entity.MockResult;
import com.lsc.freemarker.entity.SetMethodBean;
import com.lsc.freemarker.enums.ResultOutPathEnum;
import com.lsc.freemarker.enums.TemplatePathEnum;
import com.lsc.freemarker.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lisc
 * @Description: 聚合所有mock代码
 * @date 2022/11/11 17:05
 */
@Slf4j
public class FreeMarkerCodeFacade {

    /**
     * 数据库相关的moke功能
     */
    private DataBaseTableToJava dataBaseTableToJava;

    /**
     * mock模板
     */
    private FreeMarkerServiceTemplate freeMarkerServiceTemplate;

    /**
     * mock代码实现
     */
    private FreeMarkerGenerator freeMarkerGenerator;

    /**
     * 根据表名获取数据库内表内的列
     *
     * @param tableName
     * @return
     */
    public MockResult<Boolean> tableSqlCodeGeneration(String tableName) {

        boolean sqlCodeGeneration = dataBaseTableToJava.tableSqlCodeGeneration(tableName);

        return MockResult.buildSuccessResult(sqlCodeGeneration);
    }


    /**
     * 根据传入的全类名 获取该ben的set方法
     *
     * @param beanName
     * @return
     */
    public MockResult<Boolean> setMethodCodeGeneration(String beanName) {
        ClassLoader classLoader = new CustomClassLoader();

        MockResult result = new MockResult();

        freeMarkerServiceTemplate.execute(result, TemplatePathEnum.SET_METHOD_PATH, ResultOutPathEnum.SET_METHOD_PATH, new FreeMarkerDataModel<SetMethodBean>() {
            @Override
            public SetMethodBean buildModelData() throws Exception {
                Class<?> aClass = classLoader.loadClass(beanName);
                List<Field> fieldList = ReflectionUtil.getDeclaredFields(aClass);

                ArrayList<Method> methods = new ArrayList<>();
                for (Field field : fieldList) {
                    Method method = ReflectionUtil.getObjectSetMethod(aClass, field);
                    if (null != method) {
                        methods.add(method);
                    }
                }
                log.info("set方法执行完毕");
                List<String> methodList = methods.stream().map(Method::getName).collect(Collectors.toList());
                return SetMethodBean.builder().className(aClass.getSimpleName()).methodsName(methodList).build();

            }
        });

        return result;
    }


    /**
     * mock代码生成
     *
     * @param className 需要生产mock代码的类的全路径
     * @return
     */
    public MockResult<Boolean> mockCodeGeneration(String className) {
        MockResult result = new MockResult();
        freeMarkerServiceTemplate.execute(result, TemplatePathEnum.MOCK_TEMPLATE_PATH, ResultOutPathEnum.MOCK_RESULT_PATH, new FreeMarkerDataModel() {
            @Override
            public Object buildModelData() throws Exception {

                FreeMarkerDataBean mockResult = freeMarkerGenerator.classPathCreateMock(className, ResultOutPathEnum.MOCK_RESULT_PATH.getPath());
                return mockResult;
            }
        });
        return result;
    }

    @Test
    public void test() {
        MockResult<Boolean> result = mockCodeGeneration("com.ipay.ibizecoprod.biz.asynctask.taskschedule.IbepCommonTaskRecordExecuter");
        // MockResult<Boolean> result = mockCodeGeneration("com.ipay.ibizecoprod.core.service.commonTask.impl.CommonTaskRecordServiceImpl");
        System.out.println("result = " + result);
    }
}
