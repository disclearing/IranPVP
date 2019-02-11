package me.iran.iranpvp.ffa;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.kit.KitManager;
import me.iran.iranpvp.type.GameTypeManager;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FFARunnable extends BukkitRunnable {

	IranPvP plugin;
	
	public FFARunnable(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	private KitManager km = new KitManager();
	private TeleportSpawn spawn = new TeleportSpawn();
	
	
	@Override
	public void run() {
		if(FFACommand.enabled) {
			FFACommand.setTimer(FFACommand.getTimer() - 1);
			
			if(FFACommand.getTimer() == 40) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "FFA Event Started! " + ChatColor.RED.toString() + ChatColor.BOLD + "/join" + ChatColor.GOLD + " (40 Seconds)");
			} else if(FFACommand.getTimer() == 20) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "FFA Event Started! " + ChatColor.RED.toString() + ChatColor.BOLD + "/join" + ChatColor.GOLD + " (20 Seconds)");
			} else if(FFACommand.getTimer() == 10) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "FFA Event Started! " + ChatColor.RED.toString() + ChatColor.BOLD + "/join" + ChatColor.GOLD + " (10 Seconds)");
			} else if(FFACommand.getTimer() == 5) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "FFA Event Started! " + ChatColor.RED.toString() + ChatColor.BOLD + "/join" + ChatColor.GOLD + " (5 Seconds)");
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(FFACommand.fighters.contains(p.getName())) {
						km.getKits(p, GameTypeManager.getManager().getGameTypeByName(FFACommand.gameType));
					}
				}
			
			} else if(FFACommand.getTimer() == 4) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "FFA Event Started! " + ChatColor.RED.toString() + ChatColor.BOLD + "/join" + ChatColor.GOLD + " (4 Seconds)");
			} else if(FFACommand.getTimer() == 3) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "FFA Event Started! " + ChatColor.RED.toString() + ChatColor.BOLD + "/join" + ChatColor.GOLD + " (3 Seconds)");
			} else if(FFACommand.getTimer() == 2) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "FFA Event Started! " + ChatColor.RED.toString() + ChatColor.BOLD + "/join" + ChatColor.GOLD + " (2 Seconds)");
			} else if(FFACommand.getTimer() == 1) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "FFA Event Started! " + ChatColor.RED.toString() + ChatColor.BOLD + "/join" + ChatColor.GOLD + " (1 Seconds)");
			} else if(FFACommand.getTimer() == 0) {
				
				if(FFACommand.fighters.size() < 2) {
					for(String p : FFACommand.fighters) {
						Player pl = Bukkit.getPlayer(p);
						
						spawn.teleportSpawn(pl);
					
					}
					
					FFACommand.enabled = false;
					FFACommand.setTimer(60);
					FFACommand.fighters.clear();
					
					Bukkit.broadcastMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Not enough players, FFA event Stopped");
			
				} else {
						
					FFACommand.setTimer(0);
				
					Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "FFA Event Started!");
				
				}

			}
		}
	}

}
