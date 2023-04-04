package com.lairon.xteleport.service.impl.placeholder;

import com.lairon.xteleport.model.Player;
import lombok.NonNull;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.Map;

public class StrSubstitutorPlaceholderService extends AbstractPlaceholderService {
    @Override
    public String setPlaceholders(@NonNull Player player, @NonNull String string, @NonNull Map<String, String> placeholders) {
        return new StrSubstitutor(placeholders, "{", "}").replace(string);
    }

}
