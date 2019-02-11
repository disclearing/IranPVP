package me.iran.iranpvp.teams;

import me.iran.iranpvp.IranPvP;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TeamChat implements Listener {

	IranPvP plugin;
	
	public TeamChat (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(TeamManager.getManager().isInTeam(player)) {
			Team team = TeamManager.getManager().getTeamByPlayer(player);
			
			String msg = event.getMessage();

			for(Player p : Bukkit.getOnlinePlayers()) {
				
				if(team.getTeamMembers().contains(p.getName())) {
					
					if(msg.startsWith("!")) {
						event.setCancelled(true);
						p.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "TEAM " + player.getName() + " " + ChatColor.GREEN + msg.replace("!", ""));
					}
				}
			}
		}
	}
	
}
