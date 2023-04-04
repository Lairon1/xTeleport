package com.lairon.xteleport.config.impl;

import com.lairon.xteleport.config.Config;

public interface MySQLConfig extends Config {

    String address();
    int port();
    String dataBase();
    String username();
    String password();


}
