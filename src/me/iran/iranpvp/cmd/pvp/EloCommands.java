package me.iran.iranpvp.cmd.pvp;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.type.GameTypeManager;
import me.iran.iranpvp.type.RankedEvent;
import me.iran.iranpvp.type.RankedManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EloCommands implements CommandExecutor {

	IranPvP plugin;
	
	public EloCommands (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	RankedEvent rank = new RankedEvent(plugin);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("elo")) {
			
			if(args.length < 1) {
				
				player.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + player.getName() + "'s Elo");
				
				for(GameType game : GameTypeManager.getManager().getAllGameTypes()) {
					
					player.sendMessage(ChatColor.GREEN + game.getName() + ": " + ChatColor.GRAY + RankedManager.getManager().getRank(player, game).getElo());
					
				}
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				return true;
			}
			
			if(target.isOnline()) {
				
				player.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + target.getName() + "'s Elo");
				
				for(GameType game : GameTypeManager.getManager().getAllGameTypes()) {
					
					player.sendMessage(ChatColor.GREEN + game.getName() + ": " + ChatColor.GRAY + RankedManager.getManager().getRank(target, game).getElo());
					
				}
			} else {
			
			}
		}
		
		return true;
	}
	
}
