package com.lairon.xteleport.handler.namedlocation.impl;

import com.lairon.xteleport.config.impl.home.HomeLang;
import com.lairon.xteleport.config.impl.home.HomeSettings;
import com.lairon.xteleport.data.PlayerDataProvider;
import com.lairon.xteleport.handler.namedlocation.HomeHandler;
import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.location.HomeLocation;
import com.lairon.xteleport.model.location.Location;
import com.lairon.xteleport.model.location.NamedLocation;
import com.lairon.xteleport.service.PlayerService;
import com.lairon.xteleport.service.RegionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultHomeHandler implements HomeHandler {

    private final PlayerService playerService;
    private final RegionService regionService;
    private final PlayerDataProvider provider;
    private final HomeSettings homeSettings;
    private final HomeLang homeLang;

    @Override
    public void teleportHandle(@NonNull Player player, @NonNull HomeLocation location) {
        playerService.teleport(player, location);
        playerService.sendMessage(player, homeLang.teleporting(), "name", location.getName());
    }

    @Override
    public void setHandle(@NonNull Player player, @NonNull NamedLocation location) {
        if(homeSettings.checkRegion()
                && !regionService.canChangeRegion(player, location)
                && !playerService.hasPermission(player, homeSettings.regionBypassPermission())){
            playerService.sendMessage(player, homeLang.canNotInRegion());
            return;
        }
        int maxHomes = homeSettings
                .maxHomes()
                .entrySet()
                .stream()
                .filter(max -> playerService.hasPermission(player, max.getKey()))
                .mapToInt(max -> max.getValue())
                .max()
                .orElse(0);

        if(player.getHomes().size() >= maxHomes){
            playerService.sendMessage(player, homeLang.maxHomesMessage(), "max", String.valueOf(maxHomes));
            return;
        }
        HomeLocation home = new HomeLocation(
                location.getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getDirection(),
                location.getWorld(),
                location.getServer()
        );
        player.getHomes().add(home);
        provider.save(player);
        playerService.sendMessage(player, homeLang.successfullySet(), "name", home.getName());
    }

    @Override
    public void deleteHandle(@NonNull Player player, @NonNull HomeLocation location) {

    }

    @Override
    public void moveHandle(@NonNull Player player, @NonNull HomeLocation location, @NonNull Location newLocation) {

    }

    @Override
    public void showAllHandle(@NonNull Player player) {
        String homes = player.getHomes().stream().map(home -> home.getName()).collect(Collectors.joining(", "));
        playerService.sendMessage(player, homeLang.homes(), "homes", homes);
    }
}
