package me.iran.iranpvp.events.teamfight;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TeamDamageTeam implements Listener {
	IranPvP plugin;
	
	public TeamDamageTeam(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event) {
		
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player hit = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
			
			if(ArenaManager.getManager().isSpectator(damager)) {
				event.setCancelled(true);
			}
			
			Team hitTeam = TeamManager.getManager().getTeamByPlayer(hit);
			Team damTeam = TeamManager.getManager().getTeamByPlayer(damager);
			
			if(TeamManager.getManager().getAllTeams().contains(hitTeam) && TeamManager.getManager().getAllTeams().contains(damTeam)) {
				if(hitTeam.getTeamName().equalsIgnoreCase(damTeam.getTeamName())) {
					event.setCancelled(true);
				}
			}
		}
	}
}
