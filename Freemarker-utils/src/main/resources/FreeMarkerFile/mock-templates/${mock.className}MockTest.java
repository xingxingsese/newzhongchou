package ${mock.classPath};

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.testng.annotations.BeforeMethod;
import com.alibaba.fastjson.JSON;

import static org.mockito.ArgumentMatchers.any;

/*
 * @author lisc
 * @Description: ${mock.classPath}.${mock.className}
 * @date ${date?datetime}
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class ${mock.className}MockTest extends IbizcoprodJunitTestBase{

        @Spy
        @InjectMocks
        private ${mock.className} ${mock.className?uncap_first};

        <#list mock.fieldBeanList as fieldBeans>
        @Mock
        private ${fieldBeans.fieldType} ${fieldBeans.fieldName};
        </#list>


        @BeforeMethod
        public void initMocks(){
               MockitoAnnotations.initMocks(this);
         }

<#-- 遍历所有方法-->
<#list mock.methodBeanList as methodBeans>

        @Test
        public void ${methodBeans.methodName}Test() {
            try {

                /**
                 * tip:
                 * 正常流程
                 */
                reset(this);

                <#list methodBeans.methodRequestType as request>
                ${request.fieldType}  ${request.fieldName}0010 = parseObject("/${request.fieldPathAndName}.json",${request.fieldType}.class);
                </#list>

                //stub
              //  Mockito.doNothing().when(xxx).xxx(xxx);
              //  Mockito.when().thenReturn();
                //fire
                ${mock.className?uncap_first}.${methodBeans.methodName}(<#list  methodBeans.methodRequestType as par>${par.fieldName}0010 <#sep>, </#sep></#list>);

            } catch (Exception e) {
                logger.error("${mock.className}:${methodBeans.methodName}:系统异常,原始入参:{} 异常文本:{} 异常栈:", JSON.toJSONString(""), e.getMessage(), e);
                Assert.fail();
            }
        }

</#list>
}
