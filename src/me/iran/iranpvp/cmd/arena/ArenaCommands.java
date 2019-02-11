package me.iran.iranpvp.cmd.arena;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommands implements CommandExecutor {

	IranPvP plugin;
	
	public ArenaCommands(IranPvP plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Player command only");;
			return true;
		}

		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("arena")) {
			if(!player.hasPermission("kothpractice.staff")) {
				player.sendMessage(ChatColor.RED + "Not Enough Permissions.");
				return true;
			}
			
			if(args.length < 1) {
				player.sendMessage(ChatColor.RED + "/arena create <name>");
				player.sendMessage(ChatColor.RED + "/arena setteam1 <arena>");
				player.sendMessage(ChatColor.RED + "/arena setteam2 <arena>");
				player.sendMessage(ChatColor.RED + "/arena setloc1 <arena>");
				player.sendMessage(ChatColor.RED + "/arena setloc2 <arena>");
				player.sendMessage(ChatColor.RED + "/arena setteamarena true|false");
				player.sendMessage(ChatColor.RED + "/arena settime <arena> <time in seconds>");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/arena create <name>");
					return true;
				}
				
				ArenaManager.getManager().createArena(player.getLocation(), player.getLocation(), 0, args[1], player);
				
			}
			
			if(args[0].equalsIgnoreCase("setcreator")) {
				if(args.length < 3) {
					player.sendMessage(ChatColor.RED + "/arena setcreator <arena> <creator>");
					return true;
				}
				
				Arena arena = ArenaManager.getManager().getArenaByName(args[1]);
				
				if(ArenaManager.getManager().getAllArena().contains(arena)) {
					
					arena.setCreator(args[2]);
					player.sendMessage(ChatColor.GREEN + "Set the arena creator for arena " + arena.getName() + " to " + arena.getCreator());
				}
				
			}
			
			if(args[0].equalsIgnoreCase("av")) {
				player.sendMessage("" + ArenaManager.getManager().getAllAvailable().size());
			}
			
			if(args[0].equalsIgnoreCase("setteam1")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/arena setteam1 <arena name>");
					return true;
				}
				
				Arena arena = ArenaManager.getManager().getArenaByName(args[1]);
				
				if(arena == null) {
					player.sendMessage(ChatColor.RED + "Could not find that arena");
					return true;
				}
				
				
				int x = player.getLocation().getBlockX();
				int y = player.getLocation().getBlockY();
				int z = player.getLocation().getBlockZ();
				
				float pitch = player.getLocation().getPitch();
				float yaw = player.getLocation().getYaw();
				
				arena.setXTeam1(x);
				arena.setYTeam1(y);
				arena.setZTeam1(z);
				arena.setPitchTeam1(pitch);
				arena.setYawTeam1(yaw);
				
				ArenaManager.getManager().saveArena();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_SET_TEAM1").replace("%arena%", arena.getName())));
				
			}
			
			if(args[0].equalsIgnoreCase("setteam2")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/arena setteam2 <arena name>");
					return true;
				}
				
				Arena arena = ArenaManager.getManager().getArenaByName(args[1]);
				
				if(arena == null) {
					player.sendMessage(ChatColor.RED + "Could not find that arena");
					return true;
				}
				
				
				int x = player.getLocation().getBlockX();
				int y = player.getLocation().getBlockY();
				int z = player.getLocation().getBlockZ();
				
				float pitch = player.getLocation().getPitch();
				float yaw = player.getLocation().getYaw();
				
				arena.setXTeam2(x);
				arena.setYTeam2(y);
				arena.setZTeam2(z);
				arena.setPitchTeam2(pitch);
				arena.setYawTeam2(yaw);

				ArenaManager.getManager().saveArena();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_SET_TEAM2").replace("%arena%", arena.getName())));
			}
			
			if(args[0].equalsIgnoreCase("setloc1")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/arena setloc1 <arena name>");
					return true;
				}
				
				Arena arena = ArenaManager.getManager().getArenaByName(args[1]);
				
				if(arena == null) {
					player.sendMessage(ChatColor.RED + "Could not find that arena");
					return true;
				}
				
				int x = player.getLocation().getBlockX();
				int y = player.getLocation().getBlockY();
				int z = player.getLocation().getBlockZ();
				
				arena.setLoc1(player.getLocation());
				arena.setLoc1X(x);
				arena.setLoc1Y(y);
				arena.setLoc1Z(z);
				
				ArenaManager.getManager().saveArena();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_SET_LOC1").replace("%arena%", arena.getName())));
			}
			
			
			if(args[0].equalsIgnoreCase("setloc2")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/arena setloc2 <arena name>");
					return true;
				}
				
				Arena arena = ArenaManager.getManager().getArenaByName(args[1]);
				
				if(arena == null) {
					player.sendMessage(ChatColor.RED + "Could not find that arena");
					return true;
				}
				
				int x = player.getLocation().getBlockX();
				int y = player.getLocation().getBlockY();
				int z = player.getLocation().getBlockZ();
				
				arena.setLoc2(player.getLocation());
				arena.setLoc2X(x);
				arena.setLoc2Y(y);
				arena.setLoc2Z(z);
				
				ArenaManager.getManager().saveArena();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_SET_LOC2").replace("%arena%", arena.getName())));
			}
			
			if(args[0].equalsIgnoreCase("settime")) {
				if(args.length < 3) {
					player.sendMessage(ChatColor.RED + "/arena settimer <arena name> <timer>");
					return true;
				}
				
				Arena arena = ArenaManager.getManager().getArenaByName(args[1]);
				
				if(arena == null) {
					player.sendMessage(ChatColor.RED + "Could not find that arena");
					return true;
				}
				
				int time = Integer.parseInt(args[2]);
				
				arena.setArenaTimer(time);
				ArenaManager.getManager().saveArena();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_SET_TIME").replace("%time%", args[2]).replace("%arena%", arena.getName())));
			}
			
			if(args[0].equalsIgnoreCase("setteamarena")) {
				if(args.length < 3) {
					player.sendMessage(ChatColor.RED + "/arena setteamarena <arena name> true|false");
					return true;
				}
				
				Arena arena = ArenaManager.getManager().getArenaByName(args[1]);
				
				if(arena == null) {
					player.sendMessage(ChatColor.RED + "Could not find that arena");
					return true;
				}
				
				String bool = args[2];
				
				if(bool.equalsIgnoreCase("true")) {
					arena.setTeamArena(true);
					ArenaManager.getManager().saveArena();
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_SET_TEAM_TRUE").replace("%arena%", arena.getName())));
				} else if(bool.equalsIgnoreCase("false")) {
					arena.setTeamArena(false);
					ArenaManager.getManager().saveArena();
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ARENA_SET_TEAM_FALSE").replace("%arena%", arena.getName())));
				} else {
					player.sendMessage(ChatColor.RED + "/arena setteamarena <arena name> true|false");
				}
				
			}
			
			if(args[0].equalsIgnoreCase("info")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/arena info <arena>");
					return true;
				}
				
				Arena arena = ArenaManager.getManager().getArenaByName(args[1]);
				
				if(!ArenaManager.getManager().getAllArena().contains(arena)) {
					player.sendMessage(ChatColor.RED + "That arena doesn't exist");
					return true;
				}
				
				player.sendMessage(ChatColor.GOLD + "Arena Name: " + ChatColor.RED + arena.getName());
				player.sendMessage("================================");
				player.sendMessage(ChatColor.YELLOW + "Arena Team1 X: " + ChatColor.RED + arena.getXTeam1());
				player.sendMessage(ChatColor.YELLOW + "Arena Team1 Y: " + ChatColor.RED + arena.getYTeam1());
				player.sendMessage(ChatColor.YELLOW + "Arena Team1 Z: " + ChatColor.RED + arena.getZTeam1());
				player.sendMessage("================================");
				player.sendMessage(ChatColor.YELLOW + "Arena Team2 X: " + ChatColor.RED + arena.getXTeam2());
				player.sendMessage(ChatColor.YELLOW + "Arena Team2 Y: " + ChatColor.RED + arena.getYTeam2());
				player.sendMessage(ChatColor.YELLOW + "Arena Team2 Z: " + ChatColor.RED + arena.getZTeam2());
				player.sendMessage("================================");
				player.sendMessage(ChatColor.YELLOW + "Arena Max X: " + ChatColor.RED + arena.getMaxX());
				player.sendMessage(ChatColor.YELLOW + "Arena Max Y: " + ChatColor.RED + arena.getMaxY());
				player.sendMessage(ChatColor.YELLOW + "Arena Max Z: " + ChatColor.RED + arena.getMaxZ());
				player.sendMessage("================================");
				player.sendMessage(ChatColor.YELLOW + "Arena Min X: " + ChatColor.RED + arena.getMinX());
				player.sendMessage(ChatColor.YELLOW + "Arena Min Y: " + ChatColor.RED + arena.getMinY());
				player.sendMessage(ChatColor.YELLOW + "Arena Min Z: " + ChatColor.RED + arena.getMinZ());
				player.sendMessage("================================");
				player.sendMessage(ChatColor.YELLOW + "Arena Time: " + ChatColor.RED + arena.getArenaTimer());
				player.sendMessage(ChatColor.YELLOW + "Arena World: " + ChatColor.RED + arena.getWorld());
			}
			
			if(args[0].equalsIgnoreCase("delete")) {
				
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/arena delete <arena>");
					return true;
				}
				
				ArenaManager.getManager().deleteArena(args[1], player);
			}

		}
		
		
		return true;
	}

	
	
}
