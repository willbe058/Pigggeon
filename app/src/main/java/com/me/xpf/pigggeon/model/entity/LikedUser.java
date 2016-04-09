package com.me.xpf.pigggeon.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xpf on 2015/8/31.
 */
public class LikedUser {

    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @Expose
    private User user;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

}