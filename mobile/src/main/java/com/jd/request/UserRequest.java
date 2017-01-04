package com.jd.request;

/**
 * @author Ellen.
 * @date 2016/12/20.
 * @since 1.0.
 * com.jd.account .by jingdun.tech.
 */
public class UserRequest {

    private String userName;
    private String passWord;
    private String imageCode;
    private String key;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserRequest() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
}
