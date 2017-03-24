package com.longyuzichen.core.qrcode.qrcode;/**
 * Created by longyuzichen on 2016-12-23.
 */

import jp.sourceforge.qrcode.data.QRCodeImage;

import java.awt.image.BufferedImage;

/**
 * @param
 * @desc 二维码图片对象
 * @auto longyuzichen@126.com
 * @date 2016-12-23 23:52
 */
public class TwoDimensionCodeImage implements QRCodeImage {

  BufferedImage image;
    public TwoDimensionCodeImage(BufferedImage image){
        this.image = image;
    }


    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public int getPixel(int i, int i1) {
        return image.getRGB(i,i1);
    }
}
