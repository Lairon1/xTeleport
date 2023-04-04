package com.lairon.xteleport.data;

import com.lairon.xteleport.model.Player;
import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface PlayerDataProvider {

    Optional<Player> findByUUID(@NonNull UUID uuid);
    Optional<Player> findByName(@NonNull String name);
    void save(@NonNull Player player);

}
