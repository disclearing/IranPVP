package me.iran.iranpvp.cmd.pvp;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.duel.Duel;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.duel.InvitePlayer;
import me.iran.iranpvp.duel.InvitePlayerManager;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.runnable.Run;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.type.Ranked;
import me.iran.iranpvp.type.RankedManager;
import me.iran.iranpvp.utils.CustomInventory;
import me.iran.iranpvp.utils.OnJoinItems;
import me.iran.iranpvp.utils.Queue;
import me.iran.iranpvp.utils.ScoreboardUtils;
import net.minecraft.server.v1_7_R4.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PvPCommands implements CommandExecutor {

	IranPvP plugin;
	
	public PvPCommands(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	public static HashMap<String, String> invite = new HashMap<String, String>();
	
	CustomInventory duelInv = new CustomInventory();
	Queue queue = new Queue();
	
	Run run = new Run(plugin);
	
	OnJoinItems items = new OnJoinItems();
	
	ScoreboardUtils sb = new ScoreboardUtils();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("duel")) {
			if(args.length < 1) {
				player.sendMessage(ChatColor.RED + "/duel <player>");
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_NULL").replace("%target%", args[0])));
				
				return true;
			}
			
			if(target == player) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_IS_PLAYER").replace("%target%", args[0])));
				return true;
			}
			
			if(InvitePlayerManager.getManager().hasInvited(player, target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ALREADY_DUELED_TARGET").replace("%target%", args[0])));
				return true;
			}
			
			if(DuelManager.getManager().isInDuel(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_BUSY").replace("%target%", args[0])));
				return true;
			}
			
			if(DuelManager.getManager().isInDuel(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("PLAYER_IN_DUEL")));
				return true;
			}
			
			if(queue.isInUnranked(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_QUEUE_ERROR")));
				return true;
			}
			
			if(queue.isInUnranked(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_IN_QUEUE").replace("%target%", args[0])));
				return true;
			}
			
			if(queue.isInRanked(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_QUEUE_ERROR")));
				return true;
			}
			
			if(queue.isInRanked(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_IN_QUEUE").replace("%target%", args[0])));
				return true;
			}
			
			if(TeamManager.getManager().isInTeam(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_TEAM_ERROR")));
				return true;
			}
			
			if(TeamManager.getManager().isInTeam(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_IN_TEAM").replace("%target%", args[0])));
				return true;
			}
			
			if(ArenaManager.getManager().isSpectator(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_SPECTATOR_TARGET").replace("%target%", args[0])));
				return true;
			}
			
			if(ArenaManager.getManager().isSpectator(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_SPECTATOR_ERROR").replace("%target%", args[0])));
				return true;
			}
			
			invite.put(player.getName(), target.getName());
			player.openInventory(duelInv.gameTypes(player));
			
		}
		
		if(cmd.getName().equalsIgnoreCase("accept")) {
			if(args.length < 1) {
				player.sendMessage(ChatColor.RED + "/accept <player>");
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_NULL").replace("%target%", args[0])));
				return true;
			}
			
			if(target == player) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_IS_PLAYER").replace("%target%", args[0])));
				return true;
			}
			
			if(DuelManager.getManager().isInDuel(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_DUEL_ERROR")));
				return true;
			}
			
			if(DuelManager.getManager().isInDuel(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_BUSY").replace("%target%", args[0])));
				return true;
			}
			
			if(queue.isInUnranked(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_QUEUE_ERROR").replace("%target%", args[0])));
				return true;
			}
			
			if(queue.isInUnranked(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_IN_QUEUE").replace("%target%", args[0])));
				return true;
			}
			
			if(queue.isInRanked(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_QUEUE_ERROR").replace("%target%", args[0])));
				return true;
			}
			
			if(queue.isInRanked(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_IN_QUEUE").replace("%target%", args[0])));
				return true;
			}
			
			if(TeamManager.getManager().isInTeam(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_TEAM_ERROR")));
				return true;
			}
			
			if(TeamManager.getManager().isInTeam(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TARGET_IN_TEAM").replace("%target%", args[0])));
				return true;
			}
			
			if(ArenaManager.getManager().isSpectator(target)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_SPECTATOR_TARGET").replace("%target%", args[0])));
				return true;
			}
			
			if(ArenaManager.getManager().isSpectator(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_SPECTATOR_ERROR")));
				return true;
			}

			if(InvitePlayerManager.getManager().hasInvited(target, player)) {
				
				InvitePlayer invite = InvitePlayerManager.getManager().getPlayerInvites(target, player);
				
				DuelManager.getManager().createDuel(target, player, invite.getGameType());
				InvitePlayerManager.getManager().clearInvites(target);
				InvitePlayerManager.getManager().clearInvites(player);
			} else {
				player.sendMessage(ChatColor.RED + "That person has not dueled you");
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("deny")) {
			if(args.length < 1) {
				player.sendMessage(ChatColor.RED + "/deny <player>");
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(ChatColor.RED + "Could not find that player");
				return true;
			}
			
			if(target == player) {
				player.sendMessage(ChatColor.RED + "Can't do that");
				return true;
			}
			
			if(invite.containsKey(target.getName())) {
				if(InvitePlayerManager.getManager().hasInvited(target, player)) {
					invite.remove(target.getName());
					InvitePlayerManager.getManager().deleteInvites(target, player);
					player.sendMessage(ChatColor.RED.toString() + "Denied Duel from " + ChatColor.BOLD + target.getName());
				}
			} else {
				player.sendMessage(ChatColor.RED + "That person has not Dueled you");
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("ping")) {
			
			if(args.length < 1) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("PING").replace("%ping%", "" + getPing(player))));
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(ChatColor.RED + "Couldn't find player");
				return true;
			}
			
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("PING_TARGET").replace("%target%", target.getName()).replace("%ping%", "" + getPing(target))));
			
		}
		
		if(cmd.getName().equalsIgnoreCase("spectate")) {
			if(args.length < 1) {
				player.sendMessage(ChatColor.RED + "/spectate <player>");
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(ChatColor.RED + "Could not find that player");
				return true;
			}
			
			if(target == player) {
				player.sendMessage(ChatColor.RED + "Can't do that");
				return true;
			}
			
			if(TeamManager.getManager().isInTeam(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_TEAM_ERROR")));
				return true;
			}
			
			if(DuelManager.getManager().isInDuel(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_DUEL_ERROR")));
				return true;
			}

			if(queue.isInUnranked(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("IN_QUEUE_ERROR")));
				return true;
			}
			
			if(TeamManager.getManager().isInTeam(target)) {
				Team team = TeamManager.getManager().getTeamByPlayer(target);
					if(ArenaManager.getManager().isSpectator(player)) {
						player.sendMessage(ChatColor.RED + "Already in spectator mode");
						return true;
					}
					
					if(!TeamDuelManager.getManager().isInDuel(team)) {
						player.sendMessage(ChatColor.RED + "That team is not in a duel right now!");
						return true;
					}
					
					Arena arena = ArenaManager.getManager().getArenaByPlayer(target);
					
					if(arena.isActive()) {
						arena.addSpec(player.getName());
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SPECTATE_TEAM_MESSAGE").replace("%team%", team.getTeamName())));
						
						player.setGameMode(GameMode.CREATIVE);

					for (Player p : Bukkit.getOnlinePlayers()) {
						if (arena.getFighters().contains(p.getName())) {
							p.hidePlayer(player);
							player.showPlayer(p);
						}
					}

					player.setScoreboard(sb.specTeamBoard(player, target));

					items.spectatorMode(player);

					player.teleport(target.getLocation());
					player.setAllowFlight(true);

					} else {
						arena.addSpec(player.getName());
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SPECTATE_TEAM_MESSAGE").replace("%team%", team.getTeamName())));
						
						player.setGameMode(GameMode.CREATIVE);
					
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (arena.getFighters().contains(p.getName())) {
							p.hidePlayer(player);
							player.showPlayer(p);
						}
					}
					
					player.setScoreboard(sb.specTeamBoard(player, target));
					
					items.spectatorMode(player);
					player.teleport(target.getLocation());
					player.setAllowFlight(true);
				}

					
			} else if(DuelManager.getManager().isInDuel(target)) {
				
					if(ArenaManager.getManager().isSpectator(player)) {
						player.sendMessage(ChatColor.RED + "Already in spectator mode");
						return true;
					}
				
					Arena arena = ArenaManager.getManager().getArenaByPlayer(target);
				
					Duel duel = DuelManager.getManager().getDuelByPlayer(target);
					
					if(!duel.isRanked()) {
						arena.addSpec(player.getName());
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SPECTATE_PLAYER_MESSAGE").replace("%player2%", duel.getPlayer2().getName()).replace("%player1%", duel.getPlayer1().getName())));
						
						player.teleport(target.getLocation());
						items.spectatorMode(player);
						
						player.setGameMode(GameMode.CREATIVE);
						
						player.setScoreboard(sb.specDuelBoard(player, target));
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							if(arena.getFighters().contains(p.getName())) {
								p.hidePlayer(player);
								player.showPlayer(p);
							}
						}
						
						items.spectatorMode(player);
						player.teleport(target.getLocation());
						player.setAllowFlight(true);
						
					} else {
						
						Ranked elo1 = RankedManager.getManager().getRank(duel.getPlayer1(), duel.getGameType());
						Ranked elo2 = RankedManager.getManager().getRank(duel.getPlayer2(), duel.getGameType());
						
						arena.addSpec(player.getName());
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SPECTATE_PLAYER_MESSAGE_RANKED").replace("%player1elo%", "" + elo1.getElo()).replace("%player2elo%", "" + elo2.getElo()).replace("%player2%", duel.getPlayer2().getName()).replace("%player1%", duel.getPlayer1().getName())));
						
						player.teleport(target.getLocation());
						items.spectatorMode(player);
						
						player.setScoreboard(sb.specDuelBoard(player, target));
						
						player.setGameMode(GameMode.CREATIVE);
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							if(arena.getFighters().contains(p.getName())) {
								p.hidePlayer(player);
								player.showPlayer(p);
							}
						}
						
						items.spectatorMode(player);
						player.teleport(target.getLocation());
						player.setAllowFlight(true);
					}
					
			} else {
				player.sendMessage(ChatColor.RED + "That person is not in a Duel");
			}
			
		}
		
		return true;
	}

	public int getPing(Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		return ep.ping;
	}
	
}
