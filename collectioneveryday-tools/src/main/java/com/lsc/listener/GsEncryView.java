package com.lsc.tools.listener;

import com.lsc.tools.enums.JumpBtnType;
import com.lsc.tools.gui.GUIInit;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lisc
 * @Description: com.lsc.tools.listener 公司加密listener
 * @date 2022/9/26 14:45
 */
@Getter
public class GsEncryView implements ActionListener {


    private final JButton jButtonEncry = new JButton(JumpBtnType.ENCRYPTION_JBUTTON_BUILD.getBtnName());

    private final JButton jButtonDncry = new JButton(JumpBtnType.DECRYPTION_JBUTTON_BUILD.getBtnName());


    // 加密路径
    private final JLabel encryPath = new JLabel("加解密文件路径:");
    private final JTextArea jta = new JTextArea(7, 100);
    // 加密后文件输出路径
    private final JLabel outPath = new JLabel("文件输出路径(文件夹要以' \\ '结尾):");
    private final JTextField txtfield = new JTextField();

    @Override
    public void actionPerformed(ActionEvent e) {
        BtnEncryListener btnEncryListener = new BtnEncryListener(this);

        jta.setLineWrap(true);    //设置文本域中的文本为自动换行
        jta.setForeground(Color.BLACK);    //设置组件的背景色
        jta.setFont(new Font("楷体", Font.BOLD, 12));    //修改字体样式

        txtfield.setFont(new Font("楷体", Font.BOLD, 12));
        txtfield.setPreferredSize(new Dimension(800, 30));

        // 绑定单击事件
        jButtonEncry.addActionListener(btnEncryListener);
        jButtonDncry.addActionListener(btnEncryListener);

        SpringLayout springLayout = new SpringLayout();
        JPanel jPanel = new JPanel(springLayout);

        // 弹簧布局
        springLayoutCenter(encryPath, jta, outPath, txtfield, jButtonEncry, jButtonDncry, springLayout, jPanel);


        jPanel.add(encryPath);
        jPanel.add(jta);
        jPanel.add(outPath);
        jPanel.add(txtfield);
        jPanel.add(jButtonEncry);
        jPanel.add(jButtonDncry);

        GUIInit.setContent(jPanel);
    }

    // 弹簧布局
    public void springLayoutCenter(JLabel encryPath, JTextArea jta, JLabel outPath, JTextField txtfield, JButton jButtonEncry, JButton jButtonDncry, SpringLayout springLayout, JPanel jPanel) {
        /**
         * NORTH 上
         * WEST 左
         * HORIZONTAL_CENTER 中
         * EAST 右
         * SOUTH 下
         * Horizontal Center 水平居中
         * Vertical Center 垂直居中
         */
        Spring childWidth = Spring.sum(Spring.sum(Spring.width(encryPath), Spring.width(jta)), Spring.constant(20));
        int offSetX = childWidth.getValue() / 2;

        /**
         * A对象的哪个边  ,A对象  ,间距多少 , B对象的哪个边    , B对象
         */
        springLayout.putConstraint(SpringLayout.WEST, encryPath, -offSetX, SpringLayout.HORIZONTAL_CENTER, jPanel);
        springLayout.putConstraint(SpringLayout.NORTH, encryPath, 20, SpringLayout.NORTH, jPanel);

        springLayout.putConstraint(SpringLayout.WEST, jta, 0, SpringLayout.WEST, encryPath);
        springLayout.putConstraint(SpringLayout.NORTH, jta, 10, SpringLayout.SOUTH, encryPath);

        springLayout.putConstraint(SpringLayout.WEST, outPath, 0, SpringLayout.WEST, encryPath);
        springLayout.putConstraint(SpringLayout.NORTH, outPath, 50, SpringLayout.SOUTH, jta);

        springLayout.putConstraint(SpringLayout.WEST, txtfield, 0, SpringLayout.WEST, jta);
        springLayout.putConstraint(SpringLayout.NORTH, txtfield, 20, SpringLayout.SOUTH, outPath);

        springLayout.putConstraint(SpringLayout.EAST, jButtonEncry, -50, SpringLayout.HORIZONTAL_CENTER, txtfield);
        springLayout.putConstraint(SpringLayout.NORTH, jButtonEncry, 30, SpringLayout.SOUTH, txtfield);

        springLayout.putConstraint(SpringLayout.EAST, jButtonDncry, 150, SpringLayout.WEST, jButtonEncry);
        springLayout.putConstraint(SpringLayout.NORTH, jButtonDncry, 0, SpringLayout.NORTH, jButtonEncry);
    }


}
