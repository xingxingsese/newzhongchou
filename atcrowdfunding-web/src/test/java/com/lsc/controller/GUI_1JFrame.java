package com.lsc.controller;

import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/11
 */
public class GUI_1JFrame extends JFrame {

    @Test
    public void test() {
        //使用SwingUtilities工具调用createAndShowGUI()方法显示GUI程序
        SwingUtilities.invokeLater(GUI_1JFrame::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        //创建并设置JFrame容器窗口
        JFrame frame = new JFrame("JFrameText");
        //设置并关闭窗口时的默认操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口标题
        frame.setTitle("窗口");
        //设置窗口尺寸
        frame.setSize(350, 300);
        //设置窗口显示位置
        frame.setLocation(300, 200);
        //让组件显示
        frame.setVisible(true);
        //改变窗口图标
        Image image;
        try {
            image = ImageIO.read(new File("图片路径"));
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取标题
        System.out.println(frame.getTitle());
        //创建JPanel对象
        JPanel jp = new JPanel();
        //设置字体大小
        Font font = new Font("仿宋", Font.BOLD, 20);
        //JLabel() ==>创建一个没有图像的 JLabel实例，标题为空字符串。
        //JLabel(String text) ==>使用指定的文本创建一个 JLabel实例。
        JLabel jl1 = new JLabel("用户名：");
        jl1.setFont(font);//设置统一的字体，看起来更美观整齐
        //JTextField(String text) ==>构造一个新的 TextField ，用指定的文本初始化。
        JTextField jf1 = new JTextField("请输入用户名：", 20);
        jf1.setFont(font);//设置统一的字体
        JLabel jl2 = new JLabel("密码：");
        jl2.setFont(font);//设置统一的字体
        JTextField jf2 = new JTextField("请输入密码：", 20);
        jf2.setFont(font);//设置统一的字体
        //建立按钮
        JButton jb1 = new JButton("登录");
        jb1.setFont(font);//设置统一的字体
        JButton jb2 = new JButton("退出");
        jb2.setFont(font);//设置统一的字体
        //将这些文本和按钮添加到JPanel对象中，然后再将JPanel对象添加到JFrame对象中去
        jp.add(jl1);
        jp.add(jf1);
        jp.add(jl2);
        jp.add(jf2);
        jp.add(jb1);
        jp.add(jb2);
        frame.add(jp);
        //让组件显示
        frame.setVisible(true);
    }
}
