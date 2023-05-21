package com.lsc.tools.listener;

import com.lsc.service.impl.CompressionServiceImpl;
import com.lsc.tools.gui.MainPanel;
import com.lsc.tools.service.CompressionService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * @author lisc
 * @Description: com.lsc.tools.listener  加密页面, 执行按钮事件
 * @date 2022/9/27 11:19
 */
@NoArgsConstructor
public class BtnEncryListener implements ActionListener {

    private static final Logger log = LoggerFactory.getLogger(MainPanel.class);

    //@Autowired
    private CompressionService compressionService = new CompressionServiceImpl();

    private com.lsc.tools.listener.GsEncryView gsEncryView;

    // ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-config/applicationContext.xml");
    // CompressionService ctxBean = (CompressionService) ctx.getBean("compressionService");
    @Override
    public void actionPerformed(ActionEvent e) {

        log.info("BtnEncryListener.actionPerformed start");
        JButton jButton = (JButton) e.getSource();
        try {
            JTextArea jta = gsEncryView.getJta();
            JTextField txtfield = gsEncryView.getTxtfield();

            if (null == jta) {
                log.info("文件路径为null");
                return;
            }
            // 文件路径
            String pathsTxt = jta.getText();
            // 输出路径
            String outPathStr = txtfield.getText();
            List<String> pathList = Arrays.asList(pathsTxt.split("\n"));
            String name = jButton.getText();
            switch (name) {
                case "加密":
                    compressionService.encryptedCompressed(pathList, outPathStr);
                    break;
                case "解密":
                    compressionService.decryptionDecompression(pathList.get(0), outPathStr);
                    break;
                default:
                    log.info("当前按钮没有相应处理逻辑");
                    JOptionPane.showMessageDialog(null, "当前按钮没有相应处理逻辑！", "错误 ", 0);
            }
        } catch (Exception exception) {
            log.error("BtnEncryListener:actionPerformed:Exception btnName:{}, e:{}", jButton.getText(), exception);
            JOptionPane.showMessageDialog(null, exception.getMessage(), "错误 ", 0);
        }
        log.info("BtnEncryListener.actionPerformed end");
    }

    public BtnEncryListener(com.lsc.tools.listener.GsEncryView encryListener) {
        this.gsEncryView = encryListener;
    }


}
