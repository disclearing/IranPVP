package me.iran.iranpvp.cmd.pvp.specevent;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpectatorEvents implements Listener {

	IranPvP plugin;
	
	public SpectatorEvents(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		
		if(ArenaManager.getManager().isSpectator(player)) {
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			
			if(ArenaManager.getManager().isSpectator(player)) {
				event.setCancelled(true);
			}
		
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		
		Player player = event.getPlayer();
		
		if(ArenaManager.getManager().isSpectator(player)) {
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void worldChange(PlayerChangedWorldEvent event) {
		
		Player player = event.getPlayer();
		
		if(ArenaManager.getManager().isSpectator(player)) {
			player.setGameMode(GameMode.CREATIVE);
			player.setAllowFlight(true);
			player.setHealth(20.0);
			player.setFoodLevel(20);
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		
		if(ArenaManager.getManager().isSpectator(player)) {
			Arena arena = ArenaManager.getManager().getArenaBySpectator(player);
			arena.removeSpec(player.getName());
			
			player.setGameMode(GameMode.SURVIVAL);
			player.setAllowFlight(false);
			player.setHealth(20.0);
			player.setFoodLevel(20);
		}
	}
	
}
