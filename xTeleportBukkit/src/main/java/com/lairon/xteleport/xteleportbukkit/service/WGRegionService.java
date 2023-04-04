package com.lairon.xteleport.xteleportbukkit.service;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.location.Location;
import com.lairon.xteleport.service.RegionService;
import com.lairon.xteleport.xteleportbukkit.adapter.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import lombok.NonNull;

public class WGRegionService implements RegionService {
    @Override
    public boolean canChangeRegion(@NonNull Player player, @NonNull Location location) {
        org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
        org.bukkit.Location bukkitLocation = BukkitAdapter.adapt(location);
        if (bukkitPlayer == null || bukkitLocation == null) return false;

        RegionManager regionManager = WorldGuard
                .getInstance()
                .getPlatform()
                .getRegionContainer()
                .get(com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(bukkitLocation.getWorld()));

        ApplicableRegionSet applicableRegions = regionManager
                .getApplicableRegions(com.sk89q.worldedit.bukkit.BukkitAdapter.asBlockVector(bukkitLocation));

        return applicableRegions.testState((RegionAssociable) com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(bukkitPlayer), Flags.BUILD);

    }
}
