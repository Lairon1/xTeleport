package com.lairon.xteleport.data.sql.mysql;

import com.lairon.xteleport.config.impl.MySQLConfig;
import com.lairon.xteleport.data.PlayerDataProvider;
import com.lairon.xteleport.data.sql.SQLPlayerDataProvider;
import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.Server;
import com.lairon.xteleport.model.Vector;
import com.lairon.xteleport.model.World;
import com.lairon.xteleport.model.location.HomeLocation;
import com.lairon.xteleport.model.location.Location;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.UUID;

class MySqlPlayerDataProviderTest {

    @Test
    void saveTest() {


        PlayerDataProvider provider = getProvider();

        for (int i = 0; i < 10; i++) {
            Player playerTest = new Player(UUID.randomUUID(),
                    "testPlayer_" + i,
                    new HashSet<>(),
                    null);

            playerTest.getHomes().add(new HomeLocation(
                    "home",
                    Math.random() * 1000, Math.random() * 1000, Math.random() * 1000,
                    new Vector(Math.random(), Math.random(), Math.random()),
                    new World(UUID.randomUUID(), "world"),
                    new Server("testServer")
            ));
            playerTest.getHomes().add(new HomeLocation(
                    "home_nether",
                    Math.random() * 1000, Math.random() * 1000, Math.random() * 1000,
                    new Vector(Math.random(), Math.random(), Math.random()),
                    new World(UUID.randomUUID(), "nether"),
                    new Server("testServer")
            ));
            playerTest.setLastLocation(new Location(
                    Math.random() * 1000, Math.random() * 1000, Math.random() * 1000,
                    new Vector(Math.random(), Math.random(), Math.random()),
                    new World(UUID.randomUUID(), "world"),
                    new Server("testServer")));
            provider.save(playerTest);

            assert provider.findByUUID(playerTest.getUuid()).isPresent();
            assert provider.findByName(playerTest.getName()).isPresent();
        }
    }


    private PlayerDataProvider getProvider() {
        return new SQLPlayerDataProvider(new MySQLConnectionProvider(new MySQLConfig() {
            @Override
            public String address() {
                return "localhost";
            }

            @Override
            public int port() {
                return 3306;
            }

            @Override
            public String dataBase() {
                return "minecraft";
            }

            @Override
            public String username() {
                return "minecraft";
            }

            @Override
            public String password() {
                return "minecraft";
            }

            @Override
            public void reload() {

            }
        }));
    }

}