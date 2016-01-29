package com.me.xpf.pigggeon.model.api;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by xpf on 2015/7/9.
 */

public class Images implements Serializable {

    private static final long serialVersionUID = -706021054462464481L;

    private int rid;

    @Expose
    private String hidpi;

    @Expose
    private String normal;

    @Expose
    private String teaser;


    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    /**
     * @return The hidpi
     */
    public String getHidpi() {
        return hidpi;
    }

    /**
     * @param hidpi The hidpi
     */
    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    /**
     * @return The normal
     */
    public String getNormal() {
        return normal;
    }

    /**
     * @param normal The normal
     */
    public void setNormal(String normal) {
        this.normal = normal;
    }

    /**
     * @return The teaser
     */
    public String getTeaser() {
        return teaser;
    }

    /**
     * @param teaser The teaser
     */
    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Images images = (Images) o;

        if (hidpi != null ? !hidpi.equals(images.hidpi) : images.hidpi != null) return false;
        if (normal != null ? !normal.equals(images.normal) : images.normal != null) return false;
        return !(teaser != null ? !teaser.equals(images.teaser) : images.teaser != null);

    }

    @Override
    public int hashCode() {
        int result = hidpi != null ? hidpi.hashCode() : 0;
        result = 31 * result + (normal != null ? normal.hashCode() : 0);
        result = 31 * result + (teaser != null ? teaser.hashCode() : 0);
        return result;
    }
}
