package com.lairon.xteleport.xteleportbukkit.service;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.service.impl.placeholder.StrSubstitutorPlaceholderService;
import com.lairon.xteleport.xteleportbukkit.adapter.BukkitAdapter;
import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.Map;

public class PAPIPlaceholderService extends StrSubstitutorPlaceholderService {

    @Override
    public String setPlaceholders(@NonNull Player player, @NonNull String string, @NonNull Map<String, String> placeholders) {
        string = super.setPlaceholders(player, string, placeholders);
        org.bukkit.entity.Player bukkitPlayer = BukkitAdapter.adapt(player);
        if(bukkitPlayer == null) return string;
        else return PlaceholderAPI.setPlaceholders(bukkitPlayer, string);
    }
}
