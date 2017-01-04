package com.jd.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 验证码
 *
 * @author zhy 2015年6月3日 上午9:32:46
 */
public class GenerateImageCode {

    protected static Logger logger = Logger.getLogger(GenerateImageCode.class);


    public static void genbuffer(RegCode regCode) {
        // 验证码图片的宽度。
        int width = 100;
        // 验证码图片的高度。
        int height = 36;
        // 验证码字符个数
        int codeCount = 4;
        // 验证码干扰线数
        int lineCount = 10;
        Graphics2D g = null;
        try {

            int fontWidth = width / codeCount;// 字体的宽度
            int fontHeight = height - 5;// 字体的高度
            int codeY = height - 8;

            // 图像buffer
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

           // g = buffImg.getGraphics();
            g = buffImg.createGraphics();
            // 设置背景色
            // g.setColor(getRandColor(200, 250));
            g.setColor(new Color(255, 255, 255));
            g.fillRect(0, 0, width, height);

            g.setColor(new Color(204, 204, 204));
            g.drawRect(0, 0, width - 1, height - 1);

            Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
            g.setFont(font);
            Random random = new Random();
            // 设置干扰线
            for (int i = 0; i < lineCount; i++) {
                int xs = random.nextInt(width);
                int ys = random.nextInt(height);
                int xe = xs + random.nextInt(width);
                int ye = ys + random.nextInt(height);
                g.setColor(getRandColor(1, 255));
                g.drawLine(xs, ys, xe, ye);
            }

            // 添加噪点
            float yawpRate = 0.01f;// 噪声率
            int area = (int) (yawpRate * width * height);
            for (int i = 0; i < area; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);

                buffImg.setRGB(x, y, random.nextInt(255));
            }

            String str1 = randomStr(codeCount);// 得到随机字符

            for (int i = 0; i < codeCount; i++) {
                String strRand = str1.substring(i, i + 1);
                g.setColor(getRandColor(1, 255));
                // g.drawString(a,x,y);
                // a为要画出来的东西，x和y表示要画的东西最左侧字符的基线位于此图形上下文坐标系的 (x, y) 位置处
                g.drawString(strRand, i * fontWidth + 1, codeY);
            }

            regCode.setCodevalue(str1);
            regCode.setBufferedImage(buffImg);
        } catch (Exception e) {
            logger.error("验证码生产失败！", e);
        } finally {
            if(g!=null)
            g.dispose();
        }
    }

    // 得到随机字符
    public static String randomStr(int n) {
        String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        String str2 = "";
        int len = str1.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            str2 = str2 + str1.charAt((int) r);
        }
        return str2;
    }

    // 得到随机颜色
    private static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }


}
