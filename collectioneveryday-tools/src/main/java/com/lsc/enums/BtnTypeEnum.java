package com.lsc.tools.enums;

import lombok.Getter;

/**
 * @author lisc
 * @Description: com.lsc.tools swing各类按钮枚举
 * @date 2022/9/23 16:20
 */
@Getter
public enum BtnTypeEnum {
    J_BUTTON("JButton", "按钮", "javax.swing.JButton"),
    J_MENUBAR("JMenuBar", "菜单栏", "javax.swing.JMenuBar"),
    J_MENU("JMenu", "一级菜单栏", "javax.swing.JMenu"),
    J_MENU_ITEM("JMenuItem", "子菜单栏", "javax.swing.JMenuItem"),
    ;
    /**
     * 按钮类型
     */
    private String BtnType;
    /**
     * 按钮描述
     */
    private String desc;

    private String classPath;

    BtnTypeEnum(String btnType, String desc, String classPath) {
        BtnType = btnType;
        this.desc = desc;
        this.classPath = classPath;
    }


}
