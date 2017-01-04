package com.jd.webkits.context;

/**
 * Created by Ellen on 2015-06-18.
 */
public class XWebContext extends XContext{

    private XWebContext(){

    }

    /**
     * 创建 WebContext
     * @return
     */
    public static XContext createXWebContext(){
        XWebContext xWebContext = new XWebContext();
        THREAD_LOCAL.set(xWebContext);
        return xWebContext;
    }

    @Override
    public String getSource() {
        return "webpath=" + this.request.getServletPath();
    }

}
