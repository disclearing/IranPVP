package me.iran.iranpvp.utils;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.duel.DuelManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EnderpearlCooldown implements Listener {
	private static HashMap<String, Integer> enderpearl = new HashMap<String, Integer>();

	IranPvP plugin;
	
	ScoreboardUtils sb = new ScoreboardUtils();
	
	public EnderpearlCooldown (IranPvP plugin) {
		this.plugin = plugin;
	}

    @EventHandler
    public void onEnderpearl(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if(event.getAction() == null) {
        	return;
        }
        
        
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	if(player.getItemInHand().getType() == Material.ENDER_PEARL) {
        		
        		  if(DuelManager.getManager().getGames().containsKey(player.getName())) {
        			  player.sendMessage(ChatColor.RED + "Can't pearl while the game hasn't started");
        			  event.setCancelled(true);
        		  }
        		 
        		  if(enderpearl.containsKey(player.getName())) {
        			  
        			  event.setCancelled(true);
        			  player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ENDERPEARL_COOLDOWN_MESSAGE").replace("%timer%", "" + enderpearl.get(player.getName()))));

        		  } else {
        			  
        			  sb.add(player, player.getScoreboard(), ChatColor.LIGHT_PURPLE, "pearl", ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.ENDERPEARL")));
        			  
        			  enderpearl.put(player.getName(), 16);
				}
			}
		}

	}
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
    	Player player = event.getPlayer();
    	
    	if(enderpearl.containsKey(player.getName())) {
    		enderpearl.remove(player.getName());
    	}
    }

    public static HashMap<String, Integer> getEnderpearl() {
    	return enderpearl;
    }
    
}
