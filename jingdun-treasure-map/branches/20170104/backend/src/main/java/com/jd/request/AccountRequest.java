package com.jd.request;

/**
 * @author Ellen.
 * @date 2016/12/20.
 * @since 1.0.
 * com.jd.account .by jingdun.tech.
 */
public class AccountRequest {

    private String username;
    private String password;
    private String imageCode;
    private String key;

    public AccountRequest(){
        super();
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
}
