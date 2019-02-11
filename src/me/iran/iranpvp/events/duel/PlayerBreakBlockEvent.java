package me.iran.iranpvp.events.duel;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.duel.Duel;
import me.iran.iranpvp.duel.DuelManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerBreakBlockEvent implements Listener {

	IranPvP plugin;
	
	public PlayerBreakBlockEvent(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		
		Duel duel = DuelManager.getManager().getDuelByPlayer(player);
		
		if(duel == null) {
			event.setCancelled(true);
			return;
		}
		
		if(!duel.getBlocks().contains(event.getBlock().getLocation())) {
			
			if(!player.isOp()) {
				event.setCancelled(true);
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		Duel duel = DuelManager.getManager().getDuelByPlayer(player);
		
		if(duel != null) {
			duel.getBlocks().add(event.getBlock().getLocation());
		} else if(duel == null && !player.isOp()) {
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {

		if(event.getItemDrop().getItemStack().getType() == Material.GLASS_BOTTLE) {
			event.getItemDrop().remove();
		}
	}
	
}
