package me.iran.iranpvp.kit.bard;

public class BardClass {

	private int energy;
	private String player;
	private final int MAX_ENERGY = 200;
	
	public BardClass(String player) {
		energy = 0;
		this.player = player;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public int getEnerg() {
		return energy;
	}
	
	public int getMax() {
		return MAX_ENERGY;
	}
}
