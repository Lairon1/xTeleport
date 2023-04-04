package com.lairon.xteleport.xteleportbukkit.service;

import com.lairon.xteleport.model.World;
import com.lairon.xteleport.model.location.Location;
import com.lairon.xteleport.service.LocationService;
import com.lairon.xteleport.service.RegionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BukkitLocationService implements LocationService {

    private final RegionService regionService;


    @Override
    public Location generateRandomLocation(@NonNull World world, @NonNull Location min, @NonNull Location max, boolean checkRegion) {
        return null;
    }
}
