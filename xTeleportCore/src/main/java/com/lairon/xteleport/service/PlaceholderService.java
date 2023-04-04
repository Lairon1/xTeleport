package com.lairon.xteleport.service;

import com.lairon.xteleport.model.Player;
import lombok.NonNull;

import java.util.Map;

public interface PlaceholderService {

    String setPlaceholders(@NonNull Player player, @NonNull String string, @NonNull Map<String, String> placeholders);
    String setPlaceholders(@NonNull Player player, @NonNull String string, @NonNull String... placeholders);

}
