package me.iran.iranpvp.events.duel;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.cmd.team.TeamCommands;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.duel.team.TeamInviteManager;
import me.iran.iranpvp.kit.KitEvents;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.type.GameTypeManager;
import me.iran.iranpvp.utils.BungeeChat;
import me.iran.iranpvp.utils.CustomInventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TeamClickInventoryEvent implements Listener {

	IranPvP plugin;
	
	public TeamClickInventoryEvent (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	public static HashMap<Team, GameType> teamGameType = new HashMap<>(); 
	
	CustomInventory inv = new CustomInventory();
	BungeeChat chat = new BungeeChat();
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if(event.getClickedInventory() == null) {
			return;
		}
		
		if(event.getClickedInventory().getTitle() == null) {
			return;
		}
		
		if(event.getCurrentItem() == null) {
			return;
		}
		
		if(!event.getCurrentItem().hasItemMeta()) {
			return;
		}
		
		if(event.getCurrentItem().getItemMeta().getDisplayName() == null) {
			return;
		}
		
		if(!DuelManager.getManager().isInDuel(player) && !KitEvents.getEditing().containsKey(player.getName()) && !KitEvents.getNaming().containsKey(player.getName())) {
			
			event.setCancelled(true);
		}
		
		if (event.getClickedInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_DUEL_INVENTORY")))) {

			String game = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

			GameType gameType = GameTypeManager.getManager().getGameTypeByName(game);

			teamGameType.put(TeamManager.getManager().getTeamByPlayer(player), gameType);

			player.closeInventory();
			event.setCancelled(true);
			player.openInventory(inv.modes(player));

		}
		
		if (event.getClickedInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("GAME_MODES_INVENTORY")))) {
			
			
			if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("Team")) {
				
				Team team1 = TeamManager.getManager().getTeamByPlayer(player);
				
				Team team2 = TeamCommands.invite.get(team1); 
				
				GameType game = teamGameType.get(team1);
				
				TeamInviteManager.getManager().createInvite(team1, team2, game, "team");
				
				Player player1 = Bukkit.getPlayer(team1.getTeamLeader());
				Player player2 = Bukkit.getPlayer(team2.getTeamLeader());
				
				chat.acceptTeamDuel(player1, player2, game.getName());
				
				player.sendMessage(ChatColor.GREEN + "Team Duel Sent");
				
				event.setCancelled(true);
				
				player.closeInventory();
				
			} else if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("KOTH")) {

				Team team1 = TeamManager.getManager().getTeamByPlayer(player);
				
				Team team2 = TeamCommands.invite.get(team1); 
				
				GameType game = teamGameType.get(team1);
				
				TeamInviteManager.getManager().createInvite(team1, team2, game, "koth");
				
				Player player1 = Bukkit.getPlayer(team1.getTeamLeader());
				Player player2 = Bukkit.getPlayer(team2.getTeamLeader());
				
				chat.acceptKothDuel(player1, player2, game.getName());
				
				player.sendMessage(ChatColor.GREEN + "KOTH Duel Sent");
				
				event.setCancelled(true);
				
				player.closeInventory();
			}
		}
	}
}
