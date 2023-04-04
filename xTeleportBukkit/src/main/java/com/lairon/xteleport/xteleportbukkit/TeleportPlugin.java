package com.lairon.xteleport.xteleportbukkit;

import com.lairon.xteleport.config.impl.MySQLConfig;
import com.lairon.xteleport.config.impl.home.HomeLang;
import com.lairon.xteleport.config.impl.home.HomeSettings;
import com.lairon.xteleport.config.impl.home.gag.GagHomeConfig;
import com.lairon.xteleport.data.PlayerDataProvider;
import com.lairon.xteleport.data.sql.SQLPlayerDataProvider;
import com.lairon.xteleport.data.sql.mysql.MySQLConnectionProvider;
import com.lairon.xteleport.handler.namedlocation.HomeHandler;
import com.lairon.xteleport.handler.namedlocation.impl.DefaultHomeHandler;
import com.lairon.xteleport.model.location.HomeLocation;
import com.lairon.xteleport.model.location.NamedLocation;
import com.lairon.xteleport.service.PlaceholderService;
import com.lairon.xteleport.service.PlayerService;
import com.lairon.xteleport.service.RegionService;
import com.lairon.xteleport.service.impl.GagRegionService;
import com.lairon.xteleport.service.impl.placeholder.StrSubstitutorPlaceholderService;
import com.lairon.xteleport.xteleportbukkit.adapter.BukkitAdapter;
import com.lairon.xteleport.xteleportbukkit.service.BukkitPlayerService;
import com.lairon.xteleport.xteleportbukkit.service.PAPIPlaceholderService;
import com.lairon.xteleport.xteleportbukkit.service.WGRegionService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeleportPlugin extends JavaPlugin {

    private HomeHandler homeHandler;
    private PlayerService playerService;
    private RegionService regionService;
    private PlayerDataProvider playerDataProvider;
    private HomeSettings homeSettings;
    private HomeLang homeLang;
    private PlaceholderService placeholderService;

    @Override
    public void onEnable() {
        setupPlaceholderService();
        setupRegionService();
        setupPlayerDataProvider();
        homeSettings = new GagHomeConfig();
        homeLang = (HomeLang) homeSettings;

        playerService = new BukkitPlayerService(placeholderService);

        homeHandler = new DefaultHomeHandler(
                playerService,
                regionService,
                playerDataProvider,
                homeSettings,
                homeLang
        );
        Bukkit.getPluginCommand("setHome").unregister(Bukkit.getCommandMap());


        Bukkit.getPluginCommand("home").setExecutor((sender, command, label, args) -> {
            Player bukkitPlayer = (Player) sender;

            com.lairon.xteleport.model.Player player = playerDataProvider
                    .findByUUID(bukkitPlayer.getUniqueId())
                    .orElse(BukkitAdapter.createNewPlayer(bukkitPlayer));

            HomeLocation home = playerService.findHomeByName(player, args[0]).orElse(null);

            homeHandler.teleportHandle(player, home);
            return false;
        });

        Bukkit.getPluginCommand("setHome").setExecutor((sender, command, label, args) -> {
            Player bukkitPlayer = (Player) sender;

            com.lairon.xteleport.model.Player player = playerDataProvider
                    .findByUUID(bukkitPlayer.getUniqueId())
                    .orElse(BukkitAdapter.createNewPlayer(bukkitPlayer));

            homeHandler.setHandle(player, NamedLocation.of(BukkitAdapter.adapt(bukkitPlayer.getLocation()), args[0]));

            return false;
        });

    }

    private void setupPlaceholderService() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            placeholderService = new PAPIPlaceholderService();
        else
            placeholderService = new StrSubstitutorPlaceholderService();
    }

    private void setupRegionService() {
        if (false)
            regionService = new WGRegionService();
        else
            regionService = new GagRegionService();
    }

    private void setupPlayerDataProvider() {
        playerDataProvider = new SQLPlayerDataProvider(new MySQLConnectionProvider(new MySQLConfig() {
            @Override
            public void reload() {

            }

            @Override
            public String address() {
                return "localhost";
            }

            @Override
            public int port() {
                return 3306;
            }

            @Override
            public String dataBase() {
                return "minecraft";
            }

            @Override
            public String username() {
                return "minecraft";
            }

            @Override
            public String password() {
                return "minecraft";
            }

        }));
    }

    @Override
    public void onDisable() {

    }
}
