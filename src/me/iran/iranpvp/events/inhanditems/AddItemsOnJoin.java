package me.iran.iranpvp.events.inhanditems;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.utils.OnJoinItems;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AddItemsOnJoin implements Listener {

	IranPvP plugin;
	
	public AddItemsOnJoin (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	OnJoinItems items = new OnJoinItems();
	
	TeleportSpawn spawn = new TeleportSpawn();
	
	@EventHandler
	public void onPracticeJoin(PlayerJoinEvent event) {	
		Player player = event.getPlayer();

		player.setGameMode(GameMode.SURVIVAL);
	   
		player.setAllowFlight(false);		
		
		spawn.teleportSpawn(player);
	
    }
	
}
