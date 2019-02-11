package me.iran.iranpvp.teams;

import java.util.ArrayList;
import java.util.List;

import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.duel.team.TeamInviteManager;
import me.iran.iranpvp.ffa.FFACommand;
import me.iran.iranpvp.kit.bard.BardClass;
import me.iran.iranpvp.kit.bard.BardManager;
import me.iran.iranpvp.utils.BungeeChat;
import me.iran.iranpvp.utils.OnJoinItems;
import me.iran.iranpvp.utils.Queue;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamManager {
	static ArrayList<Team> teams= new ArrayList<Team>();
	
	private OnJoinItems items = new OnJoinItems();
	private BungeeChat click = new BungeeChat();
	private TeleportSpawn spawn = new TeleportSpawn();
	private Queue queue = new Queue();
	
	private static TeamManager tm;
	private TeamManager() {}

	public static TeamManager getManager() {
		if (tm == null)
			tm = new TeamManager();

		return tm;
	}
	
	public void createTeam(String name, Player leader) {
		
		if(isInTeam(leader)) {
			leader.sendMessage("You are already in a team");
			return;
		}
		
		if(teams.contains(getTeamByName(name))) {
			leader.sendMessage(ChatColor.RED + "That team already exists");
			return;
		}
		
		Team team = new Team(name, leader.getName());
		
		leader.sendMessage(ChatColor.BLUE + "You have created a Team!");
		teams.add(team);
		items.teamLeaderItems(leader);
	}
	
	@SuppressWarnings("deprecation")
	public void joinTeam(Player player, String name) {
		Team team = getTeamByName(name); //Joining Team
		Team playerTeam = getTeamByPlayer(player); //Current team
		
		//is other team valid
		if(!teams.contains(team)) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " That Team doesn't exist!");
			return;
		}
		
		if(playerTeam == team) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You can't join your own Team");
			return;
		}
		
		//Does the player have a team
		if(teams.contains(playerTeam)) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You must leave your Current Team to join another!");
			return;
		}
		//Is he invited
		if(!team.getInvites().contains(player.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You have not been invited to join this Team");
			return;
		}
		
		if(team.getTeamMembers().size() >= 30) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " That Team has reached its Maximum number of Players!");
			return;
		}
		
		if(DuelManager.getManager().isInDuel(player)) {
			player.sendMessage(ChatColor.RED + "Can't join a team while in a fight");
			return;
		}
		
		if(FFACommand.fighters.contains(player.getName())) {
			player.sendMessage(ChatColor.RED + "Can't join a team while in a FFA");
			return;
		}
		
		if(queue.isInRanked(player)) {
			player.sendMessage(ChatColor.RED + "Can't join a team while in Ranked queue");
			return;
		}
		
		if(queue.isInUnranked(player)) {
			player.sendMessage(ChatColor.RED + "Can't join a team while in Unranked queue");
			return;
		}
		
		team.getInvites().remove(player.getName());
		team.getTeamMembers().add(player.getName());
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team.getTeamMembers().contains(p.getName())) {
				p.sendMessage(ChatColor.GREEN + player.getName() + " has joined the Team");
			}
		}
		items.teamItems(player);
	}
	
	@SuppressWarnings("deprecation")
	public void inviteToTeam(Player player, Player target) {
		Team team = getTeamByPlayer(player);
		
		if(!teams.contains(team)) {
			player.sendMessage(ChatColor.RED + "You are not in a Team");
			return;
		}
		
		if(!team.getTeamLeader().equals(player.getName())) {
			player.sendMessage(ChatColor.RED + "Only leaders can do that Command");
			return;
		}
		
		if(team.getTeamMembers().contains(target.getName())) {
			player.sendMessage(ChatColor.RED + "That person is already in your Team");
			return;
		}
		
		if(team.getInvites().contains(target.getName())) {
			player.sendMessage(ChatColor.RED + "That person has already been invited to your Team");
			return;
		}
		
		if(TeamDuelManager.getManager().isInDuel(team)) {
			player.sendMessage(ChatColor.RED + "Can't do that while in a duel");
			return;
		}
		
		if(queue.isInRanked(player)) {
			player.sendMessage(ChatColor.RED + "That person is buy at the moment");
			return;
		}
		
		if(queue.isInUnranked(player)) {
			player.sendMessage(ChatColor.RED + "That person is buy at the moment");
			return;
		}
		
		team.addInvite(target.getName());
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team.getTeamMembers().contains(p.getName())) {
				p.sendMessage(ChatColor.YELLOW + player.getName() + " has invited " + target.getName() + " to join the Team!");
			}
		}
		click.joinTeam(player, target);

	}
	
	@SuppressWarnings("deprecation")
	public void unInvite(Player player, Player target) {
		
		Team team = getTeamByPlayer(player);
		
		if(!teams.contains(team)) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You are not in a Team");
			return;
		}
		
		if(!team.getTeamLeader().equals(player.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " Only Team Leaders can do this command");
			return;
		}
		
		if(team.getTeamMembers().contains(target.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " That person is already in your Team");
			return;
		}
		
		if(!team.getInvites().contains(target.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " That person has not been invited to your Team");
			return;
		}
		
		team.getInvites().remove(target.getName());
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team.getTeamMembers().contains(p.getName())) {
				p.sendMessage(ChatColor.YELLOW + player.getName() + " has revoked access for " + target.getName() + " to join the Team!");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setTeamBard(Player player, Player target) {
		Team team = getTeamByPlayer(player);
		
		
		if(!teams.contains(team)) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You are not in a Team");
			return;
		}
		
		if(!team.getTeamLeader().equals(player.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " Only Team Leaders can do this command");
			return;
		}
		
		if(!team.getTeamMembers().contains(target.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " That person is not in your Team");
			return;
		}
		
		if(team.getBard().size() != 3 && team.getBard().size() < 4) {
			
			
			if(!team.getBard().contains(target.getName())) {
				team.getBard().add(target.getName());
				
				BardManager.getManager().createBard(player);
				
				BardClass bard = BardManager.getManager().getBardByName(player);
				
				System.out.println(bard.getEnerg());
				System.out.println(bard.getPlayer());
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(team.getTeamMembers().contains(p.getName())) {
						p.sendMessage(ChatColor.YELLOW + target.getName() + " has been made a Team Bard");
					}
				}
				
			} else {
				team.getBard().remove(target.getName());
				
				BardManager.getManager().removeBard(player);
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(team.getTeamMembers().contains(p.getName())) {
						p.sendMessage(ChatColor.RED + target.getName() + " is not longer Bard");
					}
				}
			}
			
		} else {
			player.sendMessage(ChatColor.RED + "Can't make anymore people bard");
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void kickFromTeam(Player player, Player target) {
		Team team = getTeamByPlayer(player);
		
		if(!teams.contains(team)) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You are not in a Team");
			return;
		}
		
		if(!team.getTeamLeader().equals(player.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " Only Team Leaders can do this command");
			return;
		}
		
		if(!team.getTeamMembers().contains(target.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " That person is not in your Team");
			return;
		}
		
		if(team.getTeamLeader().equals(target.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You can't kick your Leader from the Team");
			return;
		}
		
		if(team.getBard().contains(target.getName())) {
			team.getBard().remove(target.getName());
		}
		
		team.getTeamMembers().remove(target.getName());
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team.getTeamMembers().contains(p.getName())) {
				p.sendMessage(ChatColor.RED + player.getName() + " has kicked " + target.getName() + " from the Team!");
			}
		}
		target.sendMessage(ChatColor.RED + "You have been Kicked to from the team " + team.getTeamName());
		items.onJoin(target);
	}
	
	@SuppressWarnings("deprecation")
	public void makeLeader(Player player, Player target) {
		Team team = getTeamByPlayer(player);
		
		if(!teams.contains(team)) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You are not in a Team");
			return;
		}
		
		if(!team.getTeamLeader().equals(player.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You are not the Team Leader");
			return;
		}
		
		if(!team.getTeamMembers().contains(target.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " That person is not in your Team");
			return;
		}
		
		team.setTeamLeader(target.getName());
		team.setTeamName(target.getName());
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team.getTeamMembers().contains(p.getName())) {
				p.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.UNDERLINE + player.getName() + " has made " + target.getName() + " the new Team Leader!");
			}
		}
		
		TeamInviteManager.getManager().clearInvites(team);
		
		items.teamItems(player);
		items.teamLeaderItems(target);
	}
	
	@SuppressWarnings("deprecation")
	public void makeLeaderInFight(Player player, Player target) {
		Team team = getTeamByPlayer(player);
		
		if(!teams.contains(team)) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You are not in a Team");
			return;
		}
		
		if(!team.getTeamLeader().equals(player.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You are not the Team Leader");
			return;
		}
		
		if(!team.getTeamMembers().contains(target.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " That person is not in your Team");
			return;
		}
		
		TeamInviteManager.getManager().clearInvites(team);
		team.setTeamLeader(target.getName());
		team.setTeamName(target.getName());
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team.getTeamMembers().contains(p.getName())) {
				p.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.UNDERLINE + player.getName() + " has made " + target.getName() + " the new Team Leader!");
			}
		}
	}
	
	public Team getTeamByName(String name) {
		for(Team team : teams) {
			if(team.getTeamName().equalsIgnoreCase(name)) {
				return team;
			}
		}
		return null;
	}
	
	
	public Team getTeamByPlayer(Player player) {
		for(Team team : teams) {
			if(team.getTeamMembers().contains(player.getName())) {
				return team;
			}
		}
		return null;
	}
	
	public boolean isInTeam(Player player) {
		for(Team team : teams) {
			if(team.getTeamMembers().contains(player.getName())) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void leaveTeam(Player player) {
		Team team = getTeamByPlayer(player);
		
		if(!teams.contains(team)) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You are not in a team");
			return;
		}
		
		if(team.getTeamMembers().size() == 1) {
/*			TeamInviteManager.getManager().clearInvites(team);
			team.getTeamMembers().clear();
			teams.remove(team);
			items.onJoin(player);
			player.sendMessage(ChatColor.RED + "You have disbaned your team");*/
			
			disbandTeam(player);
			return;
		}
		
		if(team.getTeamLeader().equals(player.getName())) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You must make someone the leader before leaving or /team disband");
			return;
		}
		
		if(TeamDuelManager.getManager().isInDuel(team)) {
			player.sendMessage(ChatColor.RED + "Can't leave a team while in a duel");
			return;
		}
		
		if(team.getTeamMembers().contains(player.getName())) {
			team.getTeamMembers().remove(player.getName());
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(team.getTeamMembers().contains(p.getName())) {
					p.sendMessage(ChatColor.RED + player.getName() + " Has left the Team!");
				}
			}
			
			player.sendMessage(ChatColor.RED + "You have left the Team");
			items.onJoin(player);
			return;
		}
		
		if(team.getBard().contains(player.getName())) {
			team.getBard().remove(player.getName());
			
			BardManager.getManager().removeBard(player);
		}
		
		if(team.getTeamMembers().contains(player.getName())) {
			team.getTeamMembers().remove(player.getName());
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(team.getTeamMembers().contains(p.getName())) {
					p.sendMessage(ChatColor.RED + player.getName() + " Has left the Team!");
				}
			}
			player.sendMessage(ChatColor.RED + "You have left the Team");
			items.onJoin(player);
			return;
		}
		
	}
	
	public void disbandTeam(Player player) {
		Team team = getTeamByPlayer(player);
		
		if(!teams.contains(team)) {
			player.sendMessage(ChatColor.GOLD + "[Teams]" + ChatColor.RED + " You are not in a team");
			return;
		}
		
		if(TeamDuelManager.getManager().isInDuel(team)) {
			player.sendMessage(ChatColor.RED + "Can't disband your team while in a duel");
			return;
		}
		
		if(team.getTeamLeader().equals(player.getName())) {
			
			for(String p : team.getTeamMembers()) {
				Player pl = Bukkit.getPlayer(p);
				spawn.teleportSpawn(pl);
				items.onJoin(pl);
				
				if(team.getBard().contains(p)) {
					BardManager.getManager().removeBard(pl);
				}
			}
			
			TeamInviteManager.getManager().clearInvites(team);
			
			team.getBard().clear();
			team.getTeamMembers().clear();
			teams.remove(team);
			player.sendMessage(ChatColor.RED + "You have disbanded your team");
		}
		
		items.onJoin(player);
	}
	
	public List<Team> getAllTeams() {
		return TeamManager.teams;
	}


}
