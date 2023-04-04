package com.lairon.xteleport.model.location;

import com.lairon.xteleport.model.Server;
import com.lairon.xteleport.model.Vector;
import com.lairon.xteleport.model.World;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Location {

    private double x, y, z;
    private Vector direction;
    private World world;
    private Server server;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.x, x) != 0) return false;
        if (Double.compare(location.y, y) != 0) return false;
        if (Double.compare(location.z, z) != 0) return false;
        if (!direction.equals(location.direction)) return false;
        return world.equals(location.world);
    }

}
