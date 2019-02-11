package me.iran.iranpvp.type;

import java.io.File;
import java.io.IOException;

import me.iran.iranpvp.IranPvP;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RankedEvent implements Listener {

	IranPvP plugin;
	
	File file = null;
	
	public RankedEvent(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		createFiles(player);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		saveElo(player);
	}
	
	public void saveElo(Player player) {
		file = new File(IranPvP.plugin.getDataFolder() + "/ranked/" + player.getUniqueId().toString(), player.getUniqueId().toString() + ".yml");

		if(!file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder() + "/ranked/" + player.getUniqueId().toString(), player.getUniqueId().toString() + ".yml");
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			
			
			for(int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
				
				GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
				
				config.createSection(player.getUniqueId().toString()  + "." + game.getName());
				config.set(player.getUniqueId().toString() + "." + game.getName(), 1000);
			}
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			
			file = new File(IranPvP.plugin.getDataFolder() + "/ranked/" + player.getUniqueId().toString(), player.getUniqueId().toString() + ".yml");
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			for(int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
				
				GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
				
				if(!config.contains(player.getUniqueId().toString() + "." + game.getName())) {
					config.createSection(player.getUniqueId().toString()  + "." + game.getName());
					config.set(player.getUniqueId().toString() + "." + game.getName(), 1000);
				} else {
					
					int elo = RankedManager.getManager().getRank(player, game).getElo();
					
					config.set(player.getUniqueId().toString() + "." + game.getName(), elo);
					
					RankedManager.getManager().deleteRanked(player, game);
				}

			}
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void createFiles(Player player) {
		file = new File(IranPvP.plugin.getDataFolder() + "/ranked/" + player.getUniqueId().toString(), player.getUniqueId().toString() + ".yml");

		if(!file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder() + "/ranked/" + player.getUniqueId().toString(), player.getUniqueId().toString() + ".yml");
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			for(int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
				
				GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
				
				config.createSection(player.getUniqueId().toString() + "." + game.getName());
				config.set(player.getUniqueId().toString() + "." + game.getName(), 1000);
				
				int elo = config.getInt(player.getUniqueId().toString() + "." + game.getName());
				
				RankedManager.getManager().getAllRanked().add(new Ranked(elo, player, game));
			}
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			
			file = new File(IranPvP.plugin.getDataFolder() + "/ranked/" + player.getUniqueId().toString(), player.getUniqueId().toString() + ".yml");
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			for(int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
				
				GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
				
				if(!config.contains(player.getUniqueId().toString() + "." + game.getName())) {
					config.createSection(player.getUniqueId().toString() + "." + game.getName());
					config.set(player.getUniqueId().toString() + "." + game.getName(), 1000);
				}
				
				int elo = config.getInt(player.getUniqueId().toString() + "." + game.getName());
				
				RankedManager.getManager().getAllRanked().add(new Ranked(elo, player, game));
			}
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
/*	public void getOfflineElo(Player player, Player target) {
		
		file = new File(IranPvP.plugin.getDataFolder() + "/ranked/" + target.getUniqueId().toString(), target.getUniqueId().toString() + ".yml");
		
		if(file.exists()) {
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			player.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + target.getName() + "'s Elo");
			
			for(int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
				
				GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
				
				if(!config.contains(target.getUniqueId().toString() + "." + game.getName())) {
					config.createSection(target.getUniqueId().toString()  + "." + game.getName());
					config.set(target.getUniqueId().toString() + "." + game.getName(), 1000);
				}
				
				player.sendMessage(ChatColor.GREEN + game.getName() + ": " + ChatColor.GRAY + config.getInt(player.getUniqueId().toString() + "." + game.getName()));

			}
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			player.sendMessage(ChatColor.RED + "Couldn't find that users file");
		}
	
	}*/
	
}
