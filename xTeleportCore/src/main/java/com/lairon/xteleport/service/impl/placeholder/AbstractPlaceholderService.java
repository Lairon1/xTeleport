package com.lairon.xteleport.service.impl.placeholder;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.service.PlaceholderService;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPlaceholderService implements PlaceholderService {

    @Override
    public String setPlaceholders(@NonNull Player player, @NonNull String string, @NonNull String... placeholders) {
        if(placeholders.length % 2 != 0)
            throw new IllegalArgumentException("Each placeholder must have a value");
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < placeholders.length; i += 2) {
            map.put(placeholders[i], placeholders[i + 1]);
        }

        return setPlaceholders(player, string, map);
    }


}
