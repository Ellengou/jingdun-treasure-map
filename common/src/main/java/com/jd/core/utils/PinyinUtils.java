package com.jd.core.utils;


import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.jd.core.ensure.Ensure;
import com.jd.utils.FileMD5;
import org.apache.log4j.Logger;

/**
 * Created by Ellen on 2016/12/7.
 * make by jingdun.com. roo
 */
public class PinyinUtils {

  static   Logger logger = Logger.getLogger(PinyinUtils.class);

    public static String getShortPinyin(String str) {
        String separator = "#";
        StringBuilder sb = new StringBuilder();
        char[] charArray = new char[str.length()];
        int i = 0;

        for(int len = str.length(); i < len; ++i) {
            char c = str.charAt(i);
            if(!ChineseHelper.isChinese(c) && c != 12295) {
                charArray[i] = c;
            } else {
                int j = i + 1;
                sb.append(c);

                while(j < len && (ChineseHelper.isChinese(str.charAt(j)) || str.charAt(j) == 12295)) {
                    sb.append(str.charAt(j));
                    ++j;
                }
                try {
                    String hanziPinyin = PinyinHelper.convertToPinyinString(sb.toString(), separator, PinyinFormat.WITHOUT_TONE);
                    String[] pinyinArray = hanziPinyin.split(separator);
                    String[] var10 = pinyinArray;
                    int var11 = pinyinArray.length;

                    for(int var12 = 0; var12 < var11; ++var12) {
                        String string = var10[var12];
                        charArray[i] = string.charAt(0);
                        ++i;
                    }
                    --i;
                    sb.setLength(0);
                }catch (Exception e){

                }

            }
        }
        return String.valueOf(charArray);
    }

    public static String getLoginName(String code,String name){
        try {
         return    PinyinUtils.getShortPinyin(name) +code.substring(code.length()-5,code.length());
        }catch (Exception e) {
            Ensure.that(e).isNotNull("1010");
            logger.error(e.getMessage());
        }
        return null;
    }

    public static String getMd5Password(){
        try {
         return    FileMD5.getFileMD5String("123456".getBytes());
        }catch (Exception e){
            Ensure.that(e).isNotNull("1011");
            logger.error(e.getMessage());
            return null;
        }

    }
}
