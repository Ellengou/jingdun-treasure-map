package com.jd.response;

import java.util.List;

/**
 * @author Ellen.
 * @date 2017/1/3.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class TagListResponse {

    private List<TagResponse> userTags;
    private List<TagResponse> sysTags;

    public List<TagResponse> getUserTags() {
        return userTags;
    }

    public void setUserTags(List<TagResponse> userTags) {
        this.userTags = userTags;
    }

    public List<TagResponse> getSysTags() {
        return sysTags;
    }

    public void setSysTags(List<TagResponse> sysTags) {
        this.sysTags = sysTags;
    }
}
