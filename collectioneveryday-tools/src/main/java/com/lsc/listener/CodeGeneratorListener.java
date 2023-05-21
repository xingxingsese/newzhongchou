package com.lsc.tools.listener;

import com.lsc.freemarker.enums.ResultCodeEnum;
import com.lsc.freemarker.core.Generator;
import com.lsc.tools.enums.JumpBtnType;
import com.lsc.tools.gui.GUIInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author lisc
 * @Description: com.lsc.tools crud代码生成
 * @date 2022/8/29 11:13
 */
@Component
public class CodeGeneratorListener implements ActionListener {

    private static final Logger log = LoggerFactory.getLogger(CodeGeneratorListener.class);

    private JLabel rootPathJLabel = new JLabel("项目路径:");
    // private JTextField rootPathField = new JTextField(50);
    private JComboBox rootPathField = new JComboBox();

    private JLabel packagePathJLabel = new JLabel("包路径:");
    private JTextField packagePathField = new JTextField(50);

    private JLabel classNameLabel = new JLabel("类名:");
    private JTextField classNameField = new JTextField(50);

    private JLabel tableNameLabel = new JLabel("表名:");
    private JTextField tableNameField = new JTextField(50);

    private final JButton jButtonGenerator = new JButton(JumpBtnType.CRUD_GENERATOR_JBUTTON_BUILD.getBtnName());

    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel jPanel = null;
        log.info("点击{}按钮", e.getActionCommand());
        // 设置布局
        jPanel = crudCodePanel();

        jButtonGenerator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("点击{}按钮", e.getActionCommand());
                // 生成业务代码
                String generateCrudCode = Generator.generateCrudCode(rootPathField.getSelectedItem().toString(), packagePathField.getText(), classNameField.getText(), tableNameField.getText());
                JOptionPane.showMessageDialog(null, ResultCodeEnum.getByCode(generateCrudCode).getDesc(), "提示 ", JOptionPane.PLAIN_MESSAGE);
            }
        });

        GUIInit.setContent(jPanel);


    }


    /**
     * crud 代码的面板
     *
     * @return
     */
    public JPanel crudCodePanel() {
        SpringLayout springLayout = new SpringLayout();
        JPanel jPanel = new JPanel(springLayout);
        Font font = new Font("宋体", Font.BOLD, 15);
        // 设置字体大小
        //rootPathField.setFont(font);
        packagePathField.setFont(font);
        classNameField.setFont(font);
        tableNameField.setFont(font);

        //向下拉列表中添加一项
        rootPathField.addItem("--请选择--");
        rootPathField.addItem("com.ipay.ibizecoprod");
        rootPathField.addItem("com.ipay.iexpbizprod");

        Spring childWidth = Spring.sum(Spring.sum(Spring.width(rootPathJLabel), Spring.width(rootPathField)), Spring.constant(20));
        int offSetX = childWidth.getValue() / 2;
        /**
         * NORTH 上
         * WEST 左
         * HORIZONTAL_CENTER 中
         * EAST 右
         * SOUTH 下
         * HORIZONTAL_CENTER 水平居中
         * VERTICAL_CENTER 垂直居中
         *  A对象的哪个边  ,A对象  ,间距多少 , B对象的哪个边    , B对象
         */
        springLayout.putConstraint(SpringLayout.EAST, rootPathJLabel, 280, SpringLayout.WEST, jPanel);
        springLayout.putConstraint(SpringLayout.NORTH, rootPathJLabel, 220, SpringLayout.NORTH, jPanel);

        springLayout.putConstraint(SpringLayout.WEST, rootPathField, 30, SpringLayout.EAST, rootPathJLabel);
        springLayout.putConstraint(SpringLayout.NORTH, rootPathField, 0, SpringLayout.NORTH, rootPathJLabel);

        springLayout.putConstraint(SpringLayout.EAST, packagePathJLabel, 0, SpringLayout.EAST, rootPathJLabel);
        springLayout.putConstraint(SpringLayout.NORTH, packagePathJLabel, 30, SpringLayout.SOUTH, rootPathJLabel);

        springLayout.putConstraint(SpringLayout.NORTH, packagePathField, 0, SpringLayout.NORTH, packagePathJLabel);
        springLayout.putConstraint(SpringLayout.WEST, packagePathField, 30, SpringLayout.EAST, packagePathJLabel);

        springLayout.putConstraint(SpringLayout.NORTH, classNameLabel, 30, SpringLayout.SOUTH, packagePathJLabel);
        springLayout.putConstraint(SpringLayout.EAST, classNameLabel, 0, SpringLayout.EAST, packagePathJLabel);

        springLayout.putConstraint(SpringLayout.NORTH, classNameField, 0, SpringLayout.NORTH, classNameLabel);
        springLayout.putConstraint(SpringLayout.WEST, classNameField, 30, SpringLayout.EAST, classNameLabel);

        springLayout.putConstraint(SpringLayout.NORTH, tableNameLabel, 30, SpringLayout.SOUTH, classNameLabel);
        springLayout.putConstraint(SpringLayout.EAST, tableNameLabel, 0, SpringLayout.EAST, classNameLabel);

        springLayout.putConstraint(SpringLayout.NORTH, tableNameField, 0, SpringLayout.NORTH, tableNameLabel);
        springLayout.putConstraint(SpringLayout.WEST, tableNameField, 0, SpringLayout.WEST, classNameField);

        springLayout.putConstraint(SpringLayout.NORTH, jButtonGenerator, 80, SpringLayout.NORTH, tableNameField);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, jButtonGenerator, 0, SpringLayout.HORIZONTAL_CENTER, tableNameField);

        jPanel.add(rootPathJLabel);
        jPanel.add(rootPathField);
        jPanel.add(packagePathJLabel);
        jPanel.add(packagePathField);
        jPanel.add(classNameLabel);
        jPanel.add(classNameField);
        jPanel.add(tableNameLabel);
        jPanel.add(tableNameField);
        jPanel.add(jButtonGenerator);

        return jPanel;
    }


}
