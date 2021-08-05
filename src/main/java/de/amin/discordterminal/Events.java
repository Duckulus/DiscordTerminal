//Created by Duckulus on 03 Aug, 2021 

package de.amin.discordterminal;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class Events implements Listener {

    private final String url;
    private final String prefix;
    private FileConfiguration config;

    public Events(FileConfiguration config) {
        this.config = config;
        url = DiscordTerminal.URL;
        prefix = DiscordTerminal.PREFIX;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException {
        if(!config.getBoolean("events.join"))return;
        DiscordWebhook webhook = new DiscordWebhook(url);
        Player player = event.getPlayer();
        webhook.setContent(prefix + boldPlayer(player) + " joined The Server!");
        webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString());
        webhook.setUsername(player.getName());
        webhook.execute();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) throws IOException {
        if(!config.getBoolean("events.leave"))return;
        DiscordWebhook webhook = new DiscordWebhook(url);
        Player player = event.getPlayer();
        webhook.setContent(prefix + boldPlayer(player) + " left The Server!");
        webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString());
        webhook.setUsername(player.getName());
        webhook.execute();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) throws IOException {
        if(!config.getBoolean("events.chat"))return;
        DiscordWebhook webhook = new DiscordWebhook(url);
        Player player = event.getPlayer();
        String message = event.getMessage();
        webhook.setContent(prefix + boldPlayer(player) + ": " + message);
        webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString());
        webhook.setUsername(player.getName());
        webhook.execute();
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) throws IOException {
        if(!config.getBoolean("events.command"))return;
        DiscordWebhook webhook = new DiscordWebhook(url);
        Player player = event.getPlayer();
        String command = event.getMessage();
        webhook.setContent(prefix + boldPlayer(player) + " executed `" + command + "` command!");
        webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString());
        webhook.setUsername(player.getName());
        webhook.execute();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) throws IOException {
        if(!config.getBoolean("events.death"))return;
        DiscordWebhook webhook = new DiscordWebhook(url);
        Player player = event.getEntity();
        webhook.setContent(prefix + boldPlayer(player) + " died!");
        webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString());
        webhook.setUsername(player.getName());
        webhook.execute();
    }

    private String boldPlayer(Player player){
        return "***" + player.getName() + "***";
    }

}
