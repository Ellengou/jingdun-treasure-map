package com.jd.request;

/**
 * @author Ellen.
 * @date 2017/1/3.
 * @since 1.0.
 * com.jd.request .by jingdun.tech.
 */
public class EvaluationRequest {

    private String content;
    private String[] views;
    private Long businessId;
    private Long userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getViews() {
        return views;
    }

    public void setViews(String[] views) {
        this.views = views;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
