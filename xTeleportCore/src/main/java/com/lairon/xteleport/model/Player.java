package com.lairon.xteleport.model;

import com.lairon.xteleport.model.location.HomeLocation;
import com.lairon.xteleport.model.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Player {

    private final UUID uuid;
    private final String name;
    private Set<HomeLocation> homes;
    private Location lastLocation;

}
