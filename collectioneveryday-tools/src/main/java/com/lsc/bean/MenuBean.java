package com.lsc.bean;

import lombok.Data;

import java.util.List;

/**
 * @author lisc
 * @Description: com.lsc.tools.bean 菜单集合
 * @date 2022/9/23 17:13
 */
@Data
public class MenuBean {
    private String name;

    private List<UiBean> uiBeanList;


}
