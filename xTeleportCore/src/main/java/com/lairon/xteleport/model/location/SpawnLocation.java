package com.lairon.xteleport.model.location;

import com.lairon.xteleport.model.Server;
import com.lairon.xteleport.model.Vector;
import com.lairon.xteleport.model.World;

import java.util.UUID;

public class SpawnLocation extends NamedLocation{
    public SpawnLocation(String name,  double x, double y, double z, Vector direction, World world, Server server) {
        super(name, x, y, z, direction, world, server);
    }
}
