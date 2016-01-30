package com.me.xpf.pigggeon.event;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by pengfeixie on 16/1/30.
 */
public class BusProvider {

    private static final Bus instance = new Bus(ThreadEnforcer.MAIN);

    public static Bus getInstance() {
        return instance;
    }
}
