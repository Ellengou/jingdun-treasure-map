package com.jd.utils.excel;

import org.apache.poi.hssf.util.HSSFColor;

/**
 * 类ExeclGray40Color.java的实现描述：灰度为40的execl背景色
 * 
 * @author Starty 2015年6月5日 下午5:29:53
 */
public class ExeclGray40Color extends HSSFColor {

    public final static short   index     = 0x37;
    public final static short[] triplet   = { 150, 150, 150 };
    public final static String  hexString = "9696:9696:9696";

    @Override
    public short getIndex() {
        return index;
    }

    @Override
    public short[] getTriplet() {
        return triplet;
    }

    @Override
    public String getHexString() {
        return hexString;
    }
}
