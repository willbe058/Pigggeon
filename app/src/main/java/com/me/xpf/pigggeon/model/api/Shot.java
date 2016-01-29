package com.me.xpf.pigggeon.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xpf on 2015/7/9.
 */

public class Shot implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;


    private int rid;

    @Expose
    private Integer id;

    @Expose
    private String title;

    @Expose
    private String description;

    private int width;
    private int height;

    @Expose
    private Images images;

    @SerializedName("views_count")
    @Expose
    private Integer viewsCount;

    @SerializedName("likes_count")
    @Expose
    private Integer likesCount;

    @SerializedName("comments_count")
    @Expose
    private Integer commentsCount;

    @SerializedName("attachments_count")
    @Expose
    private Integer attachmentsCount;


    @SerializedName("rebounds_count")
    @Expose
    private Integer reboundsCount;

    @SerializedName("buckets_count")
    @Expose
    private Integer bucketsCount;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("html_url")
    @Expose
    private String htmlUrl;

    @SerializedName("attachments_url")
    @Expose
    private String bucketsUrl;

    @SerializedName("comments_url")
    @Expose
    private String commentsUrl;

    @SerializedName("likes_url")
    @Expose
    private String likesUrl;

    @SerializedName("projects_url")
    @Expose
    private String reboundsUrl;

    @Expose
    private List<String> tags = new ArrayList<String>();

    @Expose
    private User user;

    @Expose
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isGif() {
        return this.getImages().getTeaser().endsWith("gif");
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

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
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return The images
     */
    public Images getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(Images images) {
        this.images = images;
    }

    /**
     * @return The viewsCount
     */
    public Integer getViewsCount() {
        return viewsCount;
    }

    /**
     * @param viewsCount The views_count
     */
    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    /**
     * @return The likesCount
     */
    public Integer getLikesCount() {
        return likesCount;
    }

    /**
     * @param likesCount The likes_count
     */
    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    /**
     * @return The commentsCount
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     * @param commentsCount The comments_count
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     * @return The attachmentsCount
     */
    public Integer getAttachmentsCount() {
        return attachmentsCount;
    }

    /**
     * @param attachmentsCount The attachments_count
     */
    public void setAttachmentsCount(Integer attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }

    /**
     * @return The reboundsCount
     */
    public Integer getReboundsCount() {
        return reboundsCount;
    }

    /**
     * @param reboundsCount The rebounds_count
     */
    public void setReboundsCount(Integer reboundsCount) {
        this.reboundsCount = reboundsCount;
    }

    /**
     * @return The bucketsCount
     */
    public Integer getBucketsCount() {
        return bucketsCount;
    }

    /**
     * @param bucketsCount The buckets_count
     */
    public void setBucketsCount(Integer bucketsCount) {
        this.bucketsCount = bucketsCount;
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
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     * @param htmlUrl The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }


    /**
     * @return The bucketsUrl
     */
    public String getBucketsUrl() {
        return bucketsUrl;
    }

    /**
     * @param bucketsUrl The buckets_url
     */
    public void setBucketsUrl(String bucketsUrl) {
        this.bucketsUrl = bucketsUrl;
    }

    /**
     * @return The commentsUrl
     */
    public String getCommentsUrl() {
        return commentsUrl;
    }

    /**
     * @param commentsUrl The comments_url
     */
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    /**
     * @return The likesUrl
     */
    public String getLikesUrl() {
        return likesUrl;
    }

    /**
     * @param likesUrl The likes_url
     */
    public void setLikesUrl(String likesUrl) {
        this.likesUrl = likesUrl;
    }


    /**
     * @return The reboundsUrl
     */
    public String getReboundsUrl() {
        return reboundsUrl;
    }

    /**
     * @param reboundsUrl The rebounds_url
     */
    public void setReboundsUrl(String reboundsUrl) {
        this.reboundsUrl = reboundsUrl;
    }

    /**
     * @return The tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @param tags The tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
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


    @Override
    public String toString() {
        return "Shot{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", images=" + images +
                ", viewsCount=" + viewsCount +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                ", attachmentsCount=" + attachmentsCount +
                ", reboundsCount=" + reboundsCount +
                ", bucketsCount=" + bucketsCount +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", bucketsUrl='" + bucketsUrl + '\'' +
                ", commentsUrl='" + commentsUrl + '\'' +
                ", likesUrl='" + likesUrl + '\'' +
                ", reboundsUrl='" + reboundsUrl + '\'' +
                ", tags=" + tags +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shot shot = (Shot) o;

        if (width != shot.width) return false;
        if (height != shot.height) return false;
        if (!id.equals(shot.id)) return false;
        if (!title.equals(shot.title)) return false;
        if (description != null ? !description.equals(shot.description) : shot.description != null)
            return false;
        if (images != null ? !images.equals(shot.images) : shot.images != null) return false;
        if (viewsCount != null ? !viewsCount.equals(shot.viewsCount) : shot.viewsCount != null)
            return false;
        if (likesCount != null ? !likesCount.equals(shot.likesCount) : shot.likesCount != null)
            return false;
        if (commentsCount != null ? !commentsCount.equals(shot.commentsCount) : shot.commentsCount != null)
            return false;
        if (attachmentsCount != null ? !attachmentsCount.equals(shot.attachmentsCount) : shot.attachmentsCount != null)
            return false;
        if (reboundsCount != null ? !reboundsCount.equals(shot.reboundsCount) : shot.reboundsCount != null)
            return false;
        if (bucketsCount != null ? !bucketsCount.equals(shot.bucketsCount) : shot.bucketsCount != null)
            return false;
        if (createdAt != null ? !createdAt.equals(shot.createdAt) : shot.createdAt != null)
            return false;
        if (updatedAt != null ? !updatedAt.equals(shot.updatedAt) : shot.updatedAt != null)
            return false;
        if (htmlUrl != null ? !htmlUrl.equals(shot.htmlUrl) : shot.htmlUrl != null) return false;
        return !(tags != null ? !tags.equals(shot.tags) : shot.tags != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (viewsCount != null ? viewsCount.hashCode() : 0);
        result = 31 * result + (likesCount != null ? likesCount.hashCode() : 0);
        result = 31 * result + (commentsCount != null ? commentsCount.hashCode() : 0);
        result = 31 * result + (attachmentsCount != null ? attachmentsCount.hashCode() : 0);
        result = 31 * result + (reboundsCount != null ? reboundsCount.hashCode() : 0);
        result = 31 * result + (bucketsCount != null ? bucketsCount.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (htmlUrl != null ? htmlUrl.hashCode() : 0);
        result = 31 * result + (bucketsUrl != null ? bucketsUrl.hashCode() : 0);
        result = 31 * result + (commentsUrl != null ? commentsUrl.hashCode() : 0);
        result = 31 * result + (likesUrl != null ? likesUrl.hashCode() : 0);
        result = 31 * result + (reboundsUrl != null ? reboundsUrl.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
