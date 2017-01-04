package com.jd.webkits.context;

/**
 * Created by Ellen on 2015-06-18.
 */
public class XGeneralContext extends XContext{

    private XGeneralContext(){

    }

    /**
     * 创建 GeneralContext
     * @return
     */
    public static XContext createXGeneralContext(){
        XGeneralContext xGeneralContext = new XGeneralContext();
        THREAD_LOCAL.set(xGeneralContext);
        return xGeneralContext;
    }

    @Override
    public String getSource() {
        return "clientId=" + this.getParameter("clientId");
    }

}
