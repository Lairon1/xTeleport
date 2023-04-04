package com.lairon.xteleport.data.sql;

import com.lairon.xteleport.data.SpawnDataProvider;
import com.lairon.xteleport.model.Server;
import com.lairon.xteleport.model.Vector;
import com.lairon.xteleport.model.World;
import com.lairon.xteleport.model.location.SpawnLocation;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLSpawnDataProvider implements SpawnDataProvider {

    private final SQLConnectionProvider connectionProvider;

    public SQLSpawnDataProvider(@NonNull SQLConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createTables();
    }

    @Override
    @SneakyThrows
    public Optional<SpawnLocation> findByName(@NonNull String name) {
        @Cleanup PreparedStatement statement = connectionProvider
                .getConnection()
                .prepareStatement("SELECT * FROM `xtp_spawns` WHERE `name` = ?;");

        statement.setString(1, name);

        @Cleanup ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) return Optional.of(parseLocation(resultSet));
        else return Optional.empty();
    }

    @Override
    @SneakyThrows
    public List<SpawnLocation> findAll() {
        @Cleanup Statement statement = connectionProvider.getConnection().createStatement();
        @Cleanup ResultSet resultSet = statement.executeQuery("SELECT * FROM `xtp_spawns`;");
        if (resultSet.next()) return parseLocations(resultSet);
        else return new ArrayList<>();
    }

    @Override
    @SneakyThrows
    public void save(@NonNull SpawnLocation spawn) {
        @Cleanup PreparedStatement statement = connectionProvider
                .getConnection()
                .prepareStatement("""
                        INSERT INTO `xtp_spawns` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                        ON DUPLICATE KEY UPDATE
                        `x` = VALUES(`x`),
                        `y` = VALUES(`y`),
                        `z` = VALUES(`z`),
                        `direction_x` = VALUES(`direction_x`),
                        `direction_y` = VALUES(`direction_y`),
                        `direction_z` = VALUES(`direction_z`),
                        `world_uuid` = VALUES(`world_uuid`),
                        `world_name` = VALUES(`world_name`),
                        `server` = VALUES(`server`);
                        """);

        statement.setString(1, spawn.getName());
        statement.setDouble(2, spawn.getX());
        statement.setDouble(3, spawn.getY());
        statement.setDouble(4, spawn.getZ());
        statement.setDouble(5, spawn.getDirection().getZ());
        statement.setDouble(6, spawn.getDirection().getZ());
        statement.setDouble(7, spawn.getDirection().getZ());
        statement.setString(8, spawn.getWorld().getUuid().toString());
        statement.setString(9, spawn.getWorld().getName());
        statement.setString(10, spawn.getServer().getName());

        statement.execute();
    }

    @Override
    @SneakyThrows
    public void delete(@NonNull SpawnLocation spawn) {
        @Cleanup PreparedStatement statement = connectionProvider
                .getConnection()
                .prepareStatement("DELETE FROM `xtp_spawns` WHERE `name` = ?");
        statement.setString(1, spawn.getName());
        statement.execute();
    }

    private SpawnLocation parseLocation(@NonNull ResultSet resultSet) throws SQLException {
        return new SpawnLocation(
                resultSet.getString("name"),
                resultSet.getDouble("x"),
                resultSet.getDouble("y"),
                resultSet.getDouble("z"),
                new Vector(
                        resultSet.getDouble("direction_x"),
                        resultSet.getDouble("direction_y"),
                        resultSet.getDouble("direction_z")
                ),
                new World(
                        UUID.fromString(resultSet.getString("world_uuid")),
                        resultSet.getString("world_name")
                ),
                new Server(resultSet.getString("server"))
        );
    }

    private List<SpawnLocation> parseLocations(@NonNull ResultSet resultSet) throws SQLException {
        List<SpawnLocation> spawnLocations = new ArrayList<>();
        while (resultSet.next()) {
            spawnLocations.add(parseLocation(resultSet));
        }
        return spawnLocations;
    }

    @SneakyThrows
    private void createTables() {
        @Cleanup Statement statement = connectionProvider.getConnection().createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `xtp_spawns`
                (
                    `name`             VARCHAR(200) NOT NULL,
                    `x`                DOUBLE       NOT NULL,
                    `y`                DOUBLE       NOT NULL,
                    `z`                DOUBLE       NOT NULL,
                    `direction_x`      DOUBLE       NOT NULL,
                    `direction_y`      DOUBLE       NOT NULL,
                    `direction_z`      DOUBLE       NOT NULL,
                    `world_uuid`       VARCHAR(36)  NOT NULL,
                    `world_name`       VARCHAR(200)  NOT NULL,
                    `server`           VARCHAR(200) NOT NULL,
                    UNIQUE (`name`),
                    PRIMARY KEY (`name`)
                );
                """);
    }

}
