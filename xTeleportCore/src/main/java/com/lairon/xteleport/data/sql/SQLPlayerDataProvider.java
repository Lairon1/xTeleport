package com.lairon.xteleport.data.sql;

import com.lairon.xteleport.data.PlayerDataProvider;
import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.Server;
import com.lairon.xteleport.model.Vector;
import com.lairon.xteleport.model.World;
import com.lairon.xteleport.model.location.HomeLocation;
import com.lairon.xteleport.model.location.Location;
import lombok.NonNull;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

public class SQLPlayerDataProvider implements PlayerDataProvider {

    private final SQLConnectionProvider connectionProvider;

    public SQLPlayerDataProvider(@NonNull SQLConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createTables();
    }

    @Override
    public Optional<Player> findByUUID(@NonNull UUID uuid) {
        Connection connection = connectionProvider.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement("""
                        SELECT *
                        FROM `xtp_players`
                                 JOIN `xtp_homes` AS `home`
                        WHERE `xtp_players`.`uuid` = ? AND `home`.`owner` = `xtp_players`.`uuid`;
                        """);
        ) {
            statement.setString(1, uuid.toString());
            return parsePlayer(statement.executeQuery());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Player> findByName(@NonNull String name) {
        Connection connection = connectionProvider.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement("""
                        SELECT *
                        FROM `xtp_players`
                                 JOIN `xtp_homes` AS `home`
                        WHERE `xtp_players`.`name` = ? AND `home`.`owner` = `xtp_players`.`uuid`;
                        """);
        ) {
            statement.setString(1, name);
            return parsePlayer(statement.executeQuery());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Player> parsePlayer(@NonNull ResultSet resultSet) throws SQLException {
        Player player = null;

        while (resultSet.next()) {
            if (player == null) {
                player = new Player(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        new HashSet<>(),
                        new Location(
                                resultSet.getDouble("last_location_x"),
                                resultSet.getDouble("last_location_y"),
                                resultSet.getDouble("last_location_z"),
                                new Vector(
                                        resultSet.getDouble("last_location_direction_x"),
                                        resultSet.getDouble("last_location_direction_y"),
                                        resultSet.getDouble("last_location_direction_z")
                                ),
                                new World(
                                        UUID.fromString(resultSet.getString("last_location_world_uuid")),
                                        resultSet.getString("last_location_world_name")
                                ),
                                new Server(resultSet.getString("last_location_server"))
                        )
                );


            }
            try{
                player.getHomes().add(new HomeLocation(
                        resultSet.getString("home.name"),
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

                ));
            }catch (Exception e){}
        }
        if(player == null) return Optional.empty();
        return Optional.of(player);
    }

    @Override
    public void save(@NonNull Player player) {
        Connection connection = connectionProvider.getConnection();
        try (
                PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM `xtp_players` WHERE `uuid` = ?;");
                PreparedStatement playerInsertStatement = connection.prepareStatement("INSERT INTO `xtp_players` VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                PreparedStatement insertHomeStatement = connection.prepareStatement("INSERT INTO `xtp_homes` VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        ) {

            deleteStatement.setString(1, player.getUuid().toString());

            playerInsertStatement.setString(1, player.getUuid().toString());
            playerInsertStatement.setString(2, player.getName());
            playerInsertStatement.setDouble(3, player.getLastLocation().getX());
            playerInsertStatement.setDouble(4, player.getLastLocation().getY());
            playerInsertStatement.setDouble(5, player.getLastLocation().getZ());
            playerInsertStatement.setDouble(6, player.getLastLocation().getDirection().getX());
            playerInsertStatement.setDouble(7, player.getLastLocation().getDirection().getY());
            playerInsertStatement.setDouble(8, player.getLastLocation().getDirection().getZ());
            playerInsertStatement.setString(9, player.getLastLocation().getWorld().getUuid().toString());
            playerInsertStatement.setString(10, player.getLastLocation().getWorld().getName());
            playerInsertStatement.setString(11, player.getLastLocation().getServer().getName());


            for (HomeLocation home : player.getHomes()) {
                insertHomeStatement.setString(1, home.getName());
                insertHomeStatement.setDouble(2, home.getX());
                insertHomeStatement.setDouble(3, home.getY());
                insertHomeStatement.setDouble(4, home.getZ());
                insertHomeStatement.setDouble(5, home.getDirection().getX());
                insertHomeStatement.setDouble(6, home.getDirection().getY());
                insertHomeStatement.setDouble(7, home.getDirection().getZ());
                insertHomeStatement.setString(8, home.getWorld().getUuid().toString());
                insertHomeStatement.setString(9, home.getWorld().getName());
                insertHomeStatement.setString(10, home.getServer().getName());
                insertHomeStatement.setString(11, player.getUuid().toString());
                insertHomeStatement.addBatch();
            }
            deleteStatement.execute();
            playerInsertStatement.execute();
            insertHomeStatement.executeBatch();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void createTables() {
        Connection connection = connectionProvider.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.addBatch("""
                    CREATE TABLE IF NOT EXISTS `xtp_players`
                    (
                        `uuid`                      VARCHAR(36)  NOT NULL,
                        `name`                      VARCHAR(200) NOT NULL,
                        `last_location_x`           DOUBLE       NOT NULL,
                        `last_location_y`           DOUBLE       NOT NULL,
                        `last_location_z`           DOUBLE       NOT NULL,
                        `last_location_direction_x` DOUBLE       NOT NULL,
                        `last_location_direction_y` DOUBLE       NOT NULL,
                        `last_location_direction_z` DOUBLE       NOT NULL,
                        `last_location_world_uuid`       VARCHAR(36)  NOT NULL,
                        `last_location_world_name`       VARCHAR(200)  NOT NULL,
                        `last_location_server`      VARCHAR(200) NOT NULL,
                        UNIQUE (`uuid`, `name`),
                        PRIMARY KEY (`uuid`)
                    );             
                    """);
            statement.addBatch("""
                    CREATE TABLE IF NOT EXISTS `xtp_homes`
                    (
                        `name`        VARCHAR(200) NOT NULL,
                        `x`           DOUBLE       NOT NULL,
                        `y`           DOUBLE       NOT NULL,
                        `z`           DOUBLE       NOT NULL,
                        `direction_x` DOUBLE       NOT NULL,
                        `direction_y` DOUBLE       NOT NULL,
                        `direction_z` DOUBLE       NOT NULL,
                        `world_uuid`       VARCHAR(36)  NOT NULL,
                        `world_name`       VARCHAR(200)  NOT NULL,
                        `server`      VARCHAR(200) NOT NULL,
                        `owner`       VARCHAR(36)  NOT NULL,
                        FOREIGN KEY (`owner`) REFERENCES `xtp_players` (`uuid`) ON DELETE CASCADE
                    );
                    """);
            statement.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
