package com.me.xpf.pigggeon.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xpf on 2015/7/29.
 */
public class UserFollower implements Following{

    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @Expose
    private Follower follower;

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
     * The follower
     */
    public Follower getFollower() {
        return follower;
    }

    /**
     *
     * @param follower
     * The follower
     */
    public void setFollower(Follower follower) {
        this.follower = follower;
    }

}
