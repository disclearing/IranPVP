package me.iran.iranpvp.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.runnable.Run;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ArenaManager {
	
	File file = null;
	
	public static ArrayList<Arena> arenas = new ArrayList<Arena>();
	public static ArrayList<Arena> available = new ArrayList<Arena>();
	public static ArrayList<Arena> pAvailable = new ArrayList<Arena>();
	
	private static ArenaManager am;

	private ArenaManager() {}

	public static ArenaManager getManager() {
		if (am == null)
			am = new ArenaManager();

		return am;
	}
	
	TeleportSpawn spawn = new TeleportSpawn();
	
	public void loadArena() {
		
		file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
		
		if(!file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
			System.out.println("[IranPvP] Could not find any Arenas, Creating new file");
			
			file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
			
			arenaConfig.createSection("arena");
			
			try {
				arenaConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("[IranPvP] Created arena file, now you can create arenas");
		} else {
			
			file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
			
			for(String a : arenaConfig.getConfigurationSection("arena").getKeys(false)) {
				int teamx1 = arenaConfig.getInt("arena." + a + ".team1.x");
				int teamy1 = arenaConfig.getInt("arena." + a + ".team1.y");
				int teamz1 = arenaConfig.getInt("arena." + a + ".team1.z");
				float team1pitch = (float) arenaConfig.getDouble("arena." + a + ".team1.pitch");
				float team1yaw = (float) arenaConfig.getDouble("arena." + a + ".team1.yaw");
					
				int teamx2 = arenaConfig.getInt("arena." + a + ".team2.x");
				int teamy2 = arenaConfig.getInt("arena." + a + ".team2.y");
				int teamz2 = arenaConfig.getInt("arena." + a + ".team2.z");
				float teampitch2 = (float) arenaConfig.getDouble("arena." + a + ".team2.pitch");
				float teamyaw2 = (float) arenaConfig.getDouble("arena." + a + ".team2.yaw");
					
				int timer = arenaConfig.getInt("arena." + a + ".timer");
				int time = arenaConfig.getInt("arena." + a + ".time");
				
				int loc1x = arenaConfig.getInt("arena." + a + ".loc1.x");
				int loc1y = arenaConfig.getInt("arena." + a + ".loc1.y");
				int loc1z = arenaConfig.getInt("arena." + a + ".loc1.z");
				
				int loc2x = arenaConfig.getInt("arena." + a + ".loc2.x");
				int loc2y = arenaConfig.getInt("arena." + a + ".loc2.y");
				int loc2z = arenaConfig.getInt("arena." + a + ".loc2.z");
				
				String world = arenaConfig.getString("arena." + a + ".world");
				
				String name = arenaConfig.getString("arena." + a + ".name");
				
				String creator = arenaConfig.getString("arena." + a + ".creator");
				
				/////NEW///////
				boolean teamArena = arenaConfig.getBoolean("arena." + a + ".teamarena");
				
				/////NEW///////
				
				
				Location loc1 = new Location(Bukkit.getWorld(world), loc1x, loc1y, loc1z);
				Location loc2 = new Location(Bukkit.getWorld(world), loc2x, loc2y, loc2z);
				
				Location team1 = new Location(Bukkit.getWorld(world), teamx1, teamy1, teamz1);
				Location team2 = new Location(Bukkit.getWorld(world), teamx2, teamy2, teamz2);
				
				team1.setPitch(team1pitch);
				team1.setYaw(team1yaw);
				
				team2.setPitch(teampitch2);
				team2.setYaw(teamyaw2);
				
				Arena arena = new Arena(loc1, loc2, timer, name, world);
				arena.setName(name);
				arena.setLoc1(loc1);
				arena.setLoc2(loc2);
				
				arena.setXTeam1(teamx1);
				arena.setYTeam1(teamy1);
				arena.setZTeam1(teamz1);
				arena.setPitchTeam1(team1pitch);
				arena.setYawTeam1(team1yaw);
				
				arena.setXTeam2(teamx2);
				arena.setYTeam2(teamy2);
				arena.setZTeam2(teamz2);
				arena.setPitchTeam2(teampitch2);
				arena.setYawTeam2(teamyaw2);
				arena.setTeamArena(teamArena);
				
				arena.setCreator(creator);
				
				arena.setWorld(world);
				arena.setArenaTimer(time);;
				arena.setTimer(arena.getArenaTimer());
				
				arenas.add(arena);
				
			
			}
			
		
		}
		
		for(Arena a : arenas) {
			if(a.isTeamArena() == true) {
				available.add(a);
			} else if(a.isTeamArena() == false) {
				pAvailable.add(a);
			}
		}
		
		System.out.println("Team Fight Arenas: " + available.size());
		System.out.println("1v1 Arenas: " + pAvailable.size());
	}
	
	public void saveArena() {
		file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
		
		
		if(!file.exists()) {
			
			file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
			System.out.println("[IranPvP] Could not find any Arenas, Creating new file");
			
			file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
			
			arenaConfig.createSection("arena");
			
			try {
				arenaConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("[IranPvP] Created arena file, now you can create arenas");
		} else {
			
			file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
			new YamlConfiguration();
			YamlConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
			
			for(int i = 0; i < arenas.size(); i++) {
				
				String a = arenas.get(i).getName();
				
				Arena arena = getArenaByName(a);
				
				arenaConfig.set("arena." + a + ".name", a);
				arenaConfig.set("arena." + a + ".creator", arena.getCreator());
				
				arenaConfig.set("arena." + a + ".team1.x", arena.getXTeam1());
				arenaConfig.set("arena." + a + ".team1.y", arena.getYTeam1());
				arenaConfig.set("arena." + a + ".team1.z", arena.getZTeam1());
				arenaConfig.set("arena." + a + ".team1.pitch", arena.getPitchTeam1());
				arenaConfig.set("arena." + a + ".team1.yaw", arena.getYawTeam1());
				
				arenaConfig.set("arena." + a + ".team2.x", arena.getXTeam2());
				arenaConfig.set("arena." + a + ".team2.y", arena.getYTeam2());
				arenaConfig.set("arena." + a + ".team2.z", arena.getZTeam2());
				arenaConfig.set("arena." + a + ".team2.pitch", arena.getPitchTeam2());
				arenaConfig.set("arena." + a + ".team2.yaw", arena.getYawTeam2());
				
				arenaConfig.set("arena." + a + ".xmin", arena.getMinX());
				arenaConfig.set("arena." + a + ".ymin", arena.getMinY());
				arenaConfig.set("arena." + a + ".zmin", arena.getMinZ());
				
				arenaConfig.set("arena." + a + ".xmax", arena.getMaxX());
				arenaConfig.set("arena." + a + ".ymax", arena.getMaxY());
				arenaConfig.set("arena." + a + ".zmax", arena.getMaxZ());
				
				arenaConfig.set("arena." + a + ".loc1.x", arena.getLoc1().getBlockX());
				arenaConfig.set("arena." + a + ".loc1.y", arena.getLoc1().getBlockY());
				arenaConfig.set("arena." + a + ".loc1.z", arena.getLoc1().getBlockZ());
				
				arenaConfig.set("arena." + a + ".loc2.x", arena.getLoc2().getBlockX());
				arenaConfig.set("arena." + a + ".loc2.y", arena.getLoc2().getBlockY());
				arenaConfig.set("arena." + a + ".loc2.z", arena.getLoc2().getBlockZ());
				
				arenaConfig.set("arena." + a + ".world", arena.getWorld());
				arenaConfig.set("arena." + a + ".active", false);
				arenaConfig.set("arena." + a + ".teamarena", arena.isTeamArena());
				arenaConfig.set("arena." + a + ".timer", arena.getArenaTimer());
				arenaConfig.set("arena." + a + ".time", arena.getArenaTimer());
				arenaConfig.set("arena." + a + ".world", arena.getWorld());

			}
			
			try {
				arenaConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void deleteArena(String name, Player player) {
		
		file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
			new YamlConfiguration();
			YamlConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
				
				Arena arena = getArenaByName(name);
				
				if(!arenas.contains(arena)) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_REMOVE_FAIL").replace("%arena%", arena.getName())));
					return;
				}
				
				if(arena.isActive()) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_REMOVE_FAIL").replace("%arena%", arena.getName())));
					return;
				}
				arenaConfig.set("arena." + name, null);
				
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_REMOVE_SUCCESS").replace("%arena%", arena.getName())));
				
				arenas.remove(arena);
				available.remove(arena);
				
				
			
			try {
				arenaConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createArena(Location loc1, Location loc2, int timer, String name, Player player) {
		
		if(arenas.contains(getArenaByName(name))) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_CREATE_FAIL").replace("%arena%", name)));
			
			return;
		}
		
		Arena arena = new Arena (loc1, loc2, timer, name, player.getLocation().getWorld().getName());
		
		arenas.add(arena);
		available.add(arena);
		
		file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
		
		if(!file.exists()) {
			System.out.println("[IranPvP] Could not find any Arenas, Creating new file");
			
			file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
			
			arenaConfig.createSection("arena." + name + ".name");
			arenaConfig.createSection("arena." + name + ".creator");
			
			arenaConfig.createSection("arena." + name + ".team1.x");
			arenaConfig.createSection("arena." + name + ".team1.y");
			arenaConfig.createSection("arena." + name + ".team1.z");
			arenaConfig.createSection("arena." + name + ".team1.pitch");
			arenaConfig.createSection("arena." + name + ".team1.yaw");
				
			arenaConfig.createSection("arena." + name + ".team2.x");
			arenaConfig.createSection("arena." + name + ".team2.y");
			arenaConfig.createSection("arena." + name + ".team2.z");
			arenaConfig.createSection("arena." + name + ".team2.pitch");
			arenaConfig.createSection("arena." + name + ".team2.yaw");
				
			arenaConfig.createSection("arena." + name + ".xmin");
			arenaConfig.createSection("arena." + name + ".ymin");
			arenaConfig.createSection("arena." + name + ".zmin");
				
			arenaConfig.createSection("arena." + name + ".xmax");
			arenaConfig.createSection("arena." + name + ".ymax");
			arenaConfig.createSection("arena." + name + ".zmax");
				
			arenaConfig.createSection("arena." + name + ".timer");
			arenaConfig.createSection("arena." + name + ".time");
			
			arenaConfig.createSection("arena." + name + ".world");
			arenaConfig.createSection("arena." + name + ".active");
			
			arenaConfig.createSection("arena." + name + ".loc1.x");
			arenaConfig.createSection("arena." + name + ".loc1.y");
			arenaConfig.createSection("arena." + name + ".loc1.z");
			
			arenaConfig.createSection("arena." + name + ".loc2.x");
			arenaConfig.createSection("arena." + name + ".loc2.y");
			arenaConfig.createSection("arena." + name + ".loc2.z");
			arenaConfig.createSection("arena." + name + ".teamarena");
			
			arenaConfig.set("arena." + name + ".name", name);
			arenaConfig.set("arena." + name + ".creator", arena.getCreator());
			
			arenaConfig.set("arena." + name + ".team1.x", arena.getXTeam1());
			arenaConfig.set("arena." + name + ".team1.y", arena.getYTeam1());
			arenaConfig.set("arena." + name + ".team1.z", arena.getZTeam1());
			arenaConfig.set("arena." + name + ".team1.pitch", arena.getPitchTeam1());
			arenaConfig.set("arena." + name + ".team1.yaw", arena.getYawTeam1());
				
			arenaConfig.set("arena." + name + ".team2.x", arena.getXTeam2());
			arenaConfig.set("arena." + name + ".team2.y", arena.getYTeam2());
			arenaConfig.set("arena." + name + ".team2.z", arena.getZTeam2());
			arenaConfig.set("arena." + name + ".team2.pitch", arena.getPitchTeam2());
			arenaConfig.set("arena." + name + ".team2.yaw", arena.getYawTeam2());
				
			arenaConfig.set("arena." + name + ".xmin", arena.getMinX());
			arenaConfig.set("arena." + name + ".ymin", arena.getMinY());
			arenaConfig.set("arena." + name + ".zmin", arena.getMinZ());
				
			arenaConfig.set("arena." + name + ".xmax", arena.getMaxX());
			arenaConfig.set("arena." + name + ".ymax", arena.getMaxY());
			arenaConfig.set("arena." + name + ".zmax", arena.getMaxZ());
				
			arenaConfig.set("arena." + name + ".timer", timer);
			arenaConfig.set("arena." + name + ".time", timer);
			
			arenaConfig.set("arena." + name + ".loc1.x", arena.getLoc1().getBlockX());
			arenaConfig.set("arena." + name + ".loc1.y", arena.getLoc1().getBlockY());
			arenaConfig.set("arena." + name + ".loc1.z", arena.getLoc1().getBlockZ());
			
			arenaConfig.set("arena." + name + ".loc2.x", arena.getLoc2().getBlockX());
			arenaConfig.set("arena." + name + ".loc2.y", arena.getLoc2().getBlockY());
			arenaConfig.set("arena." + name + ".loc2.z", arena.getLoc2().getBlockZ());
			
			arenaConfig.set("arena." + name + ".world", arena.getWorld());
			arenaConfig.set("arena." + name + ".active", false);
			arenaConfig.set("arena." + name + ".teamarena", true);
			
			try {
				arenaConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_CREATE_SUCCESS").replace("%arena%", arena.getName())));
			
			
		} else {
			
			file = new File(IranPvP.plugin.getDataFolder(), "arena.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
			
			arenaConfig.createSection("arena." + name + ".name");
			arenaConfig.createSection("arena." + name + ".creator");
			
			arenaConfig.createSection("arena." + name + ".team1.x");
			arenaConfig.createSection("arena." + name + ".team1.y");
			arenaConfig.createSection("arena." + name + ".team1.z");
			arenaConfig.createSection("arena." + name + ".team1.pitch");
			arenaConfig.createSection("arena." + name + ".team1.yaw");
				
			arenaConfig.createSection("arena." + name + ".team2.x");
			arenaConfig.createSection("arena." + name + ".team2.y");
			arenaConfig.createSection("arena." + name + ".team2.z");
			arenaConfig.createSection("arena." + name + ".team2.pitch");
			arenaConfig.createSection("arena." + name + ".team2.yaw");
				
			arenaConfig.createSection("arena." + name + ".xmin");
			arenaConfig.createSection("arena." + name + ".ymin");
			arenaConfig.createSection("arena." + name + ".zmin");
				
			arenaConfig.createSection("arena." + name + ".xmax");
			arenaConfig.createSection("arena." + name + ".ymax");
			arenaConfig.createSection("arena." + name + ".zmax");
				
			arenaConfig.createSection("arena." + name + ".timer");
			arenaConfig.createSection("arena." + name + ".time");
			
			arenaConfig.createSection("arena." + name + ".loc1.x");
			arenaConfig.createSection("arena." + name + ".loc1.y");
			arenaConfig.createSection("arena." + name + ".loc1.z");
			
			arenaConfig.createSection("arena." + name + ".loc2.x");
			arenaConfig.createSection("arena." + name + ".loc2.y");
			arenaConfig.createSection("arena." + name + ".loc2.z");
			
			arenaConfig.createSection("arena." + name + ".world");
			arenaConfig.createSection("arena." + name + ".active");
			arenaConfig.createSection("arena." + name + ".teamarena");
			
			arenaConfig.set("arena." + name + ".name", name);
			arenaConfig.set("arena." + name + ".creator", arena.getCreator());
			
			arenaConfig.set("arena." + name + ".team1.x", arena.getXTeam1());
			arenaConfig.set("arena." + name + ".team1.y", arena.getYTeam1());
			arenaConfig.set("arena." + name + ".team1.z", arena.getZTeam1());
			arenaConfig.set("arena." + name + ".team1.pitch", arena.getPitchTeam1());
			arenaConfig.set("arena." + name + ".team1.yaw", arena.getYawTeam1());
				
			arenaConfig.set("arena." + name + ".team2.x", arena.getXTeam2());
			arenaConfig.set("arena." + name + ".team2.y", arena.getYTeam2());
			arenaConfig.set("arena." + name + ".team2.z", arena.getZTeam2());
			arenaConfig.set("arena." + name + ".team2.pitch", arena.getPitchTeam2());
			arenaConfig.set("arena." + name + ".team2.yaw", arena.getYawTeam2());
				
			arenaConfig.set("arena." + name + ".xmin", arena.getMinX());
			arenaConfig.set("arena." + name + ".ymin", arena.getMinY());
			arenaConfig.set("arena." + name + ".zmin", arena.getMinZ());
				
			arenaConfig.set("arena." + name + ".xmax", arena.getMaxX());
			arenaConfig.set("arena." + name + ".ymax", arena.getMaxY());
			arenaConfig.set("arena." + name + ".zmax", arena.getMaxZ());
				
			arenaConfig.set("arena." + name + ".timer", timer);
			arenaConfig.set("arena." + name + ".time", timer);
			
			arenaConfig.set("arena." + name + ".loc1.x", arena.getLoc1().getBlockX());
			arenaConfig.set("arena." + name + ".loc1.y", arena.getLoc1().getBlockY());
			arenaConfig.set("arena." + name + ".loc1.z", arena.getLoc1().getBlockZ());
			
			arenaConfig.set("arena." + name + ".loc2.x", arena.getLoc2().getBlockX());
			arenaConfig.set("arena." + name + ".loc2.y", arena.getLoc2().getBlockY());
			arenaConfig.set("arena." + name + ".loc2.z", arena.getLoc2().getBlockZ());
			
			arenaConfig.set("arena." + name + ".world", arena.getWorld());
			arenaConfig.set("arena." + name + ".active", false);
			arenaConfig.set("arena." + name + ".teamarena", true);
			try {
				arenaConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_CREATE_SUCCESS").replace("%arena%", arena.getName())));
			
		}
		
	}
		
	
	
	public Arena getArenaByName(String name) {
		for(Arena arena : arenas) {
			if(arena.getName().equalsIgnoreCase(name)) {
				return arena;
			}
		}
		
		return null;
	}
	
	public Arena getArenaByPlayer(Player player) {
		for(Arena arena : arenas) {
			if(arena.getFighters().contains(player.getName())) {
				return arena;
			}
		}
		
		return null;
	}
	
	//team fights
	@SuppressWarnings("deprecation")
	public void teleportTeams(Team team1, Team team2) {
		Arena arena = available.get(0);
		
		if(arena == null) {
			return;
		}
		
		available.remove(arena);
		arena.setInactive();
		
		int x1 = arena.getXTeam1();
		int y1 = arena.getYTeam1();
		int z1 = arena.getZTeam1();
		
		int x2 = arena.getXTeam2();
		int y2 = arena.getYTeam2();
		int z2 = arena.getZTeam2();
		
		float pitch1 = arena.getPitchTeam1();
		float yaw1 = arena.getYawTeam1();
		
		float pitch2 = arena.getPitchTeam2();
		float yaw2 = arena.getYawTeam2();
		
		Location loc1 = new Location(Bukkit.getWorld(arena.getWorld()), x1, y1, z1);
		loc1.setPitch(pitch1);
		loc1.setYaw(yaw1);
		
		Location loc2 = new Location(Bukkit.getWorld(arena.getWorld()), x2, y2, z2);
		loc2.setPitch(pitch2);
		loc2.setYaw(yaw2);
		
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team1.getTeamMembers().contains(p.getName())) {
				p.teleport(loc1);
				arena.getFighters().add(p.getName());
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team2.getTeamMembers().contains(p.getName())) {
				p.teleport(loc2);
				arena.getFighters().add(p.getName());
			}
		}

	}
	
	@SuppressWarnings("deprecation")
	public void teleportKothTeams(Team team1, Team team2) {
		Arena arena = available.get(0);
		
		if(arena == null) {
			return;
		}
		
		available.remove(arena);
		arena.setActive();
		
		int x1 = arena.getXTeam1();
		int y1 = arena.getYTeam1();
		int z1 = arena.getZTeam1();
		
		int x2 = arena.getXTeam2();
		int y2 = arena.getYTeam2();
		int z2 = arena.getZTeam2();
		
		float pitch1 = arena.getPitchTeam1();
		float yaw1 = arena.getYawTeam1();
		
		float pitch2 = arena.getPitchTeam2();
		float yaw2 = arena.getYawTeam2();
		
		Location loc1 = new Location(Bukkit.getWorld(arena.getWorld()), x1, y1, z1);
		loc1.setPitch(pitch1);
		loc1.setYaw(yaw1);
		
		Location loc2 = new Location(Bukkit.getWorld(arena.getWorld()), x2, y2, z2);
		loc2.setPitch(pitch2);
		loc2.setYaw(yaw2);
		
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team1.getTeamMembers().contains(p.getName())) {
				p.teleport(loc1);
				arena.getFighters().add(p.getName());
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team2.getTeamMembers().contains(p.getName())) {
				p.teleport(loc2);
				arena.getFighters().add(p.getName());
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(arena.getFighters().contains(p.getName())) {
				p.sendMessage(ChatColor.GOLD + "Arena: " + ChatColor.YELLOW + arena.getName());
				p.sendMessage(ChatColor.GOLD + "Timer: " + ChatColor.YELLOW + Run.toMMSS(arena.getArenaTimer()));
				p.sendMessage(ChatColor.GOLD + "Active: " + ChatColor.YELLOW + arena.isActive());
			}
		}

	}
	
	//1v1's
	public void teleportPlayers(Player player1, Player player2) {
		Arena arena = pAvailable.get(0);
		
		if(arena == null) {
			return;
		}
		
		pAvailable.remove(arena);
		arena.setInactive();
		
		arena.addFighter(player1.getName());
		arena.addFighter(player2.getName());
		
		int x1 = arena.getXTeam1();
		int y1 = arena.getYTeam1();
		int z1 = arena.getZTeam1();
		
		int x2 = arena.getXTeam2();
		int y2 = arena.getYTeam2();
		int z2 = arena.getZTeam2();
		
		float pitch1 = arena.getPitchTeam1();
		float yaw1 = arena.getYawTeam1();
		
		float pitch2 = arena.getPitchTeam2();
		float yaw2 = arena.getYawTeam2();
		
		Location loc1 = new Location(Bukkit.getWorld(arena.getWorld()), x1, y1, z1);
		loc1.setPitch(pitch1);
		loc1.setYaw(yaw1);
		
		Location loc2 = new Location(Bukkit.getWorld(arena.getWorld()), x2, y2, z2);
		loc2.setPitch(pitch2);
		loc2.setYaw(yaw2);
		
		
		player1.teleport(loc1);
		player2.teleport(loc2);

		player1.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_TELEPORT").replace("%creator%", arena.getCreator()).replace("%arena%", arena.getName())));
		
		player2.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_TELEPORT").replace("%creator%", arena.getCreator()).replace("%arena%", arena.getName())));
	}
	
	//remove teams
	public void removeTeams(Player player) {
		final Arena arena = getArenaByPlayer(player);
		
		if(!arenas.contains(arena)) {
			return;
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			if(arena.getSpec().contains(p.getName())) {
				spawn.teleportSpawn(p);
				p.setGameMode(GameMode.SURVIVAL);
				p.setAllowFlight(false);
				arena.getSpec().remove(p.getName());
			}
		}

		for(Player p : Bukkit.getOnlinePlayers()) {
			
			if(arena.getFighters().contains(p.getName())) {
				
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				
			}
		}

		arena.setTimer(arena.getArenaTimer());
		arena.setInactive();				
		arena.getCappers().clear();
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(IranPvP.plugin, new Runnable() {

			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					
					if(arena.getFighters().contains(p.getName())) {
						spawn.teleportSpawn(p);
					}
				}
				
				available.add(arena);

				arena.getFighters().clear();
				arena.getSpec().clear();				
			}
			
		}, 60);
		
	}
	
	//remove 1v1's
	public void removePlayer(Player player) {
		Arena arena = getArenaByPlayer(player);
		
		if(!arenas.contains(arena)) {
			return;
		}

		pAvailable.add(arena);
		
		arena.setInactive();
		arena.setTimer(arena.getArenaTimer());
		arena.getFighters().clear();
		arena.getCappers().clear();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(arena.getSpec().contains(p.getName())) {
				spawn.teleportSpawn(p);
				p.setAllowFlight(false);
				p.setGameMode(GameMode.SURVIVAL);
				arena.getSpec().remove(p.getName());
				player.showPlayer(p);
				
			}
		}
		
		arena.getSpec().clear();
	}
	
	public boolean insideCube(Player player, Location loc) {
		Arena arena = getArenaByPlayer(player);
		
		if(!arenas.contains(arena)) {
			return false;
		}
		
		if((loc.getBlockX() >= arena.getMinX()) && (loc.getBlockX() <= arena.getMaxX())) {
			if((loc.getBlockZ() >= arena.getMinZ()) && (loc.getBlockZ() <= arena.getMaxZ())) {
				if((loc.getBlockY() >= arena.getMinY()) && (loc.getBlockY() <= arena.getMaxY())) {
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean isSpectator(Player player) {
		for(Arena arena : arenas) {
			if(arena.getSpec().contains(player.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	public Arena getArenaBySpectator(Player player) {
		for(Arena arena : arenas) {
			if(arena.getSpec().contains(player.getName())) {
				return arena;
			}
		}
		return null;
	}
	
	public ArrayList<Arena> getAllArena() {
		return arenas;
	}
	
	public ArrayList<Arena> getAllAvailable() {
		return available;
	}
}