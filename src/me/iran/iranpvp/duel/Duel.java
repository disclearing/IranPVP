package me.iran.iranpvp.duel;

import java.util.ArrayList;

import me.iran.iranpvp.type.GameType;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Duel {

	private Player player1;
	private Player player2;
	private Player winner;
	private GameType game;
	private boolean ranked;
	private ArrayList<Location> blocks;
	
	private boolean kothStatus;

	private int timer = 0;
	
	public Duel(Player player1, Player player2, GameType game) {
		this.player1 = player1;
		this.player2 = player2;
		this.game = game;
		winner = null;
		kothStatus = false;
		ranked = false;
		
		blocks = new ArrayList<Location>();
	}

	public void setRanked(boolean ranked) {
		this.ranked = ranked;
	}
	
	public ArrayList<Location> getBlocks() {
		return blocks;
	}
	
	public boolean isRanked() {
		if(ranked) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getTimer() {
		return timer;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public GameType getGameType() {
		return this.game;
	}
	
	public boolean getKothStatus() {
		return this.kothStatus;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public void setKothStatus(boolean kothStatus) {
		this.kothStatus = kothStatus;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Player getWinner() {
		return this.winner;
	}
	
	public void setGameType(GameType game) {
		this.game = game;
	}

}
