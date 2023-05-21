package com.lsc.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author lisc
 * @Description: com.lsc.tools.bean
 * @date 2022/8/23 10:23
 */
@Data
@Component
public class ClassBean {
    private String fileName;
    private String path;
    private String content;
}
