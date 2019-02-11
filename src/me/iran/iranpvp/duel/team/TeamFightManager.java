package me.iran.iranpvp.duel.team;

import java.util.ArrayList;

import me.iran.iranpvp.teams.Team;

import org.bukkit.entity.Player;

public class TeamFightManager {

	public static ArrayList<TeamFight> fights = new ArrayList<>();
	
	
	private static TeamFightManager fm;

	private TeamFightManager() {}

	public static TeamFightManager getManager() {
		if (fm == null)
			fm = new TeamFightManager();

		return fm;
	}
	
	public void createTeamFight(Team team1, Team team2) {
		TeamFight fight = new TeamFight(team1, team2);
		
		for(String p : team1.getTeamMembers()) {
			fight.getAliveTeam1().add(p);
		}
		
		for(String p : team2.getTeamMembers()) {
			fight.getAliveTeam2().add(p);
		}
		
		fights.add(fight);
	}
	
	public void deleteTeamFight(Player player) {
		TeamFight fight = getFightByPlayer(player);
		
		if(fights.contains(fight)) {
			fights.remove(fight);
		}
	}
	
	public boolean inTeamFight(Team team) {
		for(TeamFight fight : fights) {
			if(fight.getTeam1() == team || fight.getTeam2() == team) {
				return true;
			}
		}
		
		return false;
	}
	
	public Team getPlayerTeam(Player player) {
		for(TeamFight fight : fights) {
			if(fight.getTeam1().getTeamMembers().contains(player.getName())) {
				return fight.getTeam1();
				
			} else if (fight.getTeam2().getTeamMembers().contains(player.getName())) {
				return fight.getTeam2();
			} 
		}
		
		return null;
	}
	
	public TeamFight getFightByPlayer(Player player) {
		for(TeamFight fight : fights) {
			if(fight.getTeam1().getTeamMembers().contains(player.getName()) || fight.getTeam2().getTeamMembers().contains(player.getName())) {
				return fight;
			}
		}
		
		return null;
	}
	
	public TeamFight getFightByTeam(Team team) {
		for(TeamFight fight : fights) {
			if(fight.getTeam1() == team || fight.getTeam2() == team) {
				return fight;
			}
		}
		return null;
	}
	
	public ArrayList<TeamFight> getAllTeamFights() {
		return fights;
	}
}
