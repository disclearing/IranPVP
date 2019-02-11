package me.iran.iranpvp.events.teamfight;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.duel.team.TeamFight;
import me.iran.iranpvp.duel.team.TeamFightManager;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.utils.OnJoinItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisconnectWhileInTeam implements Listener {

	IranPvP plugin;
	
	public DisconnectWhileInTeam (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	OnJoinItems items = new OnJoinItems();
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(TeamManager.getManager().isInTeam(player)) {
			Team team = TeamManager.getManager().getTeamByPlayer(player);
			
			if(TeamDuelManager.getManager().isInDuel(team)) {
				if(team.getTeamMembers().size() > 1) {
					
					Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
					
					Player newLeader = Bukkit.getPlayer(team.getTeamMembers().get(1));
					
/*					TeamManager.getManager().makeLeaderInFight(player, newLeader);
					team.removeMember(player.getName());*/
					
					TeamManager.getManager().makeLeaderInFight(player, newLeader);
					team.removeMember(player.getName());
					arena.getFighters().remove(player.getName());
					
					if(arena.isActive() && arena.getCappers().contains(player.getName())) {
						arena.getCappers().remove(player.getName());
						arena.setTimer(arena.getArenaTimer());
						
					} else if(!arena.isActive()) {
						
						if(TeamDuelManager.getManager().isInDuel(team)) {
							
							TeamFight fight = TeamFightManager.getManager().getFightByPlayer(player);
							
							if(fight.getTeam1() == team) {
							
								fight.getAliveTeam1().remove(player.getName());
							
								if(fight.getAliveTeam1().size() < 1) {
									TeamDuelManager.getManager().endDuel(Bukkit.getPlayer(fight.getTeam2().getTeamLeader()));
								}
								
							} else if(fight.getTeam2() == team) {
								
								fight.getAliveTeam2().remove(player.getName());
							
								if(fight.getAliveTeam2().size() < 1) {
									TeamDuelManager.getManager().endDuel(Bukkit.getPlayer(fight.getTeam1().getTeamLeader()));
								}
								
							}
						}
					}
					
					
				} else if(team.getTeamMembers().size() < 2) {
					Team team1 = TeamDuelManager.getManager().getDuelByPlayer(player).getTeam1();
					Team team2 = TeamDuelManager.getManager().getDuelByPlayer(player).getTeam2();
					
					if(team1 == team) {
						TeamDuelManager.getManager().endDuel(Bukkit.getPlayer(team2.getTeamLeader()));
						TeamManager.getManager().disbandTeam(player);
					} else if(team2 == team) {
						TeamDuelManager.getManager().endDuel(Bukkit.getPlayer(team1.getTeamLeader()));
						TeamManager.getManager().disbandTeam(player);
					}
				}
			} else if(!TeamDuelManager.getManager().isInDuel(team)) {
				if(team.getTeamMembers().size() > 1) {
					
					Player newLeader = Bukkit.getPlayer(team.getTeamMembers().get(1));
					
					TeamManager.getManager().makeLeader(player, newLeader);
					team.removeMember(player.getName());
					
					items.teamLeaderItems(newLeader);
					
				} else if(team.getTeamMembers().size() < 2) {
					
					TeamManager.getManager().disbandTeam(player);
				}
			}
			
		}
		
		
	}
	
}
