package com.lsc.tools.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lisc
 * @Description: 各个跳转按钮或标签
 * JUMP 结尾的为跳转按钮
 * BUILD 结尾的为业务逻辑生成按钮
 * @date 2022/10/24 19:11
 */
@Getter
@AllArgsConstructor
public enum JumpBtnType {

    // 一级菜单栏
    ENORDE_CRYPTION_JMENU_JUMP("JMenu", "加解密", "一级菜单栏", "标签栏"),
    CODE_GENERATOR_JMENU_JUMP("JMenu", "代码生成", "一级菜单栏", "标签栏"),
    CODE_TOOL_JMENU_JUMP("JMenu", "代码工具", "一级菜单栏", "标签栏"),


    // 二级菜单栏
    ENCRYPTION_JMENU_JUMP("JMenuItem", "GS加密", "ENORDE_CRYPTION_JMENU_JUMP", "二级菜单栏"),

    TOOL_LIST_JMENU_JUMP("JMenuItem", "工具集合", "CODE_TOOL_JMENU_JUMP", "二级菜单栏"),

    MOCK_CODE_GENERATOR_JMENU_JUMP("JMenuItem", "MOCK代码生成", "CODE_GENERATOR_JMENU_JUMP", "二级菜单栏"),
    CRUD_CODE_GENERATOR_JMENU_JUMP("JMenuItem", "CRUD代码生成", "CODE_GENERATOR_JMENU_JUMP", "二级菜单栏"),


    // 切换按钮
    OBJECT_TO_JSON_JBUTTON_JUMP("JButton", "对象转JSON", "TOOL_LIST_JMENU_JUMP", "执行按钮-CRUD代码生成按钮"),


    // 执行按钮
    ENCRYPTION_JBUTTON_BUILD("JButton", "加密", "ENCRYPTION_JMENU_JUMP", "执行按钮"),
    DECRYPTION_JBUTTON_BUILD("JButton", "解密", "ENCRYPTION_JMENU_JUMP", "执行按钮"),

    MOCK_GENERATOR_JBUTTON_BUILD("JButton", "生成", "MOCK_CODE_GENERATOR_JMENU_JUMP", "执行按钮-MOCK代码生成按钮"),
    CRUD_GENERATOR_JBUTTON_BUILD("JButton", "生成", "CRUD_CODE_GENERATOR_JMENU_JUMP", "执行按钮-CRUD代码生成按钮"),

    OBJECT_TO_JSON__JBUTTON_BUILD("JButton", "生成", "TOOL_LIST_JMENU_JUMP", "执行按钮-CRUD代码生成按钮"),
    ;
    /**
     * 按钮类型
     */
    private String btnType;
    /**
     * 按钮名称
     */
    private String btnName;
    /**
     * 上级按钮名称
     */
    private String parentTtnName;
    /**
     * 按钮描述
     */
    private String desc;


}
