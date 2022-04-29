package com.lsc.controller;

import com.alibaba.fastjson.JSONObject;
import com.lsc.bean.TAdmin;
import com.lsc.bean.TAdminExample;
import com.lsc.mapper.TAdminMapper;
import com.lsc.service.impl.AdminServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/4/20
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminCrudControllerTest {

    @Spy
    @InjectMocks
    AdminServiceImpl adminService;

    @Mock
    TAdminMapper tAdminMapper;

    //初始化Mock对象
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addAdmin() {
     //   Mockito.when(tAdminMapper.selectByExample(any(TAdminExample.class))).thenReturn(new ArrayList<>());
       /* ArrayList<TAdmin> list = new ArrayList<>();
        Mockito.when(tAdminMapper.selectByExample(any())).thenReturn(list);
        adminService.selectLogInfo("001","001");*/
        Path path = Paths.get("WEB-INF/views/permission/role.jsp");
        System.out.println("path = " + path);
        System.out.println("path.getFileName() = " + path.getFileName());
        System.out.println("path.getFileSystem() = " + path.getFileSystem());
        System.out.println("path.getRoot() = " + path.getRoot());


    }

    @Test
    public void test(){
        List<String> list = new ArrayList<>();
        list.add("site");
        TestBean testBean = new TestBean();
        testBean.setName("test");
        testBean.setList(list);
        testBean.setStringList(JSONObject.toJSONString(list));

        System.out.println("JSONObject.toJSONString(list) = " + JSONObject.toJSONString(testBean));
    }
}