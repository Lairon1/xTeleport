package com.lairon.xteleport.service;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.location.Location;
import lombok.NonNull;

public interface RegionService {

    boolean canChangeRegion(@NonNull Player player, @NonNull Location location);

}
