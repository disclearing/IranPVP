package me.iran.iranpvp.duel.team;

import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.type.GameType;

public class TeamDuel {

	Team Team1;
	Team Team2;
	Team winner;
	GameType game;
	
	boolean kothStatus;

	public TeamDuel(Team Team1, Team Team2, GameType game) {
		this.Team1 = Team1;
		this.Team2 = Team2;
		this.game = game;
		winner = null;
		kothStatus = false;
	}

	public Team getTeam1() {
		return Team1;
	}

	public Team getTeam2() {
		return Team2;
	}

	public GameType getGameType() {
		return this.game;
	}
	
	public boolean getKothStatus() {
		return this.kothStatus;
	}

	public void setTeam1(Team Team1) {
		this.Team1 = Team1;
	}

	public void setKothStatus(boolean kothStatus) {
		this.kothStatus = kothStatus;
	}

	public void setTeam2(Team Team2) {
		this.Team2 = Team2;
	}

	public void setWinner(Team winner) {
		this.winner = winner;
	}

	public Team getWinner() {
		return this.winner;
	}
	
	public void setGameType(GameType game) {
		this.game = game;
	}

}
