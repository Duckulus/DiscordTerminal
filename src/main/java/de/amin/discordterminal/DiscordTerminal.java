package de.amin.discordterminal;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class DiscordTerminal extends JavaPlugin {

    public static String URL;
    public static String PREFIX;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        config = getConfig();
        File configFile = new File(getDataFolder(), "config.yml");
        saveDefaultConfig();

        if(!config.isConfigurationSection("events")){
            System.out.println("[DiscordTerminal] Deprecated config was found. Creating a new one...");
            boolean deleted = configFile.delete();
            if(!deleted){
                System.out.println("[DiscordTerminal] Fatal Error, disabling Plugin...");
                Bukkit.getPluginManager().disablePlugin(this);
            }
            saveDefaultConfig();
        }

        URL = config.getString("url");
        PREFIX = config.getString("prefix");

        if (!URL.equals("dummy.org")) {
            Bukkit.getPluginManager().registerEvents(new Events(config), this);
            if(config.getBoolean("events.startup")){
                DiscordWebhook webhook = new DiscordWebhook(URL);
                webhook.setContent(PREFIX + "Server Starting...");
                try {
                    webhook.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println(" ");
            System.out.println("[DiscordTerminal] Plugin not activated. Replace dummy url with your Webhook URL in the config!");
            System.out.println(" ");
        }
    }

    @Override
    public void onDisable() {
        if (!URL.equals("dummy.org")) {
            if(config.getBoolean("events.startup")){
                DiscordWebhook webhook = new DiscordWebhook(URL);
                webhook.setContent(PREFIX + "Server stopping...");
                try {
                    webhook.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
