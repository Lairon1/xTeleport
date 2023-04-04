package com.lairon.xteleport.service;

import com.lairon.xteleport.model.World;
import com.lairon.xteleport.model.location.Location;
import lombok.NonNull;

public interface LocationService {

    Location generateRandomLocation(@NonNull World world, @NonNull Location min, @NonNull Location max, boolean checkRegion);
}
