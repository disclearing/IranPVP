package me.iran.iranpvp.utils;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.runnable.Run;
import me.iran.iranpvp.teams.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class TeleportSpawn {

	OnJoinItems items = new OnJoinItems();
	
	ScoreboardUtils sb = new ScoreboardUtils();
	
	public void teleportSpawn(Player player) {
		
		int x = IranPvP.plugin.getConfig().getInt("spawn.x");
		int y = IranPvP.plugin.getConfig().getInt("spawn.y");
		int z = IranPvP.plugin.getConfig().getInt("spawn.z");
		double pitch = IranPvP.plugin.getConfig().getDouble("spawn.pitch");
		double yaw = IranPvP.plugin.getConfig().getDouble("spawn.yaw");
		String world = IranPvP.plugin.getConfig().getString("spawn.world");

		Location loc = new Location(Bukkit.getWorld(world), x, y, z);
		loc.setPitch((float) pitch);
		loc.setYaw((float) yaw);

		if(TeamManager.getManager().isInTeam(player)) {
			if(TeamManager.getManager().getTeamByPlayer(player).getTeamLeader().equals(player.getName())) {
				items.teamLeaderItems(player);
			} else {
				items.teamItems(player);
			}
		} else {
			items.onJoin(player);
		}
		
		for(PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		
		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.teleport(loc);
		
		player.setScoreboard(sb.lobbyBoard(player));
		
		
		
	}
	
	public void teleportKit(Player player) {
		
		int x = IranPvP.plugin.getConfig().getInt("kit.x");
		int y = IranPvP.plugin.getConfig().getInt("kit.y");
		int z = IranPvP.plugin.getConfig().getInt("kit.z");
		double pitch = IranPvP.plugin.getConfig().getDouble("kit.pitch");
		double yaw = IranPvP.plugin.getConfig().getDouble("kit.yaw");
		String world = IranPvP.plugin.getConfig().getString("kit.world");

		Location loc = new Location(Bukkit.getWorld(world), x, y, z);
		loc.setPitch((float) pitch);
		loc.setYaw((float) yaw);

		player.teleport(loc);
		
		items.clearInv(player);
		
		player.setHealth(20.0);
		player.setFoodLevel(20);
		
	}
}
