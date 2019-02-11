package me.iran.iranpvp.duel.team;

import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.type.GameType;

public class TeamInvite {

	private Team team1;
	private Team team2;
	private String type;
	private GameType gametype;
	
	public TeamInvite(Team team1, Team team2, GameType gametype, String type) {
		this.team1 = team1;
		this.team2 = team2;
		this.type = type;
		this.gametype = gametype;
	}
	
	public void setTeam1(Team team1) {
		this.team1 = team1;
	}
	
	public void setTeam2(Team team2) {
		this.team2 = team2;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setGameType(GameType gametype) {
		this.gametype = gametype;
	}
	
	public Team getTeam1() {
		return this.team1;
	}

	public Team getTeam2() {
		return this.team2;
	}
	
	public String getType() {
		return this.type;
	}
	
	public GameType getGameType() {
		return this.gametype;
	}
}
