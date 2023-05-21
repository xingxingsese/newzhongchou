package com.lsc.bean;

import com.lsc.tools.enums.BtnTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lisc
 * @Description: com.lsc.tools.bean
 * @date 2022/9/23 16:18
 */
@Data
@NoArgsConstructor
public class UiBean {

    /**
     * 按钮名称
     */
    private String menuName;

    /**
     * 按钮类型
     */
    private BtnTypeEnum btnType;

    /**
     * 是否激活使用
     */
    private boolean isActivation;

    /**
     * 事件实现类
     */
    private String listener;


}
