package com.me.xpf.pigggeon.event;

/**
 * Created by pengfeixie on 16/1/30.
 */
public class Event {

    public static class UpdateShotEvent {

        private String shot, sort;

        public UpdateShotEvent(String shot, String sort) {
            this.shot = shot;
            this.sort = sort;
        }

        public String getShot() {
            return shot;
        }

        public String getSort() {
            return sort;
        }
    }

}
