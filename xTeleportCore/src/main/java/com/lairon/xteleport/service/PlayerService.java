package com.lairon.xteleport.service;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.location.HomeLocation;
import com.lairon.xteleport.model.location.Location;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

public interface PlayerService {

    void teleport(@NonNull Player player, @NonNull Location location);
    Location getLocation(@NonNull Player player);
    void sendMessage(@NonNull Player player, @NonNull String message);
    void sendMessage(@NonNull Player player, @NonNull String message, @NonNull String... placeholders);
    void sendMessage(@NonNull Player player, @NonNull String message, @NonNull Map<String, String> placeholders);
    boolean hasPermission(@NonNull Player player, @NonNull String permission);
    Optional<HomeLocation> findHomeByName(@NonNull Player player, @NonNull String name);

}
