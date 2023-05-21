package com.lsc.freemarker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.entity
 * @date 2022/11/14 11:07
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SetMethodBean {

    private String className;

    private List<String> methodsName;
}
