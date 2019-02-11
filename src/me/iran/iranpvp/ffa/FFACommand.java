package me.iran.iranpvp.ffa;

import java.util.ArrayList;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.kit.KitEvents;
import me.iran.iranpvp.kit.KitManager;
import me.iran.iranpvp.runnable.Run;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.type.GameTypeManager;
import me.iran.iranpvp.utils.CustomInventory;
import me.iran.iranpvp.utils.OnJoinItems;
import me.iran.iranpvp.utils.ScoreboardUtils;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public class FFACommand implements CommandExecutor {

	IranPvP plugin;

	public FFACommand(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	public static boolean enabled = false;
	private static int timer = 60;
	public static String gameType = "";
	//Set gametype
	
	public static ArrayList<String> fighters = new ArrayList<String>();
	
	TeleportSpawn spawn = new TeleportSpawn();
	
	OnJoinItems items = new OnJoinItems();
	
	KitManager km = new KitManager();
	
	CustomInventory inv = new CustomInventory();
	
	Run run = new Run(plugin);
	
	ScoreboardUtils sb = new ScoreboardUtils();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("ffa")) {
			if (!player.hasPermission("rifthq.ffa")) {
				return true;
			}
			if(args.length < 1) {
				player.sendMessage(ChatColor.RED + "/ffa start | stop | set");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("start")) {
				if (enabled) {
					player.sendMessage(ChatColor.RED
							+ "A FFA seems to be running!");
					return true;
				}
				
				player.openInventory(inv.ffa(player));

			}

			if (args[0].equalsIgnoreCase("stop")) {
				if (!enabled) {
					player.sendMessage(ChatColor.RED
							+ "FFA is not running at the moment");
					return true;
				}

				enabled = false;
				timer = 60;
				gameType = "";
				Bukkit.broadcastMessage(ChatColor.RED + "FFA Event Stopped by "
						+ player.getDisplayName());
			}

			if (args[0].equalsIgnoreCase("set")) {

				if (!IranPvP.plugin.getConfig().contains("ffa.arena")) {

					IranPvP.plugin.getConfig().createSection("ffa.arena.x");
					IranPvP.plugin.getConfig().createSection("ffa.arena.y");
					IranPvP.plugin.getConfig().createSection("ffa.arena.z");
					IranPvP.plugin.getConfig().createSection("ffa.arena.pitch");
					IranPvP.plugin.getConfig().createSection("ffa.arena.yaw");
					IranPvP.plugin.getConfig().createSection("ffa.arena.world");

					IranPvP.plugin.getConfig().set("ffa.arena.x", player.getLocation().getBlockX());
					IranPvP.plugin.getConfig().set("ffa.arena.y", player.getLocation().getBlockY());
					IranPvP.plugin.getConfig().set("ffa.arena.z", player.getLocation().getBlockZ());
					IranPvP.plugin.getConfig().set("ffa.arena.pitch", player.getLocation().getPitch());
					IranPvP.plugin.getConfig().set("ffa.arena.yaw", player.getLocation().getYaw());
					IranPvP.plugin.getConfig().set("ffa.arena.world", player.getLocation().getWorld().getName());
					
					IranPvP.plugin.saveConfig();
					player.sendMessage(ChatColor.GREEN + "Set FFA arena location");
					
				} else {
					IranPvP.plugin.getConfig().set("ffa.arena.x", player.getLocation().getBlockX());
					IranPvP.plugin.getConfig().set("ffa.arena.y", player.getLocation().getBlockY());
					IranPvP.plugin.getConfig().set("ffa.arena.z", player.getLocation().getBlockZ());
					IranPvP.plugin.getConfig().set("ffa.arena.pitch", player.getLocation().getPitch());
					IranPvP.plugin.getConfig().set("ffa.arena.yaw", player.getLocation().getYaw());
					IranPvP.plugin.getConfig().set("ffa.arena.world", player.getLocation().getWorld().getName());
					
					IranPvP.plugin.saveConfig();
					player.sendMessage(ChatColor.GREEN + "Set FFA arena location");
				}

			}
		}

		if (cmd.getName().equalsIgnoreCase("join")) {
			
			if(TeamManager.getManager().isInTeam(player)) {
				player.sendMessage(ChatColor.RED + "Can't do this while in a team");
				return true;
			}
			
			if(DuelManager.getManager().isInDuel(player)) {
				player.sendMessage(ChatColor.RED + "Can't do this while in a duel");
				return true;
			}
			
			if(ArenaManager.getManager().isSpectator(player)) {
				player.sendMessage(ChatColor.RED + "Can't do this while in spectator mode");
				return true;
			}
			
			if(fighters.contains(player.getName())) {
				player.sendMessage(ChatColor.RED + "Already in this FFA");
				return true;
			}
			
			if(fighters.size() >= 80) {
				player.sendMessage(ChatColor.RED + "This FFA is already full");
				return true;
			}
			
			if(enabled == false) {
				player.sendMessage(ChatColor.RED + "This FFA is not active");
				return true;
			}
			
			if(getTimer() <= 0) {
				player.sendMessage(ChatColor.RED + "This FFA is in progress");
				return true;
			}
			
			if(KitEvents.getEditing().containsKey(player.getName())) {
				player.sendMessage(ChatColor.RED + "Can't join FFA while in editing mode");
				return true;
			}
			
			fighters.add(player.getName());

			for(Player p : Bukkit.getOnlinePlayers()) {
				
				if(fighters.contains(p.getName())) {
					player.showPlayer(p);
					p.showPlayer(player);
				}
				
			}
			
			ffaTeleport(player);
			
			items.clearInv(player);
			
			km.getKits(player, GameTypeManager.getManager().getGameTypeByName(gameType));
			
			player.setScoreboard(sb.ffaBoard(player));
			
			Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.GOLD + " has joined FFA (" + fighters.size() + "/80)");
			
		}
		
		if(cmd.getName().equalsIgnoreCase("leave")) {
			if(!fighters.contains(player.getName())) {
				player.sendMessage(ChatColor.RED + "Already in this FFA");
				return true;
			}
			
			if(getTimer() <= 0) {
				player.sendMessage(ChatColor.RED + "Can't leave mid game");
				return true;
			}
			
			fighters.remove(player.getName());
			
			player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			
			spawn.teleportSpawn(player);
			
			Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.RED + " has left FFA (" + fighters.size() + "/80)");
		}

		return true;
	}

	public static int getTimer() {
		return timer;
	}

	public static void setTimer(int timer) {
		FFACommand.timer = timer;
	}

	public void ffaTeleport(Player player) {
		int x = IranPvP.plugin.getConfig().getInt("ffa.arena.x");
		int y = IranPvP.plugin.getConfig().getInt("ffa.arena.y");
		int z = IranPvP.plugin.getConfig().getInt("ffa.arena.z");
		float pitch = (float) IranPvP.plugin.getConfig().getDouble("ffa.arena.pitch");
		float yaw = (float) IranPvP.plugin.getConfig().getDouble("ffa.arena.yaw");
		String world = IranPvP.plugin.getConfig().getString("ffa.arena.world");
		
		Location loc = new Location(Bukkit.getWorld(world), x, y, z);
		loc.setPitch(pitch);
		loc.setYaw(yaw);
		
		player.teleport(loc);
		
	}
	
}
