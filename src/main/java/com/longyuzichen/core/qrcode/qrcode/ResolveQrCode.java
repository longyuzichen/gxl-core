package com.longyuzichen.core.qrcode.qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @param
 * @desc qrcode解析二维码
 * @auto longyuzichen@126.com
 * @date 2016-12-23 23:02
 */
public class ResolveQrCode {
    private static final Logger log = LoggerFactory.getLogger(ResolveQrCode.class);

    /**
     * 解析二维码
     *
     * @param imgpath 图片路径
     * @return
     */
    public static String decode(String imgpath) {
        BufferedImage image = null;
        String contents = null;
        try {
            File imageFile = new File(imgpath);
            image = ImageIO.read(imageFile);
            QRCodeDecoder qrCodeDecoder = new QRCodeDecoder();
            contents = new String(qrCodeDecoder.decode(new TwoDimensionCodeImage(image)), "utf-8");
        } catch (IOException e) {
            log.error("qrcode 解析二维码IO异常！", e);
//            e.printStackTrace();
        }
        return contents;
    }

    /**
     * 解析二维码
     *
     * @param input 输出流
     * @return
     */
    public static String decode(InputStream input) {
        String contents = null;
        try {
            BufferedImage image = null;
            image = ImageIO.read(input);
            QRCodeDecoder qrCodeDecoder = new QRCodeDecoder();
            contents = new String(qrCodeDecoder.decode(new TwoDimensionCodeImage(image)), "utf-8");
        } catch (IOException e) {
            log.error("qrcode 解析二维码IO异常！", e);
//            e.printStackTrace();
        }
        return contents;
    }

}
