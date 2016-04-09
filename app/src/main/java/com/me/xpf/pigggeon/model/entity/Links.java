package com.me.xpf.pigggeon.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by xpf on 2015/7/24.
 */
public class Links implements Serializable {

    @Expose
    private String web;
    @Expose
    private String twitter;

    /**
     *
     * @return
     * The web
     */
    public String getWeb() {
        return web;
    }

    /**
     *
     * @param web
     * The web
     */
    public void setWeb(String web) {
        this.web = web;
    }

    /**
     *
     * @return
     * The twitter
     */
    public String getTwitter() {
        return twitter;
    }

    /**
     *
     * @param twitter
     * The twitter
     */
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

}
