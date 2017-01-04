package com.jd.utils;

import java.awt.image.BufferedImage;

/**
 * RegCode
 *
 * @author zhanghongyuan
 * @date 2015/6/18
 */
public class RegCode {
    private String codevalue;
    private BufferedImage bufferedImage;

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public String getCodevalue() {
        return codevalue;
    }

    public void setCodevalue(String codevalue) {
        this.codevalue = codevalue;
    }
}
