package com.lairon.xteleport.config.impl.home;

import com.lairon.xteleport.config.Config;

import java.util.Map;

public interface HomeSettings extends Config {

    Boolean checkRegion();
    String regionBypassPermission();
    Map<String, Integer> maxHomes();


}
