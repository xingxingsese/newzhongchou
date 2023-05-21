package com.lsc.tools.listener;

import com.lsc.freemarker.core.CustomClassLoader;
import com.lsc.freemarker.utils.ReflectionUtil;
import com.lsc.tools.enums.JumpBtnType;
import com.lsc.tools.gui.GUIInit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lisc
 * @Description: com.lsc.tools.listener
 * @date 2022/11/3 14:21
 */
@Slf4j
public class CodeToolListener implements ActionListener {

    private JPanel mainJpanel = new JPanel();

    /**
     * 顶部面板
     */
    private JPanel topJpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    /**
     * 中间面板
     */
    private JPanel middleJpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


    /**
     * 顶部面板 - 按钮
     */
    private JButton objectToJsonBtn = new JButton(JumpBtnType.OBJECT_TO_JSON_JBUTTON_JUMP.getBtnName());

    /**
     * 顶部面板 - 按钮
     */
    private JButton codeGeneratorBtn = new JButton("业务代码生成");

    // 生成按钮
    JButton generateBtn = new JButton(JumpBtnType.OBJECT_TO_JSON__JBUTTON_BUILD.getBtnName());


    @Override
    public void actionPerformed(ActionEvent e) {


        mainJpanel.setLayout(new BorderLayout());
        // 顶部面板添加按钮 , 并且把顶部面板添加到主面板上
        mainJpanel.add(topJpanelAdd(), BorderLayout.NORTH);
        // 中间面板添加输入框
        //  mainJpanel.add(middleJpanelBuild(), BorderLayout.CENTER);


        log.info("切换主面板");
        GUIInit.setContent(this.mainJpanel);
    }


    /**
     * 顶部jpanel 添加按钮方法
     *
     * @return
     */
    public JPanel topJpanelAdd() {
        topJpanel.add(objectToJsonBtn);
        topJpanel.add(codeGeneratorBtn);
        // 单击事件
        actionListener();
        return this.topJpanel;
    }

    /**
     * 按钮的单击事件
     */
    public void actionListener() {
        // 中间面板添加输入框
        objectToJsonBtn.addActionListener(a -> {
            log.info("{} :单击事件开始", a.getActionCommand());
            mainJpanel.add(objectToJsonBtnJpanelBuild(), BorderLayout.CENTER);
            GUIInit.setContent(this.mainJpanel);
        });

        codeGeneratorBtn.addActionListener(a -> {
            log.info("{} :单击事件开始", a.getActionCommand());

        });

    }

    /**
     * 对象转JSON 的 中间面板
     *
     * @return
     */
    public JPanel objectToJsonBtnJpanelBuild() {
        log.info("完善中间面板");

        JLabel classPathJLabel = new JLabel("类路径:");
        JTextField classPathTextField = new JTextField(50);

        // 结果文本框
        JTextArea resultTextArea = new JTextArea(50, 100);
        // 生成按钮
        JButton generateBtn = new JButton("生成");
        generateBtn.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建自定义的类加载器
                CustomClassLoader loader = new CustomClassLoader();

                // 使用自定义的类加载器加载TestHelloWorld类
                Class classaa = loader.loadClass(classPathTextField.getText());
                String creationJsonTxt = ReflectionUtil.CreationJsonTxt(classaa);
                resultTextArea.setText(creationJsonTxt);
            }
        });

        // 设置背景颜色
        middleJpanel.setBackground(Color.lightGray);

        middleJpanel.add(classPathJLabel);
        middleJpanel.add(classPathTextField);
        middleJpanel.add(generateBtn);
        middleJpanel.add(resultTextArea);

        return middleJpanel;

    }
}
