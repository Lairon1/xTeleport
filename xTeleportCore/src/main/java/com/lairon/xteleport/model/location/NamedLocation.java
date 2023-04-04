package com.lairon.xteleport.model.location;

import com.lairon.xteleport.model.Server;
import com.lairon.xteleport.model.Vector;
import com.lairon.xteleport.model.World;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class NamedLocation extends Location {

    private String name;

    public NamedLocation(String name, double x, double y, double z, Vector direction, World world, Server server) {
        super(x, y, z, direction, world, server);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NamedLocation that = (NamedLocation) o;

        return name.equals(that.name);
    }

    public static NamedLocation of(@NonNull Location location, @NonNull String name) {
        return new NamedLocation(
                name,
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getDirection(),
                location.getWorld(),
                location.getServer());
    }

}
