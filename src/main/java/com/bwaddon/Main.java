package com.esycho.bwaddon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        // Check BedWars1058
        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            getLogger().severe("BedWars1058 not found! Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Save config
        saveDefaultConfig();

        // Register Commands
        getCommand("cosmetics").setExecutor(new CosmeticsCommand());

        // Register Events
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        getLogger().info("=================================");
        getLogger().info("BedWars1058CosmeticsPRO Enabled! Enjoy");
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info("Author: Esycho");
        getLogger().info("=================================");
    }

    @Override
    public void onDisable() {
        getLogger().info("BedWars1058CosmeticsPRO Disabled!");
    }

    public static Main getInstance() {
        return instance;
    }
}