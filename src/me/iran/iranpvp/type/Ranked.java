package me.iran.iranpvp.type;

import org.bukkit.entity.Player;

public class Ranked {
	
	int elo;
	Player player;
	GameType gametype;
	
	public Ranked(int elo, Player player, GameType gametype) {
		this.elo = elo;
		this.player = player;
		this.gametype = gametype;
	}
	
	public int getElo() {
		return this.elo;
	}
	
	public void setElo(int elo) {
		this.elo = elo;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setGameType(GameType game) {
		this.gametype = game;
	}
	
	public GameType getGameType() {
		return this.gametype;
	}
}
