package com.longyuzichen.core.qrcode.qrcode;
/**
 * Created by longyuzichen on 2016-12-23.
 */

import com.swetake.util.Qrcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @param
 * @desc qrcode 生产二维码
 * @auto longyuzichen@126.com
 * @date 2016-12-23 23:02
 */
public class CreateQrCode {

    private static final Logger log = LoggerFactory.getLogger(CreateQrCode.class);

    /**
     * 生成二维码
     *
     * @param contents 存储内容
     * @param filepath 图片路径
     */
    public static void encode(String contents, String filepath) {
        encode(contents, filepath, 7);
    }

    /**
     * 生产二维码
     *
     * @param contents 存储内容
     * @param out      输出流
     */
    public static void encode(String contents, OutputStream out) {
        encode(contents, out, 7);
    }

    /**
     * 生成二维码
     *
     * @param contents 存储内容
     * @param filepath 图片路径
     * @param size     二维码尺寸
     */
    public static void encode(String contents, String filepath, int size) {
        encode(contents, filepath, "JPG", size);
    }

    /**
     * 生产二维码
     *
     * @param contents 存储内容
     * @param out      输出流
     * @param size     二维码尺寸
     */
    public static void encode(String contents, OutputStream out, int size) {
        encode(contents, out, "JPG", size);

    }


    /**
     * 生产二维码
     *
     * @param contents  存储内容
     * @param filepath  图片路径
     * @param imageType 图片类型
     */
    public static void encode(String contents, String filepath, String imageType) {
        encode(contents, filepath, imageType, 7);
    }

    /**
     * 生产二维码
     *
     * @param contents  存储内容
     * @param out       输出流
     * @param imageType 图片类型
     */
    public static void encode(String contents, OutputStream out, String imageType) {
        encode(contents, out, imageType, 7);
    }

    /**
     * 生产二维码
     *
     * @param contents  存储内容
     * @param filepath  图片路径
     * @param imageType 图片类型
     * @param size      二维码尺寸
     */
    public static void encode(String contents, String filepath, String imageType, int size) {
        try {
            BufferedImage image = qrCodeCommon(contents, imageType, size);
            File file = new File(filepath);
            ImageIO.write(image, imageType, file);
        } catch (IOException e) {
            log.error("qrcode 生产二维码IO异常！", e);
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码图片
     *
     * @param contents  存储内容
     * @param out       输出流
     * @param imageType 图片类型
     * @param size      二维码尺寸
     */
    public static void encode(String contents, OutputStream out, String imageType, int size) {
        try {
            BufferedImage image = qrCodeCommon(contents, imageType, size);
            ImageIO.write(image, imageType, out);
        } catch (IOException e) {
            log.error("qrcode 生产二维码IO异常！", e);
            e.printStackTrace();
        }
    }


    /**
     * 生产二维码（QrCode)图片的公共方法
     *
     * @param contents  存储内容
     * @param imageType 图片类型
     * @param size      二维码尺寸
     * @return
     */
    private static BufferedImage qrCodeCommon(String contents, String imageType, int size) {
        BufferedImage image = null;
        try {
            Qrcode qrcodeHandler = new Qrcode();
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
            qrcodeHandler.setQrcodeVersion(size);
            // 获得内容的字节数组，设置编码格式
            byte[] bytes = contents.getBytes("UTF-8");
            // 图片尺寸
            int imgsize = 67 + 12 * (size - 1);
            image = new BufferedImage(imgsize, imgsize, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            //设置背景颜色
            graphics2D.setBackground(Color.WHITE);
            graphics2D.clearRect(0, 0, imgsize, imgsize);
            //设定图像颜色 >BLACK
            graphics2D.setColor(Color.BLACK);
            //设定偏移量，不设置可能导致解析错误
            int pixoff = 2;
            //输出内容 》 二维码
            if (bytes.length > 0 && bytes.length < 800) {
                boolean[][] codeOut = qrcodeHandler.calQrcode(bytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            graphics2D.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                throw new Exception("QRCode content bytes length = " + bytes.length + " not in [0, 800].");
            }
            graphics2D.dispose();
            image.flush();
        } catch (UnsupportedEncodingException e) {
            log.error("qrcode 生产二维码异常！", e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("qrcode 生产二维码异常！", e);
            e.printStackTrace();
        }
        return image;
    }


}
