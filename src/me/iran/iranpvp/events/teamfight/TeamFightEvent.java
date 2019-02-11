package me.iran.iranpvp.events.teamfight;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.duel.team.TeamFight;
import me.iran.iranpvp.duel.team.TeamFightManager;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.utils.TeleportSpawn;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TeamFightEvent implements Listener {

	IranPvP plugin;
	
	public TeamFightEvent (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	TeleportSpawn spawn = new TeleportSpawn();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
	
		event.getDrops().clear();
		
		if(event.getEntity().getKiller() instanceof Player) {
			final Player killer = (Player) event.getEntity().getKiller();
		
			//Player is in a team
		if(TeamManager.getManager().isInTeam(player)) {
		 
			Team team = TeamManager.getManager().getTeamByPlayer(player);
			
			//Team is in Duel
			if(TeamDuelManager.getManager().isInDuel(team)) {
				
				Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
			
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(arena.getFighters().contains(p.getName())) {
						p.sendMessage(ChatColor.RED + killer.getName() + ChatColor.GOLD + " has killed " + ChatColor.RED + player.getName());
					}
				}
				
				//Arena is not active
				if(!arena.isActive()) {
					
					TeamFight fight = TeamFightManager.getManager().getFightByPlayer(player);
				
					if(!TeamFightManager.getManager().getAllTeamFights().contains(fight)) {
						return;
					}
					
					if(fight.isAlive(player.getName())) {
				
						
						if(fight.getTeam1().getTeamMembers().contains(player.getName())) {
						
							fight.getAliveTeam1().remove(player.getName());
						
						
							if(fight.getAliveTeam1().size() <= 0) {
								
								for(Player p : Bukkit.getOnlinePlayers()) {
									if(arena.getFighters().contains(p.getName())) {
										p.sendMessage(ChatColor.DARK_AQUA + fight.getTeam2().getTeamName() + ChatColor.GREEN + " has won the Team Fight");
									}
								}
								

										TeamDuelManager.getManager().endDuel(killer);
								
										TeamFightManager.getManager().deleteTeamFight(killer);
									
								
							}
						}
						
						if(fight.getTeam2().getTeamMembers().contains(player.getName())) {
						
							fight.getAliveTeam2().remove(player.getName());
							
						
							if(fight.getAliveTeam2().size() <= 0) {
								
								for(Player p : Bukkit.getOnlinePlayers()) {
									if(arena.getFighters().contains(p.getName())) {
										p.sendMessage(ChatColor.DARK_AQUA + fight.getTeam1().getTeamName() + ChatColor.GREEN + " has won the Team Fight");
									}
								}
								
										TeamDuelManager.getManager().endDuel(killer);
								
										TeamFightManager.getManager().deleteTeamFight(killer);
								
					
								
								}

							}

						}

					}

				}

			}
		} else if(!(event.getEntity().getKiller() instanceof Player)) {
			
			if(TeamManager.getManager().isInTeam(player)) {
				 
				Team team = TeamManager.getManager().getTeamByPlayer(player);
				
				//Team is in Duel
				if(TeamDuelManager.getManager().isInDuel(team)) {
					
					Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
				
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(arena.getFighters().contains(p.getName())) {
							p.sendMessage(ChatColor.RED + player.getName() + ChatColor.GOLD + " died");
						}
					}
					
					//Arena is not active
					if(!arena.isActive()) {
						
						final TeamFight fight = TeamFightManager.getManager().getFightByPlayer(player);
					
						if(!TeamFightManager.getManager().getAllTeamFights().contains(fight)) {
							return;
						}
						
						if(fight.isAlive(player.getName())) {
					
							
							if(fight.getTeam1().getTeamMembers().contains(player.getName())) {
							
								fight.getAliveTeam1().remove(player.getName());
							
							
								if(fight.getAliveTeam1().size() <= 0) {
									
									for(Player p : Bukkit.getOnlinePlayers()) {
										if(arena.getFighters().contains(p.getName())) {
											p.sendMessage(ChatColor.DARK_AQUA + fight.getTeam2().getTeamName() + ChatColor.GREEN + " has won the Team Fight");
										}
									}
									
									Bukkit.getScheduler().scheduleAsyncDelayedTask(IranPvP.plugin, new Runnable() {

										@Override
										public void run() {
											
											TeamDuelManager.getManager().endDuel(Bukkit.getPlayer(fight.getTeam2().getTeamLeader()));
											
											TeamFightManager.getManager().deleteTeamFight(Bukkit.getPlayer(fight.getTeam2().getTeamLeader()));
										}
										
									}, 100);
									
							
								
								}
							}
							
							if(fight.getTeam2().getTeamMembers().contains(player.getName())) {
							
								fight.getAliveTeam2().remove(player.getName());
								
							
								if(fight.getAliveTeam2().size() <= 0) {
									
									for(Player p : Bukkit.getOnlinePlayers()) {
										if(arena.getFighters().contains(p.getName())) {
											p.sendMessage(ChatColor.DARK_AQUA + fight.getTeam1().getTeamName() + ChatColor.GREEN + " has won the Team Fight");
										}
									}
									
									Bukkit.getScheduler().scheduleAsyncDelayedTask(IranPvP.plugin, new Runnable() {

										@Override
										public void run() {
											TeamDuelManager.getManager().endDuel(Bukkit.getPlayer(fight.getTeam1().getTeamLeader()));
											TeamFightManager.getManager().deleteTeamFight(Bukkit.getPlayer(fight.getTeam1().getTeamLeader()));
										}
										
									}, 100);
									
									
									}

								}

							}

						}

				}

			}
		}

	}
}