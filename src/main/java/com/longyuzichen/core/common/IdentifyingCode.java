/**
 * Copyright [2017] guoxinlei(longyuzichen@126.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.longyuzichen.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * com.longyuzichen.core.common
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc 验证码生产
 * @date 2017-03-24 23:45
 */
public class IdentifyingCode {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentifyingCode.class);

    private static char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '2', '3', '4', '5', '6', '7', '8', '9'};

    //默认验证码干扰线数量
    private static final int LINE_COUNT = 50;
    //验证码颜色
    private static final Color COLOR = Color.BLUE;
    //图片格式
    private static final String IMAGE_TYPE = "JPG";
    //图片高度
    private static final int IMAGE_HEIGHT = 40;
    //图片宽度
    private static final int IMAGE_WIDTH = 120;

    private IdentifyingCode() {
    }


    /**
     * 生产验证码
     *
     * @param code 字符内容
     * @param out  输出流
     */
    public static void verifyCodeStream(String code, OutputStream out) {
        verifyCodeStream(IMAGE_HEIGHT, IMAGE_WIDTH, code, IMAGE_TYPE, out);
    }

    /**
     * 生产验证码
     *
     * @param code 字符内容
     * @param file 输出流
     */
    public static void verifyCodeStream(String code, File file) {
        verifyCodeStream(IMAGE_HEIGHT, IMAGE_WIDTH, code, file);
    }


    /**
     * 生产验证码
     *
     * @param height 图片高度
     * @param widht  图片宽度
     * @param code   字符内容
     * @param out    输出流
     */
    public static void verifyCodeStream(int height, int widht, String code, OutputStream out) {
        verifyCodeStream(height, widht, code, IMAGE_TYPE, out);
    }

    /**
     * 生产验证码
     *
     * @param height 图片高度
     * @param widht  图片宽度
     * @param code   字符内容
     * @param file   输出流
     */
    public static void verifyCodeStream(int height, int widht, String code, File file) {
        verifyCodeFile(height, widht, code, IMAGE_TYPE, file);
    }

    /**
     * 生产验证码
     *
     * @param height    图片高度
     * @param widht     图片宽度
     * @param code      字符内容
     * @param imageType 图片类型
     * @param out       输出流
     */
    public static void verifyCodeStream(int height, int widht, String code, String imageType, OutputStream out) {
        try {
            BufferedImage image = createCode(height, widht, code, imageType);
            ImageIO.write(image, imageType, out);
        } catch (IOException e) {
            LOGGER.error("验证码生产异常！", e);
        }
    }

    /**
     * 生成验证码
     *
     * @param height    图片高度
     * @param width     图片宽度
     * @param code      字符内容
     * @param imageType 图片类型
     * @param file      输入文件
     */
    public static void verifyCodeFile(int height, int width, String code, String imageType, File file) {
        try {
            BufferedImage image = createCode(height, width, code);
            ImageIO.write(image, imageType, file);
        } catch (IOException e) {
            LOGGER.error("验证码生产异常！", e);
        }
    }

    /**
     * 创建验证码码
     *
     * @param height 图片高度
     * @param width  图片宽度
     * @param code   验证码
     * @return
     */
    public static BufferedImage createCode(int height, int width, String code) {
        BufferedImage image = createCode(height, width, "微软雅黑", code, getRandomColor());
        return image;
    }

    /**
     * 创建验证码码
     *
     * @param height    图片高度
     * @param width     图片宽度
     * @param fontStyle 图片字体样式
     * @return
     */
    public static BufferedImage createCode(int height, int width, String code, String fontStyle) {
        BufferedImage image = createCode(height, width, fontStyle, code, getRandomColor());
        return image;
    }


    /**
     * 创建验证码码
     *
     * @param height    图片高度
     * @param width     图片宽度
     * @param fontStyle 图片字体样式
     * @param code      code集合
     * @param color     图片颜色
     * @return
     */
    public static BufferedImage createCode(int height, int width, String fontStyle, String code, Color color) {
        int x = 0;
        int fontHeight = 0;
        fontHeight = height - 5; //字体的高度
        x = width / (code.length() + 3);//每个字符的宽度

        //图片buffer
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = image.createGraphics();
        //将图像填充为白色
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        //创建字体
        Font font = new Font(fontStyle, Font.BOLD, 14);
        graphics2D.setFont(font);
        for (int i = 0; i < LINE_COUNT; i++) {
            int x0 = getRandomNumber(width);
            int y0 = getRandomNumber(height);
            int x1 = x0 + getRandomNumber(width / 8);
            int y1 = y0 + getRandomNumber(height / 8);
            graphics2D.setColor(color);
            graphics2D.drawLine(x0, y0, x1, y1);
        }
        for (int i = 0; i < code.length(); i++) {
            String rand = String.valueOf(code.charAt(i));

            //设置字体颜色
            graphics2D.setColor(getRandomColor());
            graphics2D.drawString(rand, (i + 1) * x, getRandomNumber(height / 2) + 25);
        }
        return image;
    }

    /**
     * 获取随机数
     *
     * @param number
     * @return
     */
    public static int getRandomNumber(int number) {
        return new Random().nextInt(number);
    }

    /**
     * 获取随机颜色
     *
     * @return
     */
    public static Color getRandomColor() {
        int r = getRandomNumber(255);
        int g = getRandomNumber(255);
        int b = getRandomNumber(255);
        return new Color(r, g, b);
    }
}
