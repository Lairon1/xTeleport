package com.lairon.xteleport.config.impl.home;

import com.lairon.xteleport.config.Config;

public interface HomeLang extends Config {

    /**
     * Placeholder:
     * {name} - home name
     */
    String teleporting();

    /**
     * Placeholder:
     * {name} - home name
     */
    String successfullySet();

    String canNotInRegion();

    /**
     * Placeholder:
     * {max} - max homes
     */
    String maxHomesMessage();

    String successfullyDelete();

    /**
     * Placeholder:
     * {homes} - homes list
     */
    String homes();

}
