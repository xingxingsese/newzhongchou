package com.lsc.tools.gui;

import com.lsc.tools.annotation.ActionListenerInstaller;
import com.lsc.tools.constant.SystemConstant;

import javax.swing.*;
import java.awt.*;

/**
 * @author lisc
 * @Description: gui总入口
 * JFrame 各标签文档: https://blog.csdn.net/feng8403000/article/details/125215102
 * 各事件文档: https://blog.csdn.net/qq_45879084/article/details/106377757?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-1-106377757-blog-114345234.t5_layer_eslanding_D_4&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-1-106377757-blog-114345234.t5_layer_eslanding_D_4&utm_relevant_index=2
 * https://blog.csdn.net/qq_41474121/article/details/122736955
 * <p>
 * https://www.xinbaoku.com/archive/5Mc2FxcP.html
 * @date 2022/8/26 16:50
 */
public class GUIInit {

    // 创建窗口对象
    public static final JFrame mainWin = new JFrame("工具箱");

    public static void main(String[] args) {

        GUIInit guiInit = new GUIInit();
        guiInit.init();

        mainWin.add(MainPanel.getInstance().info());
        // 设置窗口可见
        mainWin.setVisible(true);

    }

    public void init() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        mainWin.setSize(SystemConstant.FRAME_WIDTH, SystemConstant.FRAME_HEIGHT); // 大小

        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 3代表EXIT_ON_CLOSE(在 JFrame 中定义)：使用 System exit 方法退出应用程序。仅在应用程序中使用。
        // 窗口设置到屏幕居中
        mainWin.setLocationRelativeTo(null);
        //窗体大小默认不可变
        // jFrame.setResizable(false);

        //为内容面板设置布局管理器
        mainWin.setLayout(null);

    }

    /**
     * 用于切换Jpanel 面板
     *
     * @param jPanel
     */
    public static void setContent(Container jPanel) {
        mainWin.setContentPane(jPanel);
        mainWin.setVisible(true);

    }

    public static Container getContainer() {
        return mainWin.getContentPane();
    }


}
