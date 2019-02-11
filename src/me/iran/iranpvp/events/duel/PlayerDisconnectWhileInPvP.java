package me.iran.iranpvp.events.duel;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.duel.Duel;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.utils.Queue;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnectWhileInPvP implements Listener {

	IranPvP plugin;
	
	public PlayerDisconnectWhileInPvP (IranPvP plugin) {
		this.plugin = plugin;
	}

	Queue queue = new Queue();
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		player.setAllowFlight(false);
		
		if(queue.isInUnranked(player)) {
			queue.leaveUnranked(player);
		}
		
		if(queue.isInRanked(player)) {
			queue.leaveRanked(player);
		}
		
		if(DeathInventory.getTimer().containsKey(player.getName())) {
			DeathInventory.getTimer().remove(player.getName());
		}
		
		if(DeathInventory.getArmor().containsKey(player.getName())) {
			DeathInventory.getArmor().remove(player.getName());
		}
		
		if(DeathInventory.getInv().containsKey(player.getName())) {
			DeathInventory.getInv().remove(player.getName());
		}
		
		if(DeathInventory.getPots().containsKey(player.getName())) {
			DeathInventory.getPots().remove(player.getName());
		}
		
		if(ArenaManager.getManager().isSpectator(player)) {
			Arena arena = ArenaManager.getManager().getArenaBySpectator(player);
			
			arena.removeSpec(player.getName());
		}
		
		if(!TeamManager.getManager().isInTeam(player)) {
			if(DuelManager.getManager().isInDuel(player)) {
				final Duel duel = DuelManager.getManager().getDuelByPlayer(player);
				
				if(duel.isRanked()) {

					if (duel.getPlayer1().equals(player)) {
					
						DuelManager.getManager().endRankedDuel(duel.getPlayer2());
					
					} else if (duel.getPlayer2().equals(player)) {
						
						DuelManager.getManager().endRankedDuel(duel.getPlayer1());
					
					}
				} else {
					
					if (duel.getPlayer1().equals(player)) {
							
						DuelManager.getManager().endDuel(duel.getPlayer2());
					
					} else if (duel.getPlayer2().equals(player)) {
						
						DuelManager.getManager().endDuel(duel.getPlayer1());

					}
				}
				
			}
		}
	}
	
}
