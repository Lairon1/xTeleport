package com.lairon.xteleport.service.impl;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.location.Location;
import com.lairon.xteleport.service.PlayerService;
import com.lairon.xteleport.service.RegionService;
import lombok.NonNull;

public class GagRegionService implements RegionService {

    @Override
    public boolean canChangeRegion(@NonNull Player player, @NonNull Location location) {
        return true;
    }
}
