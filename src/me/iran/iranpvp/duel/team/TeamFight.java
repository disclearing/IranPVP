package me.iran.iranpvp.duel.team;

import java.util.ArrayList;
import java.util.List;

import me.iran.iranpvp.teams.Team;

public class TeamFight {

	Team team1;
	Team team2;
	
	List<String> aliveTeam1;
	List<String> aliveTeam2;
	
	public TeamFight(Team team1, Team team2) {
		aliveTeam1 = new ArrayList<>();
		aliveTeam2 = new ArrayList<>();
		
		this.team1 = team1;
		this.team2 = team2;
	}
	
	public Team getTeam1() {
		return this.team1;
	}
	
	public Team getTeam2() {
		return this.team2;
	}
	
	public void setTeam1(Team team1) {
		this.team1 = team1;
	}
	
	public void setTeam2(Team team2) {
		this.team2 = team2;
	}
	
	public void removePlayerTeam1(String name) {
		aliveTeam1.remove(name);
	}
	
	public void removePlayerTeam2(String name) {
		aliveTeam2.remove(name);
	}
	
	public void setAliveTeam1(List<String> list) {
		this.aliveTeam1 = list;
	}
	
	public void setAliveTeam2(List<String> list) {
		this.aliveTeam2 = list;
	}
	
	public List<String> getAliveTeam1() {
		return this.aliveTeam1;
	}
	
	public List<String> getAliveTeam2() {
		return this.aliveTeam2;
	}
	
	public boolean isAlive(String name) {
		if(aliveTeam1.contains(name) || aliveTeam2.contains(name)) {
			return true;
		}
		return false;
	}
}
