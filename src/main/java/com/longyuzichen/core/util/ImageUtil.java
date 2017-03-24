package com.longyuzichen.core.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * com.longyuzichen.core.util
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc 图片工具类
 * @date 2017-03-24 23:41
 */
public class ImageUtil {
   // private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);
    // 水印文字
    // private static final  String WATERMARK = "微尘";
    //水印字体
    private static final String FONT = "楷体_GB2312";

    /**
     * 添加水印图片
     *
     * @param file     文件
     * @param contents 水印文字
     * @param out      输出流
     */
    public static void pressImage(File file, String contents, OutputStream out) {
        pressImage(file, contents, out, "JPG");
    }


    /**
     * 添加水印图片
     *
     * @param file     文件
     * @param contents 水印文字
     * @param outFile  输出文件
     */
    public static void pressImage(File file, String contents, File outFile) {
        pressImage(file, contents, outFile, "JPG");
    }

    /**
     * 添加水印图片
     *
     * @param file      文件
     * @param contents  水印文字
     * @param outFile   输入文件
     * @param imageType 图片类型
     */
    public static void pressImage(File file, String contents, File outFile, String imageType) {
        try {
            BufferedImage image = watermarking(file, contents);
            ImageIO.write(image, imageType, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加水印图片
     *
     * @param file      文件
     * @param contents  水印文字
     * @param out       输出流
     * @param imageType 图片类型
     */
    public static void pressImage(File file, String contents, OutputStream out, String imageType) {
        try {
            BufferedImage image = watermarking(file, contents);
            ImageIO.write(image, imageType, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入水印
     *
     * @param file      图片文件
     * @param watermark 水印文字
     * @return
     */
    public static BufferedImage watermarking(File file, String watermark) {
        ImageIcon icon = new ImageIcon(file.getPath());
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        //图像呈现
        graphics2D.drawImage(icon.getImage(), 0, 0, null);
        // AlphaComposite 类实现一些基本的 alpha 合成规则，将源色与目标色组合，
        // 在图形和图像中实现混合和透明效果
        AlphaComposite alpha = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.5f);
        graphics2D.setComposite(alpha);
        // 水印颜色
        graphics2D.setColor(Color.white);
        // 为呈现<a href="http://lib.csdn.net/base/datastructure" class='replace_word' title="算法与数据结构知识库" target='_blank' style='color:#df3434; font-weight:bold;'>算法</a>设置首选项(此处为文本抗锯齿提示键)的值
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 水印字体
        //g2d.setFont(new Font("Arial", Font.ITALIC, 16));
        graphics2D.setFont(new Font(FONT, Font.PLAIN, 18));
        // 水印文字
        //String watermark = "微尘";
        // 文本呈现
        graphics2D.drawString(watermark, (icon.getIconWidth() - 50),
                (icon.getIconHeight() - 15));
        graphics2D.dispose();

        return image;
    }
}
