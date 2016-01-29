package com.me.xpf.pigggeon.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by xpf on 2015/7/24.
 */
public class Team implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String username;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @Expose
    private String bio;
    @Expose
    private String location;
    @Expose
    private Links links;
    @SerializedName("buckets_count")
    @Expose
    private Integer bucketsCount;
    @SerializedName("comments_received_count")
    @Expose
    private Integer commentsReceivedCount;
    @SerializedName("followers_count")
    @Expose
    private Integer followersCount;
    @SerializedName("followings_count")
    @Expose
    private Integer followingsCount;
    @SerializedName("likes_count")
    @Expose
    private Integer likesCount;
    @SerializedName("likes_received_count")
    @Expose
    private Integer likesReceivedCount;
    @SerializedName("members_count")
    @Expose
    private Integer membersCount;
    @SerializedName("projects_count")
    @Expose
    private Integer projectsCount;
    @SerializedName("rebounds_received_count")
    @Expose
    private Integer reboundsReceivedCount;
    @SerializedName("shots_count")
    @Expose
    private Integer shotsCount;
    @SerializedName("can_upload_shot")
    @Expose
    private Boolean canUploadShot;
    @Expose
    private String type;
    @Expose
    private Boolean pro;
    @SerializedName("buckets_url")
    @Expose
    private String bucketsUrl;
    @SerializedName("followers_url")
    @Expose
    private String followersUrl;
    @SerializedName("following_url")
    @Expose
    private String followingUrl;
    @SerializedName("likes_url")
    @Expose
    private String likesUrl;
    @SerializedName("members_url")
    @Expose
    private String membersUrl;
    @SerializedName("shots_url")
    @Expose
    private String shotsUrl;
    @SerializedName("team_shots_url")
    @Expose
    private String teamShotsUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @param htmlUrl
     * The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     *
     * @return
     * The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     *
     * @param avatarUrl
     * The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     *
     * @return
     * The bio
     */
    public String getBio() {
        return bio;
    }

    /**
     *
     * @param bio
     * The bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     *
     * @return
     * The bucketsCount
     */
    public Integer getBucketsCount() {
        return bucketsCount;
    }

    /**
     *
     * @param bucketsCount
     * The buckets_count
     */
    public void setBucketsCount(Integer bucketsCount) {
        this.bucketsCount = bucketsCount;
    }

    /**
     *
     * @return
     * The commentsReceivedCount
     */
    public Integer getCommentsReceivedCount() {
        return commentsReceivedCount;
    }

    /**
     *
     * @param commentsReceivedCount
     * The comments_received_count
     */
    public void setCommentsReceivedCount(Integer commentsReceivedCount) {
        this.commentsReceivedCount = commentsReceivedCount;
    }

    /**
     *
     * @return
     * The followersCount
     */
    public Integer getFollowersCount() {
        return followersCount;
    }

    /**
     *
     * @param followersCount
     * The followers_count
     */
    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    /**
     *
     * @return
     * The followingsCount
     */
    public Integer getFollowingsCount() {
        return followingsCount;
    }

    /**
     *
     * @param followingsCount
     * The followings_count
     */
    public void setFollowingsCount(Integer followingsCount) {
        this.followingsCount = followingsCount;
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
     * The likesReceivedCount
     */
    public Integer getLikesReceivedCount() {
        return likesReceivedCount;
    }

    /**
     *
     * @param likesReceivedCount
     * The likes_received_count
     */
    public void setLikesReceivedCount(Integer likesReceivedCount) {
        this.likesReceivedCount = likesReceivedCount;
    }

    /**
     *
     * @return
     * The membersCount
     */
    public Integer getMembersCount() {
        return membersCount;
    }

    /**
     *
     * @param membersCount
     * The members_count
     */
    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }

    /**
     *
     * @return
     * The projectsCount
     */
    public Integer getProjectsCount() {
        return projectsCount;
    }

    /**
     *
     * @param projectsCount
     * The projects_count
     */
    public void setProjectsCount(Integer projectsCount) {
        this.projectsCount = projectsCount;
    }

    /**
     *
     * @return
     * The reboundsReceivedCount
     */
    public Integer getReboundsReceivedCount() {
        return reboundsReceivedCount;
    }

    /**
     *
     * @param reboundsReceivedCount
     * The rebounds_received_count
     */
    public void setReboundsReceivedCount(Integer reboundsReceivedCount) {
        this.reboundsReceivedCount = reboundsReceivedCount;
    }

    /**
     *
     * @return
     * The shotsCount
     */
    public Integer getShotsCount() {
        return shotsCount;
    }

    /**
     *
     * @param shotsCount
     * The shots_count
     */
    public void setShotsCount(Integer shotsCount) {
        this.shotsCount = shotsCount;
    }

    /**
     *
     * @return
     * The canUploadShot
     */
    public Boolean getCanUploadShot() {
        return canUploadShot;
    }

    /**
     *
     * @param canUploadShot
     * The can_upload_shot
     */
    public void setCanUploadShot(Boolean canUploadShot) {
        this.canUploadShot = canUploadShot;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The pro
     */
    public Boolean getPro() {
        return pro;
    }

    /**
     *
     * @param pro
     * The pro
     */
    public void setPro(Boolean pro) {
        this.pro = pro;
    }

    /**
     *
     * @return
     * The bucketsUrl
     */
    public String getBucketsUrl() {
        return bucketsUrl;
    }

    /**
     *
     * @param bucketsUrl
     * The buckets_url
     */
    public void setBucketsUrl(String bucketsUrl) {
        this.bucketsUrl = bucketsUrl;
    }

    /**
     *
     * @return
     * The followersUrl
     */
    public String getFollowersUrl() {
        return followersUrl;
    }

    /**
     *
     * @param followersUrl
     * The followers_url
     */
    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    /**
     *
     * @return
     * The followingUrl
     */
    public String getFollowingUrl() {
        return followingUrl;
    }

    /**
     *
     * @param followingUrl
     * The following_url
     */
    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
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
     * The membersUrl
     */
    public String getMembersUrl() {
        return membersUrl;
    }

    /**
     *
     * @param membersUrl
     * The members_url
     */
    public void setMembersUrl(String membersUrl) {
        this.membersUrl = membersUrl;
    }

    /**
     *
     * @return
     * The shotsUrl
     */
    public String getShotsUrl() {
        return shotsUrl;
    }

    /**
     *
     * @param shotsUrl
     * The shots_url
     */
    public void setShotsUrl(String shotsUrl) {
        this.shotsUrl = shotsUrl;
    }

    /**
     *
     * @return
     * The teamShotsUrl
     */
    public String getTeamShotsUrl() {
        return teamShotsUrl;
    }

    /**
     *
     * @param teamShotsUrl
     * The team_shots_url
     */
    public void setTeamShotsUrl(String teamShotsUrl) {
        this.teamShotsUrl = teamShotsUrl;
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

}