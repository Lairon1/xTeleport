package com.lairon.xteleport.xteleportbukkit.service;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.location.Location;
import com.lairon.xteleport.service.PlaceholderService;
import com.lairon.xteleport.service.impl.AbstractPlayerService;
import com.lairon.xteleport.xteleportbukkit.adapter.BukkitAdapter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@RequiredArgsConstructor
public class BukkitPlayerService extends AbstractPlayerService {

    private final PlaceholderService placeholderService;

    @Override
    public void teleport(@NonNull Player player, @NonNull Location location) {
        org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
        org.bukkit.Location bukkitLocation = BukkitAdapter.adapt(location);

        if (bukkitPlayer != null && bukkitLocation != null && bukkitLocation.getWorld() != null)
            bukkitPlayer.teleportAsync(bukkitLocation);
    }

    @Override
    public Location getLocation(@NonNull Player player) {
        org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
        return BukkitAdapter.adapt(bukkitPlayer.getLocation());
    }

    @Override
    public void sendMessage(@NonNull Player player, @NonNull String message) {
        if (message.isEmpty()) return;
        org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
        if (bukkitPlayer == null) return;
        TextComponent deserialize = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        MiniMessage build = MiniMessage.builder().build();
        Component finalMessage = build.deserialize(build.serialize(deserialize));
        bukkitPlayer.sendMessage(finalMessage);
    }

    @Override
    public void sendMessage(@NonNull Player player, @NonNull String message, @NotNull @NonNull String... placeholders) {
        sendMessage(player, placeholderService.setPlaceholders(player, message, placeholders));
    }

    @Override
    public void sendMessage(@NonNull Player player, @NonNull String message, @NonNull Map<String, String> placeholders) {
        sendMessage(player, placeholderService.setPlaceholders(player, message, placeholders));
    }

    @Override
    public boolean hasPermission(@NonNull Player player, @NonNull String permission) {
        org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
        if (bukkitPlayer == null) return false;
        return bukkitPlayer.hasPermission(permission);
    }
}
