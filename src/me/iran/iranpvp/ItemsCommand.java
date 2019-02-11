package me.iran.iranpvp;

import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemsCommand implements CommandExecutor {

	IranPvP plugin;
	
	public ItemsCommand(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	TeleportSpawn spawn = new TeleportSpawn();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if(!(sender instanceof Player)) {
			if(cmd.getName().equalsIgnoreCase("iranpvpitems")) {
				
				if(args.length < 1) {
					sender.sendMessage(ChatColor.RED + "/items <player>");
					return true;
				}
				
				Player player = Bukkit.getPlayer(args[0]);
				
				if(player == null) {
					return true;
				}
				
				if (TeamManager.getManager().isInTeam(player)) {
					Team team = TeamManager.getManager()
							.getTeamByPlayer(player);
					if (TeamDuelManager.getManager().isInDuel(team)) {
						player.sendMessage(ChatColor.RED + "Can't do this while in a duel");
						return true;
					}
				}

				if (DuelManager.getManager().isInDuel(player)) {
					player.sendMessage(ChatColor.RED + "Can't do this while in a duel");
					return true;
				}

				spawn.teleportSpawn(player);
			}
			

			
		}
		
		return true;
	}
	
}
