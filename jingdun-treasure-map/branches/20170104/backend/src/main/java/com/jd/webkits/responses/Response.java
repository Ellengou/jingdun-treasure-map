package com.jd.webkits.responses;

/**
 * Created by Zhenwei on 2015/6/11.
 */
public class Response {

    private Response(){}

    private static Response response = new Response();

    public static Response getInstance(){
        return response;
    }

}
