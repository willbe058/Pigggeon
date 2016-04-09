package com.me.xpf.pigggeon.model.entity;

import java.io.Serializable;

/**
 * Created by xpf on 2015/8/22.
 */
public interface Userable extends Serializable {

    /**
     * @return The id
     */
    Integer getId();

    /**
     * @param id The id
     */
    void setId(Integer id);

    /**
     * @return The name
     */
    String getName();

    /**
     * @param name The name
     */
    void setName(String name);

    /**
     * @return The username
     */
    String getUsername();

    /**
     * @param username The username
     */
    void setUsername(String username);

    /**
     * @return The htmlUrl
     */
    String getHtmlUrl();

    /**
     * @param htmlUrl The html_url
     */
    void setHtmlUrl(String htmlUrl);

    /**
     * @return The avatarUrl
     */
    String getAvatarUrl();

    /**
     * @param avatarUrl The avatar_url
     */
    void setAvatarUrl(String avatarUrl);

    /**
     * @return The bio
     */
    String getBio();

    /**
     * @param bio The bio
     */
    void setBio(String bio);

    /**
     * @return The location
     */
    String getLocation();

    /**
     * @param location The location
     */
    void setLocation(String location);

    /**
     *
     * @return
     * The links
     */

    /**
     *
     * @param links
     * The links
     */

    /**
     * @return The bucketsCount
     */
    Integer getBucketsCount();

    /**
     * @param bucketsCount The buckets_count
     */
    void setBucketsCount(Integer bucketsCount);

    /**
     * @return The commentsReceivedCount
     */
    Integer getCommentsReceivedCount();

    /**
     * @param commentsReceivedCount The comments_received_count
     */
    void setCommentsReceivedCount(Integer commentsReceivedCount);

    /**
     * @return The followersCount
     */
    Integer getFollowersCount();

    /**
     * @param followersCount The followers_count
     */
    void setFollowersCount(Integer followersCount);

    /**
     * @return The followingsCount
     */
    Integer getFollowingsCount();

    /**
     * @param followingsCount The followings_count
     */
    void setFollowingsCount(Integer followingsCount);

    /**
     * @return The likesCount
     */
    Integer getLikesCount();

    /**
     * @param likesCount The likes_count
     */
    void setLikesCount(Integer likesCount);

    /**
     * @return The likesReceivedCount
     */
    Integer getLikesReceivedCount();

    /**
     * @param likesReceivedCount The likes_received_count
     */
    void setLikesReceivedCount(Integer likesReceivedCount);

    /**
     * @return The projectsCount
     */
    Integer getProjectsCount();

    /**
     * @param projectsCount The projects_count
     */
    void setProjectsCount(Integer projectsCount);

    /**
     * @return The reboundsReceivedCount
     */
    Integer getReboundsReceivedCount();

    /**
     * @param reboundsReceivedCount The rebounds_received_count
     */
    void setReboundsReceivedCount(Integer reboundsReceivedCount);

    /**
     * @return The shotsCount
     */
    Integer getShotsCount();

    /**
     * @param shotsCount The shots_count
     */
    void setShotsCount(Integer shotsCount);

    /**
     * @return The teamsCount
     */
    Integer getTeamsCount();

    /**
     * @param teamsCount The teams_count
     */
    void setTeamsCount(Integer teamsCount);

    /**
     * @return The canUploadShot
     */
    Boolean getCanUploadShot();

    /**
     * @param canUploadShot The can_upload_shot
     */
    void setCanUploadShot(Boolean canUploadShot);

    /**
     * @return The type
     */
    String getType();

    /**
     * @param type The type
     */
    void setType(String type);

    /**
     * @return The pro
     */
    Boolean getPro();

    /**
     * @param pro The pro
     */
    void setPro(Boolean pro);

    /**
     * @return The bucketsUrl
     */
    String getBucketsUrl();

    /**
     * @param bucketsUrl The buckets_url
     */
    void setBucketsUrl(String bucketsUrl);

    /**
     * @return The followersUrl
     */
    String getFollowersUrl();

    /**
     * @param followersUrl The followers_url
     */
    void setFollowersUrl(String followersUrl);

    /**
     * @return The followingUrl
     */
    String getFollowingUrl();

    /**
     * @param followingUrl The following_url
     */
    void setFollowingUrl(String followingUrl);

    /**
     * @return The likesUrl
     */
    String getLikesUrl();

    /**
     * @param likesUrl The likes_url
     */
    void setLikesUrl(String likesUrl);

    /**
     * @return The projectsUrl
     */
    String getProjectsUrl();

    /**
     * @param projectsUrl The projects_url
     */
    void setProjectsUrl(String projectsUrl);

    /**
     * @return The shotsUrl
     */
    String getShotsUrl();

    /**
     * @param shotsUrl The shots_url
     */
    void setShotsUrl(String shotsUrl);

    /**
     * @return The teamsUrl
     */
    String getTeamsUrl();

    /**
     * @param teamsUrl The teams_url
     */
    void setTeamsUrl(String teamsUrl);

    /**
     * @return The createdAt
     */
    String getCreatedAt();

    /**
     * @param createdAt The created_at
     */
    void setCreatedAt(String createdAt);

    /**
     * @return The updatedAt
     */
    String getUpdatedAt();

    /**
     * @param updatedAt The updated_at
     */
    void setUpdatedAt(String updatedAt);

}
