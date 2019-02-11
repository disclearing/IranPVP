package me.iran.iranpvp.events.duel;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.duel.Duel;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.ffa.FFACommand;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class PlayerDeathWhileInPvP implements Listener {

	IranPvP plugin;
	
	public PlayerDeathWhileInPvP (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		event.setDeathMessage(null);
		
		if (event.getEntity().getKiller() instanceof Player) {
			
			final Player killer = event.getEntity().getKiller();
			
			if (!TeamManager.getManager().isInTeam(player) && !TeamManager.getManager().isInTeam(killer)) {
				
				if (DuelManager.getManager().isInDuel(player) && DuelManager.getManager().isInDuel(killer)) {
					
					Duel duel = DuelManager.getManager().getDuelByPlayer(player);
					
					if(duel.isRanked()) {
						
						DuelManager.getManager().endRankedDuel(killer);
						
					} else {
						
						DuelManager.getManager().endDuel(killer);
						
					}
				}
			}

		} else if(!(event.getEntity().getKiller() instanceof Player)) {
			
			if (!TeamManager.getManager().isInTeam(player)) {
				
				if (DuelManager.getManager().isInDuel(player)) {
					
					Duel duel = DuelManager.getManager().getDuelByPlayer(player);
					
					final Player player1 = duel.getPlayer1();
					final Player player2 = duel.getPlayer2();
					
					if(duel.isRanked()) {
						
						if(player.equals(player1)) {
									DuelManager.getManager().endRankedDuel(player2);
						} else if(player.equals(player2)) {
									DuelManager.getManager().endRankedDuel(player1);
						}
						
					} else {
						
						if(player.equals(player1)) {
							
							DuelManager.getManager().endDuel(player2);
						
						} else if(player.equals(player2)) {
							
							DuelManager.getManager().endDuel(player1);
						
						}
					}
				}
			}
		}

	}

	@EventHandler
	public void onTp(PlayerTeleportEvent event) {
		
		Player player = event.getPlayer();
		
		if(event.getCause() == TeleportCause.ENDER_PEARL) {
			if(TeamManager.getManager().isInTeam(player)) {
				Team team = TeamManager.getManager().getTeamByPlayer(player);
				
				if(!TeamDuelManager.getManager().isInDuel(team)) {
					event.setCancelled(true);
				}
			} else {
				
				if(!DuelManager.getManager().isInDuel(player) && !FFACommand.fighters.contains(player.getName())) {
					event.setCancelled(true);
				}
				
			}
		}
	}
}
