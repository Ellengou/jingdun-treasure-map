package com.jd.response;

import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/20.
 * @since 1.0.
 * com.jd.account .by jingdun.tech.
 */
public class AccountResponse {
    private String username;
    private String password;
    private String imageCode;
    private List<MenusResponse> meuns;//菜单
    private List<String> codes;
    private List<String> resources;//按钮 code 集合
    private String role;//角色

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public List<MenusResponse> getMeuns() {
        return meuns;
    }

    public void setMeuns(List<MenusResponse> meuns) {
        this.meuns = meuns;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
