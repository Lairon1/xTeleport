package com.lairon.xteleport.service.impl;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.location.HomeLocation;
import com.lairon.xteleport.service.PlayerService;
import lombok.NonNull;

import java.util.Optional;

public abstract class AbstractPlayerService implements PlayerService {

    @Override
    public Optional<HomeLocation> findHomeByName(@NonNull Player player, @NonNull String name) {
        return player
                .getHomes()
                .stream()
                .filter(home -> home.getName().equals(name))
                .findFirst();
    }
}
