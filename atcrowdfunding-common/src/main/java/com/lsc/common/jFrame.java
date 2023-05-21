package com.lsc.common;

import javax.swing.*;
import java.awt.*;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/7/24
 */
public class jFrame extends JFrame {
    public jFrame() {


        JFrame f = new JFrame("测试窗口");

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(600, 300);
        setSize(500, 350);
        Container c = getContentPane();
        c.setBackground(Color.gray);
        JLabel l = new JLabel("测试窗口");
        c.add(l);
//		c.remove(l);
        c.validate();
        setContentPane(c);
        setResizable(true);
//		setBounds(500, 200,400, 400);
    }

    public static void main(String[] args) {
        JFrame j = new JFrame();
        j.setVisible(true);
        j.setBounds(100, 100, 700, 500);
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton b1 = new JButton("上");
        j.add(b1, BorderLayout.NORTH);


    }

}
