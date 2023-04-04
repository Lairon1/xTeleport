package com.lairon.xteleport.data.sql;

import java.sql.Connection;

public interface SQLConnectionProvider {

    Connection getConnection();

}
