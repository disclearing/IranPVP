package me.iran.iranpvp.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class Arena {

	Location loc1;
	Location loc2;
	
	int x1;
	int y1;
	int z1;
	
	int x2;
	int y2;
	int z2;
	
	float pitch1;
	float yaw1;
	
	float pitch2;
	float yaw2;
	
	int loc1x;
	int loc1y;
	int loc1z;
	
	int loc2x;
	int loc2y;
	int loc2z;
	
	private String name;
	private String creator;
	
	private List<String> fighters;
	private int timer;
	private int time;
	private ArrayList<String> cappers;
	private boolean active; 
	private boolean teamArena;
	
	private ArrayList<String> spec;
	
	private String world;
	
	public Arena (Location loc1, Location loc2, int timer, String name, String world) {
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.timer = timer;
		this.time = timer;
		this.name = name;
		this.world = world;
		active = false;
		teamArena = true;
		spec = new ArrayList<String>();
		fighters = new ArrayList<String>();
		cappers = new ArrayList<String>();
		this.creator = "Anonymous";
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public ArrayList<String> getSpec() {
		return spec;
	}
	
	public void setSpec(ArrayList<String> spec) {
		this.spec = spec;
	}
	
	public void addSpec(String spec) {
		this.spec.add(spec);
	}
	
	public void removeSpec(String spec){
		this.spec.remove(spec);
	}
	
	public boolean isSpec(String player) {
		if(spec.contains(player)) {
			return true;
		}
		
		return false;
	}
	
	//Team 1
	
	public void setXTeam1(int x) {
		this.x1 = x;
	}
	
	public void setYTeam1(int y) {
		this.y1 = y;
	}
	
	public void setZTeam1(int z) {
		this.z1 = z;
	}
	
	public void setPitchTeam1(float pitch) {
		this.pitch1 = pitch;
	}
	
	public void setYawTeam1(float yaw) {
		this.yaw1 = yaw;
	}
	
	public float getPitchTeam1() { 
		return this.pitch1;
	}
	
	public float getYawTeam1() {
		return this.yaw1;
	}
	
	public int getXTeam1() {
		return x1;
	}
	
	public int getYTeam1() {
		return y1;
	}
	
	public int getZTeam1() {
		return z1;
	}
	
	//Team 2
	
	public void setXTeam2(int x) {
		this.x2 = x;
	}
	
	public void setYTeam2(int y) {
		this.y2 = y;
	}
	
	public void setZTeam2(int z) {
		this.z2 = z;
	}
	
	public void setPitchTeam2(float pitch) {
		this.pitch2 = pitch;
	}

	public void setYawTeam2(float yaw) {
		this.yaw2 = yaw;
	}

	public float getPitchTeam2() {
		return this.pitch2;
	}

	public float getYawTeam2() {
		return this.yaw2;
	}

	public int getXTeam2() {
		return x2;
	}
	
	public int getYTeam2() {
		return y2;
	}
	
	public int getZTeam2() {
		return z2;
	}
	
	//KOTH
	
	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}
	
	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}
	
	public Location getLoc1() {
		return this.loc1;
	}
	
	public Location getLoc2() {
		return this.loc2;
	}
	
	public void setLoc1X(int x) {
		this.loc1x = x;
	}
	
	public void setLoc1Y(int y) {
		this.loc1y = y;
	}
	
	public void setLoc1Z(int z) {
		this.loc1z = z;
	}
	
	public void setLoc2X(int x) {
		this.loc2x = x;
	}
	
	public void setLoc2Y(int y) {
		this.loc2y = y;
	}
	
	public void setLoc2Z(int z) {
		this.loc2z = z;
	}
	
	//KOTH min
	public int getMaxX() {
		return Math.max(loc1.getBlockX(), loc2.getBlockX());
	}
	
	public int getMaxZ() {
		return Math.max(loc1.getBlockZ(), loc2.getBlockZ());
	}
	
	public int getMaxY() {
		return Math.max(loc1.getBlockY(), loc2.getBlockY());
	}
	
	public int getMinX() {
		return Math.min(loc1.getBlockX(), loc2.getBlockX());
	}
	
	public int getMinZ() {
		return Math.min(loc1.getBlockZ(), loc2.getBlockZ());
	}
	
	public int getMinY() {
		return Math.min(loc1.getBlockY(), loc2.getBlockY());
	}
	
	//Name
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	//Timer
	
	public int getTimer() {
		return timer;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public void setArenaTimer(int time) {
		this.time = time;
	}
	
	public int getArenaTimer() {
		return this.time;
	}
	
	//Fighters
	public List<String> getFighters() {
		return fighters;
	}
	
	public void setFightersList(List<String> fighters) {
		this.fighters = fighters;
	}
	
	public void addFighter(String name) {
		fighters.add(name);
	}
	
	public void removeFighter(String name) {
		fighters.remove(name);
	}
	
	//Cappers
	public ArrayList<String> getCappers() {
		return cappers;
	}
	
	public void setCapperList(ArrayList<String> cappers) {
		this.cappers = cappers;
	}
	
	public void setCapper(String capper) {
		cappers.add(0, capper);
	}
	
	public String getCapper() {
		return cappers.get(0);
	}
	
	//Active
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive() {
		active = true;
	}
	
	public void setInactive() {
		active = false;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}
	
	public boolean isTeamArena() {
		return teamArena;
	}
	
	public void setTeamArena(boolean teamArena) {
		this.teamArena = teamArena;
	}
}
