package com.lsc.tools.gui;

import com.lsc.tools.annotation.ActionListenerFor;
import com.lsc.tools.annotation.ActionListenerInstaller;
import com.lsc.tools.enums.JumpBtnType;
import com.lsc.tools.listener.CodeGeneratorListener;
import com.lsc.tools.listener.CodeToolListener;
import com.lsc.tools.listener.GsEncryView;
import com.lsc.tools.listener.MockGeneratorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * @author lisc
 * @Description: 主面板
 * @date 2022/9/5 15:34
 */
@Component
public class MainPanel extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(MainPanel.class);

    // 菜单栏
    private JMenuBar jMenuBer = new JMenuBar();

    // 一级菜单栏
    private JMenu menu = new JMenu(JumpBtnType.ENORDE_CRYPTION_JMENU_JUMP.getBtnName());
    // 一级菜单栏
    private JMenu mockMenu = new JMenu(JumpBtnType.CODE_GENERATOR_JMENU_JUMP.getBtnName());
    // 一级菜单栏
    private JMenu codeToolMenu = new JMenu(JumpBtnType.CODE_TOOL_JMENU_JUMP.getBtnName());

    /**
     * 加解密子菜单栏
     */
    @ActionListenerFor(listener = GsEncryView.class)
    private JMenuItem itemEncry = new JMenuItem(JumpBtnType.ENCRYPTION_JMENU_JUMP.getBtnName());

    /**
     * mock代码生成菜单栏
     */
    @ActionListenerFor(listener = MockGeneratorListener.class)
    private JMenuItem itemMockGenerator = new JMenuItem(JumpBtnType.MOCK_CODE_GENERATOR_JMENU_JUMP.getBtnName());

    /**
     * 业务代码子菜单栏
     */
    @ActionListenerFor(listener = CodeGeneratorListener.class)
    private JMenuItem crudGenerator = new JMenuItem(JumpBtnType.CRUD_CODE_GENERATOR_JMENU_JUMP.getBtnName());

    @ActionListenerFor(listener = CodeToolListener.class)
    private JMenuItem toolJmenuItem = new JMenuItem(JumpBtnType.TOOL_LIST_JMENU_JUMP.getBtnName());

    /**
     * 主面板
     * 存放所有按钮
     */
    public MainPanel info() {
        log.info("{}:MainPanel() 开始执行", this.getClass().getSimpleName());

        // 把子菜单添加到菜单栏
        menu.add(itemEncry);

        // 子菜单添加到一级菜单
        mockMenu.add(itemMockGenerator);
        mockMenu.add(crudGenerator);

        codeToolMenu.add(toolJmenuItem);


        // 把一级菜单栏添加到标签栏
        jMenuBer.add(menu);
        jMenuBer.add(mockMenu);
        jMenuBer.add(codeToolMenu);


        jMenuBer.setVisible(true);
        GUIInit.mainWin.setJMenuBar(jMenuBer);

        return this;
    }


    /**
     * 内部类单例
     */
    private MainPanel() {
    }

    public static MainPanel mainPanel;

    public static MainPanel getInstance() {
        return MainPanelHander.mainPanel;
    }

    private static class MainPanelHander {
        private static final MainPanel mainPanel = new MainPanel();
    }

    /**
     * 代码块加载优先级比构造函数高
     */ {
        // 处理注解的方法
        ActionListenerInstaller.processAnnotations(this);
    }
}
