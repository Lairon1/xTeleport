package com.lairon.xteleport.data.sql.mysql;

import com.lairon.xteleport.config.impl.MySQLConfig;
import com.lairon.xteleport.data.SpawnDataProvider;
import com.lairon.xteleport.data.sql.SQLSpawnDataProvider;
import com.lairon.xteleport.model.Server;
import com.lairon.xteleport.model.Vector;
import com.lairon.xteleport.model.World;
import com.lairon.xteleport.model.location.SpawnLocation;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class MySQLSpawnDataProviderTest {

    @Test
    void saveTest() {
        SpawnDataProvider provider = getProvider();

        for (int i = 0; i < 10; i++) {
            String spawnName = "spawn" + i;

            SpawnLocation spawnLocation = new SpawnLocation(
                    spawnName,
                    Math.random() * 1000,Math.random() * 100, Math.random() * 1000,
                    new Vector(Math.random(), Math.random(), Math.random()),
                    new World(UUID.randomUUID(), "world"),
                    new Server("anarchy")
            );
            provider.save(spawnLocation);
            assert provider.findByName(spawnName).isPresent();
            provider.delete(spawnLocation);
            assert !provider.findByName(spawnName).isPresent();
        }

    }

    private SpawnDataProvider getProvider() {
        return new SQLSpawnDataProvider(new MySQLConnectionProvider(new MySQLConfig() {
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
