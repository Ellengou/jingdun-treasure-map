package com.jd.utils;

import org.apache.commons.lang3.StringUtils;

public class JSParse {

    public static String HTMLEncode(String txt) {
        if(StringUtils.isBlank(txt)){
            return txt;
        }
        String Ntxt = txt;
        Ntxt = Ntxt.replace(" ", "");
        Ntxt = Ntxt.replace("<", "&lt;");
        Ntxt = Ntxt.replace(">", "&gt;");
        Ntxt = Ntxt.replace("\"", "&quot;");
        Ntxt = Ntxt.replace("'", "&#39;");
        return Ntxt;
    }
}
