package me.iran.iranpvp.cmd.team;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.duel.team.TeamInviteManager;
import me.iran.iranpvp.runnable.Run;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.utils.BungeeChat;
import me.iran.iranpvp.utils.CustomInventory;
import me.iran.iranpvp.utils.OnJoinItems;
import me.iran.iranpvp.utils.Queue;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommands implements CommandExecutor {

	IranPvP plugin;
	
	public static HashMap<Team, Team> invite = new HashMap<Team, Team>();
	
	public TeamCommands(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	Run run = new Run(plugin);
	Queue queue = new Queue();
	OnJoinItems items = new OnJoinItems();
	BungeeChat click = new BungeeChat();
	CustomInventory inv = new CustomInventory();
	TeleportSpawn spawn = new TeleportSpawn();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " Player Command Only!");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("team")) {
			
			if(args.length < 1) {
				player.sendMessage(ChatColor.AQUA.toString() + ChatColor.STRIKETHROUGH + "----------------");
				
				player.sendMessage(ChatColor.YELLOW + "Team Leader Commands");
				player.sendMessage("");
				player.sendMessage(ChatColor.GRAY + "/team create");
				player.sendMessage(ChatColor.GRAY + "/team disband");
				player.sendMessage(ChatColor.GRAY + "/team kick <player>");
				player.sendMessage(ChatColor.GRAY + "/team leave");
				player.sendMessage(ChatColor.GRAY + "/team duel <team>");
				player.sendMessage(ChatColor.GRAY + "/team accept <team>");
				player.sendMessage(ChatColor.GRAY + "/team invite <player>");
				player.sendMessage(ChatColor.GRAY + "/team bard <player>");
				player.sendMessage(ChatColor.AQUA.toString() + ChatColor.STRIKETHROUGH + "----------------");
				
				player.sendMessage("");
				player.sendMessage(ChatColor.YELLOW + "Team Commands");
				player.sendMessage(ChatColor.GRAY + "/team join <player>");
				player.sendMessage(ChatColor.GRAY + "/team leave");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
				
				if(queue.isInUnranked(player)) {
					player.sendMessage(ChatColor.RED + "Can't create a team while in a Queue");
					return true;
				}
				
				TeamManager.getManager().createTeam(player.getName(), player);
			}
			
			if(args[0].equalsIgnoreCase("invite")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + " /team invite <player>");
					return true;
				}
				
				Player target = Bukkit.getPlayer(args[1]);
				
				if(target == null) {
					player.sendMessage(ChatColor.RED + " Invalid player");
					return true;
				}
				
				if(target == player) {
					player.sendMessage(ChatColor.RED + " You can't invite yourself");
					return true;
				}
				
				TeamManager.getManager().inviteToTeam(player, target);
			}
			
			if(args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("j")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.GOLD + "[Teams]"  + ChatColor.RED + " /team help for more info!");
					return true;
				}
				
				TeamManager.getManager().joinTeam(player, args[1]);
			}
			
			if(args[0].equalsIgnoreCase("leave")) {
				
				if(TeamManager.getManager().isInTeam(player)) {
					Team team = TeamManager.getManager().getTeamByPlayer(player);
					
					if(TeamDuelManager.getManager().isInDuel(team)) {
						player.sendMessage(ChatColor.RED + "Can't leave a team while in a duel");
						return true;
					} else {
						TeamManager.getManager().leaveTeam(player);
						spawn.teleportSpawn(player);
					}
				}
			}
			
			if(args[0].equalsIgnoreCase("disband")) {
				if(TeamManager.getManager().isInTeam(player)) {
					Team team = TeamManager.getManager().getTeamByPlayer(player);
					TeamManager.getManager().disbandTeam(player);
				}
			}
			
			if(args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("k")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + " /team kick <player>");
					return true;
				}
				
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null) {
					player.sendMessage(ChatColor.RED + " Invalid player");
					return true;
				}
				
				TeamManager.getManager().kickFromTeam(player, target);
			}
			
			if(args[0].equalsIgnoreCase("leader") || args[0].equalsIgnoreCase("l")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + " /team leader <player>");
					return true;
				}
				
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null) {
					player.sendMessage(ChatColor.RED + " Invalid player");
					return true;
				}
				
				TeamManager.getManager().makeLeader(player, target);
				TeamManager.getManager().getTeamByPlayer(target).setTeamName(target.getName());
			}
			
			if(args[0].equalsIgnoreCase("bard") || args[0].equalsIgnoreCase("b")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/team bard <player>");
					return true;
				}
				
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null) {
					player.sendMessage(ChatColor.GOLD + "[Teams]"  + ChatColor.RED + " Invalid player");
					return true;
				}
				
				TeamManager.getManager().setTeamBard(player, target);
			}
			
			
			if(args[0].equalsIgnoreCase("duel") || args[0].equalsIgnoreCase("d")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.GOLD + "[Teams]"  + ChatColor.RED + " /team duel <team>");
					return true;
				}
				if(!TeamManager.getManager().isInTeam(player)) {
					player.sendMessage(ChatColor.RED + "You must be in a team to do this command");
					return true;
				}
				Team team1 = TeamManager.getManager().getTeamByPlayer(player);
				
				if(!team1.getTeamLeader().equals(player.getName())) {
					player.sendMessage(ChatColor.RED  + "Only leaders can do this command");
					return true;
				}
				
				if(TeamDuelManager.getManager().isInDuel(team1)) {
					player.sendMessage(ChatColor.RED  + "Can't duel someone while in a match already");
					return true;
				}
				
				Team team2 = TeamManager.getManager().getTeamByName(args[1]);
				
				if(!TeamManager.getManager().getAllTeams().contains(team2)) {
					player.sendMessage(ChatColor.RED + "Couldn't find that team");
					return true;
				}
				
				
				if(TeamDuelManager.getManager().isInDuel(team2)) {
					player.sendMessage(ChatColor.RED  + "That team is busy");
					return true;
				}
				
				if(team1 == team2) {
					player.sendMessage(ChatColor.RED + "Can't duel yourself");
					return true;
				}
				
				if(TeamInviteManager.getManager().hasInvited(team1, team2)) {
					player.sendMessage(ChatColor.RED + "You have already dueled that Team");
					return true;
				}
				
				invite.put(team1, team2);
				
				player.openInventory(inv.gameTypesTeam(player));
				
			}
			
			

			if(args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("a")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.GOLD + "[Teams]"  + ChatColor.RED + " /team accept <team>");
					return true;
				}
				if(!TeamManager.getManager().isInTeam(player)) {
					player.sendMessage(ChatColor.RED + "You must be in a team to do this command");
					return true;
				}
				
				Team team1 = TeamManager.getManager().getTeamByName(args[1]);
				
				if(!TeamManager.getManager().getAllTeams().contains(team1)) {
					player.sendMessage(ChatColor.RED + "Couldn't find that team");
					return true;
				}
				
				Team team2 = TeamManager.getManager().getTeamByPlayer(player);
				
				if(!team2.getTeamLeader().equals(player.getName())) {
					player.sendMessage(ChatColor.RED  + "Only leaders can do this command");
					return true;
				}
				
				if(TeamDuelManager.getManager().isInDuel(team2)) {
					player.sendMessage(ChatColor.RED  + "Can't duel someone while in a match");
					return true;
				}
					
		
				if(TeamInviteManager.getManager().hasInvited(team1, team2)) {
					GameType game = TeamInviteManager.getManager().getTeamInvite(team1, team2).getGameType();
					String type = TeamInviteManager.getManager().getTeamInvite(team1, team2).getType();
					
					if(type.equalsIgnoreCase("team")) {
						TeamDuelManager.getManager().createDuel(team1, team2, game);
					}
					
					if(type.equalsIgnoreCase("koth")) {
						TeamDuelManager.getManager().createKothDuel(team1, team2, game);
					}
					
					TeamInviteManager.getManager().clearInvites(team1);
					TeamInviteManager.getManager().clearInvites(team2);
				} else {
					player.sendMessage(ChatColor.RED + "That team has not dueled you!");
				}
				
			}
			
			if(args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {
				if(args.length < 2) {
					
					Team team = TeamManager.getManager().getTeamByPlayer(player);
					
					if(!TeamManager.getManager().getAllTeams().contains(team)) {
						player.sendMessage(ChatColor.RED + "Must be in a Team");
						return true;
					}
					
					player.sendMessage(ChatColor.AQUA.toString() + "------" + ChatColor.BOLD + team.getTeamName() + ChatColor.AQUA + "------");
					player.sendMessage("");
					for(String p : team.getTeamMembers()) {
						player.sendMessage(ChatColor.GREEN + p);
					}
					player.sendMessage("");
					return true;
				}
			
				Player target = Bukkit.getPlayer(args[1]);
				
				if(target == null) {
					
					Team team = TeamManager.getManager().getTeamByName(args[1]);
					
					if(!TeamManager.getManager().getAllTeams().contains(team)) {
						player.sendMessage(ChatColor.RED + "Couldn't find that team");
						return true;
					}
					
					player.sendMessage(ChatColor.AQUA + "Team Name: " + ChatColor.GREEN + team.getTeamName());
					
					for(String p : team.getTeamMembers()) {
						player.sendMessage(ChatColor.GREEN + p);
					}
					
				} else {
					Team team = TeamManager.getManager().getTeamByPlayer(target);
					
					if(!TeamManager.getManager().getAllTeams().contains(team)) {
						player.sendMessage(ChatColor.RED + "Couldn't find that team");
						return true;
					}
					
					player.sendMessage(ChatColor.AQUA + "Team Name: " + ChatColor.GREEN + team.getTeamName());
					
					for(String p : team.getTeamMembers()) {
						player.sendMessage(ChatColor.GREEN + p);
					}
				}
			}
			
			if(args[0].equalsIgnoreCase("end")) {
				
				if(!player.hasPermission("iranpvp.staff")) {
					return true;
				}
				
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/team end <arena> (ends the duel)");
					return true;
				}
				
				Player target = Bukkit.getPlayer(args[1]);
				
				if(target == null) {
					player.sendMessage(ChatColor.RED + "Could not find that player");
					return true;
				}
				
				TeamDuelManager.getManager().endDuel(target);
				player.sendMessage(ChatColor.RED + "Forced the Duel to Stop");
			}

		}
		
		return true;
	}
}
