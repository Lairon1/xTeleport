package com.lairon.xteleport.model;

import lombok.Data;

import java.util.UUID;

@Data
public class World {

    private final UUID uuid;
    private final String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        World world = (World) o;

        return uuid.equals(world.uuid);
    }

}
