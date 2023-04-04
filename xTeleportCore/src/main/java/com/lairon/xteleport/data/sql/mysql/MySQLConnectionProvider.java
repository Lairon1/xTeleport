package com.lairon.xteleport.data.sql.mysql;

import com.lairon.xteleport.config.impl.MySQLConfig;
import com.lairon.xteleport.data.sql.SQLConnectionProvider;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnectionProvider implements SQLConnectionProvider {

    private Connection connection;
    private MySQLConfig config;
    private int counter = 0;

    public MySQLConnectionProvider(@NonNull MySQLConfig config) {
        this.config = config;
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        if (connection == null || connection.isClosed()) createConnection();
        return connection;
    }

    private void createConnection() throws Exception {
        System.out.println("Create MySQL connection №" + (++counter));
        connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",
                        config.address(),
                        config.port(),
                        config.dataBase()),
                config.username(),
                config.password());
    }

}