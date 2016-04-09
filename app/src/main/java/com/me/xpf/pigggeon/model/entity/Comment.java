package com.me.xpf.pigggeon.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xpf on 2015/7/17.
 */
public class Comment {

    @Expose
    private Integer id;
    @Expose
    private String body;
    @SerializedName("likes_count")
    @Expose
    private Integer likesCount;
    @SerializedName("likes_url")
    @Expose
    private String likesUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @Expose
    private User user;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     *
     * @return
     * The likesCount
     */
    public Integer getLikesCount() {
        return likesCount;
    }

    /**
     *
     * @param likesCount
     * The likes_count
     */
    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    /**
     *
     * @return
     * The likesUrl
     */
    public String getLikesUrl() {
        return likesUrl;
    }

    /**
     *
     * @param likesUrl
     * The likes_url
     */
    public void setLikesUrl(String likesUrl) {
        this.likesUrl = likesUrl;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

}