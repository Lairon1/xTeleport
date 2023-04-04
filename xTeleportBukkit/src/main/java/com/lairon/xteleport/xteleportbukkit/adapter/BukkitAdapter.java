package com.lairon.xteleport.xteleportbukkit.adapter;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.Server;
import com.lairon.xteleport.model.Vector;
import com.lairon.xteleport.model.World;
import com.lairon.xteleport.model.location.Location;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class BukkitAdapter {

    public static Location adapt(@NotNull org.bukkit.Location location) {
        return new Location(
                location.getX(),
                location.getY(),
                location.getZ(),
                adapt(location.getDirection()),
                adapt(location.getWorld()),
                new Server("this"));
    }

    public static Vector adapt(@NotNull org.bukkit.util.Vector vector) {
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    public static World adapt(@NotNull org.bukkit.World world) {
        return new World(world.getUID(), world.getName());
    }

    public static org.bukkit.Location adapt(@NotNull Location location) {
        org.bukkit.Location location1 = new org.bukkit.Location(adapt(location.getWorld()), location.getX(), location.getY(), location.getZ());
        location1.setDirection(adapt(location.getDirection()));
        return location1;
    }

    public static org.bukkit.util.Vector adapt(@NotNull Vector vector) {
        return new org.bukkit.util.Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    public static org.bukkit.World adapt(@NotNull World world) {
        return Bukkit.getWorld(world.getUuid());
    }

    public static org.bukkit.entity.Player adapt(@NotNull Player player) {
        return Bukkit.getPlayer(player.getUuid());
    }

    public static Player createNewPlayer(@NotNull org.bukkit.entity.Player player){
        return new Player(player.getUniqueId(), player.getName(), new HashSet<>(), adapt(player.getLocation()));
    }

}
