package me.iran.iranpvp.duel;

import me.iran.iranpvp.type.GameType;

public class InvitePlayer {

	String player1;
	String player2;
	
	GameType game;
	
	public InvitePlayer(String player1, String player2, GameType game) {
		this.player1= player1;
		this.player2 = player2;
		this.game = game;
	}
	
	public void setPlayer1(String player1) {
		this.player1= player1;
	}
	
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
	
	public void setGameType(GameType game) {
		this.game = game;
	}
	
	public String getPlayer1() {
		return this.player1;
	}
	
	public String getPlayer2() {
		return this.player2;
	}
	
	public GameType getGameType() {
		return this.game;
	}
	
}
