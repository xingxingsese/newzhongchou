package com.lsc.controller;

import com.alibaba.fastjson.JSON;
import com.lsc.api.FreeMarkerService;
import com.lsc.bean.CodeBean;
import com.lsc.common.AssertUtils;
import com.lsc.common.ExceptionCode;
import com.lsc.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/5
 */
@RestController
public class FreeMarkerController {
    private static final Logger log = LoggerFactory.getLogger(FreeMarkerController.class);

    @Autowired
    private FreeMarkerService freeMarkerService;

    /**
     * 根据json文件生成java代码
     *
     */
    @PostMapping("/freemarker/jsonbuildcode")
    public String beanJsonBuildCode(CodeBean request) {
        log.info("接收到的数据为:{}", JSON.toJSONString(request));

        HashMap<String, Object> map = new HashMap<>();
        map.put("projectPath",request.getProjectPath());
        map.put("ClassName",request.getClassName());
        map.put("MapClassName",request.getMapClass());
        map.put("date",new Date());
        String outPath = request.getOutPath();
        Boolean result = freeMarkerService.beanJsonBuildCode(Constant.JAVA_CODE_TEMPLATE_PATH, outPath, map);


        return result?Constant.OK:Constant.FAIL;
    }



}
