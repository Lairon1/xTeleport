package com.lairon.xteleport.config.impl.home.gag;

import com.lairon.xteleport.config.impl.home.HomeLang;
import com.lairon.xteleport.config.impl.home.HomeSettings;

import java.util.HashMap;
import java.util.Map;

public class GagHomeConfig implements HomeLang, HomeSettings {


    @Override
    public void reload() {

    }

    /**
     * Placeholder:
     * {name} - home name
     */
    @Override
    public String teleporting() {
        return "&7[&6XTP&7] Телепортация на точку дома &6{name}";
    }

    /**
     * Placeholder:
     * {name} - home name
     */
    @Override
    public String successfullySet() {
        return "&7[&6XTP&7] Точка дома &6{name}&7 успешно установлена";
    }

    @Override
    public String canNotInRegion() {
        return "&7[&6XTP&7] Вы не можете установить точку дома в регионе";
    }

    /**
     * Placeholder:
     * {max} - max homes
     */
    @Override
    public String maxHomesMessage() {
        return "&7[&6XTP&7] Вы превысили максимальный лимит домов в &6{max}&7 штук";
    }

    @Override
    public String successfullyDelete() {
        return "&7[&6XTP&7] Вы успешно удалили точку дома {name}";
    }

    /**
     * Placeholder:
     * {homes} - homes list
     */
    @Override
    public String homes() {
        return "&7[&6XTP&7] Ваши дома >> {homes}";
    }

    @Override
    public Boolean checkRegion() {
        return true;
    }

    @Override
    public String regionBypassPermission() {
        return "xtp.bypass.region";
    }

    @Override
    public Map<String, Integer> maxHomes() {
        return new HashMap<>(){{
            put("xtp.homes.1", 1);
            put("xtp.homes.10", 10);
        }};
    }
}
