package com.me.xpf.pigggeon.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xpf on 2015/7/29.
 */
public class UserFollowee implements Following {

    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @Expose
    private Followee followee;

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
     * @return The followee
     */
    public Followee getFollowee() {
        return followee;
    }

    /**
     * @param followee The followee
     */
    public void setFollowee(Followee followee) {
        this.followee = followee;
    }

}
