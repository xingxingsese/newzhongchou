package com.lsc.tools.listener;

import com.lsc.freemarker.core.FreeMarkerGenerator;
import com.lsc.freemarker.entity.MockResult;
import com.lsc.freemarker.facade.FreeMarkerCodeFacade;
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
 * @Description: com.lsc.tools 代码生成
 * @date 2022/8/29 11:13
 */
@Component
public class MockGeneratorListener implements ActionListener {

    private static final Logger log = LoggerFactory.getLogger(MockGeneratorListener.class);

    private FreeMarkerCodeFacade freeMarkerCodeFacade = new FreeMarkerCodeFacade();

    private final JButton jButtonGenerator = new JButton("生成");


    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel jPanel = mockCodePanel();

        GUIInit.setContent(jPanel);

    }

    /**
     * mock代码的面板
     *
     * @return
     */
    public JPanel mockCodePanel() {
        // 待生成mock代码全路径
        JLabel encryPath = new JLabel("待生成全类名:");
        JTextField txtfield = new JTextField();

        // 生成后文件输出路径
        JLabel outPath = new JLabel("生成文件路径:");
        JTextArea jta = new JTextArea(7, 100);

        jta.setLineWrap(true);    //设置文本域中的文本为自动换行
        jta.setForeground(Color.BLACK);    //设置组件的背景色
        jta.setFont(new Font("楷体", Font.BOLD, 12));    //修改字体样式

        txtfield.setFont(new Font("楷体", Font.BOLD, 12));
        txtfield.setPreferredSize(new Dimension(800, 30));

        SpringLayout springLayout = new SpringLayout();
        JPanel jPanel = new JPanel(springLayout);
        // 弹簧布局
        springLayoutCenter(encryPath, txtfield, outPath, jta, jButtonGenerator, springLayout, jPanel);


        jPanel.add(encryPath);
        jPanel.add(txtfield);
        jPanel.add(outPath);
        jPanel.add(jta);
        jPanel.add(jButtonGenerator);

        // 绑定单击事件
        jButtonGenerator.addActionListener(e1 -> {
            MockResult<Boolean> result = freeMarkerCodeFacade.mockCodeGeneration(txtfield.getText());
            jta.setText(String.valueOf(result.isSuccess()));
        });


        return jPanel;
    }

    // 弹簧布局
    public void springLayoutCenter(JLabel encryPath, JTextField txtfield, JLabel outPath, JTextArea jta, JButton jButtonEncry, SpringLayout springLayout, JPanel jPanel) {
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

        springLayout.putConstraint(SpringLayout.WEST, txtfield, 0, SpringLayout.WEST, encryPath);
        springLayout.putConstraint(SpringLayout.NORTH, txtfield, 10, SpringLayout.SOUTH, encryPath);

        springLayout.putConstraint(SpringLayout.WEST, outPath, 0, SpringLayout.WEST, encryPath);
        springLayout.putConstraint(SpringLayout.NORTH, outPath, 50, SpringLayout.SOUTH, txtfield);

        springLayout.putConstraint(SpringLayout.WEST, jta, 0, SpringLayout.WEST, txtfield);
        springLayout.putConstraint(SpringLayout.NORTH, jta, 20, SpringLayout.SOUTH, outPath);

        springLayout.putConstraint(SpringLayout.EAST, jButtonEncry, -50, SpringLayout.HORIZONTAL_CENTER, jta);
        springLayout.putConstraint(SpringLayout.NORTH, jButtonEncry, 30, SpringLayout.SOUTH, jta);

    }
}
