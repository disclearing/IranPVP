package me.iran.iranpvp.events.duel;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {

	IranPvP plugin;
	
	public RespawnEvent (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	TeleportSpawn spawn = new TeleportSpawn();
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
	
		Bukkit.getScheduler().scheduleSyncDelayedTask(IranPvP.plugin, new Runnable() {
			
			public void run() {
				spawn.teleportSpawn(player);
				
			}
			
		}, 5);
	
	}
	
}
