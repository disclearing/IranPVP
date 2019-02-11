package me.iran.iranpvp.events.inhanditems;

import me.iran.iranpvp.IranPvP;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropItemsInHandEvent implements Listener{

	IranPvP plugin;
	
	public DropItemsInHandEvent (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent event) {
		if(event.getItemDrop().getItemStack().hasItemMeta()) {
			
			if(event.getItemDrop() == null) {
				return;
			}
			
			if(event.getItemDrop().getItemStack() == null) {
				return;
			}
			
			if(event.getItemDrop().getItemStack().getItemMeta() == null) {
				return;
			}

			if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName() == null) {
				return;
			}
			
		/*		KIT_EDITOR: '&4Kit Editor'x
				RANKED_SWORD: '&a&lRanked Queue'x
				UNRANKED_SWORD: '&b&lUnranked Queue'x
				CREATE_TEAM: '&6&lCreate Team'x
				TEAM_INFO: '&a&lTeam Info'x
				DISBAND_TEAM: '&c&lDisband Team'x
				ALL_TEAMS: '&e&lView Current Teams'x
				LEAVE_TEAM: '&c&lLeave Team'x
				PING: '&aPing: &l%ping%'*/
			
			if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("CREATE_TEAM")))) {
				event.setCancelled(true);
			} else if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_INFO")))) {
				event.setCancelled(true);
			} else if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("LEAVE_TEAM")))) {
				event.setCancelled(true);
			} else if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ALL_TEAMS")))) {
				event.setCancelled(true);
			} else if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("DISBAND_TEAM")))) {
				event.setCancelled(true);
			}  else if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.RED.toString() + ChatColor.BOLD + "Leave Unranked queue")) {
				event.setCancelled(true);
			}  else if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("KIT_EDITOR")))) {
				event.setCancelled(true);
			} else if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("UNRANKED_SWORD")))) {
				event.setCancelled(true);
			} else if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.RED + "Leave Spectator")) {
				event.setCancelled(true);
			} else if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.RED.toString() + ChatColor.BOLD + "Leave Ranked queue")) {
				event.setCancelled(true);
			} else if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("RANKED_SWORD")))) {
				event.setCancelled(true);
			}
			
		} 
	}
	
}
