package me.iran.iranpvp.utils;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.type.GameTypeManager;
import me.iran.iranpvp.type.Ranked;
import me.iran.iranpvp.type.RankedManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Queue {
	
	public void unrankedMatch() {
		for(GameType game : GameTypeManager.getManager().getAllGameTypes()) {
			if(game.getUnranked().size() > 1) {
				
				if(ArenaManager.getManager().getAllAvailable().size() > 0) {
					Player player1 = Bukkit.getPlayer(game.getUnranked().get(0));
					Player player2 = Bukkit.getPlayer(game.getUnranked().get(1));
					
					player1.sendMessage(ChatColor.GREEN + "Matched you up with " + ChatColor.GOLD + player2.getName() + ChatColor.GREEN + " in Queue " + ChatColor.YELLOW + game.getName());
					player2.sendMessage(ChatColor.GREEN + "Matched you up with " + ChatColor.GOLD + player1.getName() + ChatColor.GREEN + " in Queue " + ChatColor.YELLOW + game.getName());
					
					player1.getInventory().clear();
					player1.getInventory().setArmorContents(null);
					
					player2.getInventory().clear();
					player2.getInventory().setArmorContents(null);
					
					player1.getInventory().setContents(game.getInv());
					player1.getInventory().setArmorContents(game.getArmor());
					
					player2.getInventory().setContents(game.getInv());
					player2.getInventory().setArmorContents(game.getArmor());
					
					game.removeUnranked(player1.getName());
					game.removeUnranked(player2.getName());
					
					DuelManager.getManager().createDuel(player1, player2, game);
				}
				
			}
		}
	}

	public void rankedMatch() {
		for(Ranked rank : RankedManager.getManager().getAllRanked()) {
			for(Ranked r : RankedManager.getManager().getAllRanked()) {
				
				int min = rank.getElo() - 75;
				int max = rank.getElo() + 75;
				
				if(!rank.getPlayer().equals(r.getPlayer())) {
					
					if(rank.getGameType().equals(r.getGameType())) {
						
						if(r.getElo() >= min && r.getElo() <= max) {
							
							if(rank.getGameType().getRanked().contains(rank.getPlayer().getName()) && rank.getGameType().getRanked().contains(r.getPlayer().getName())) {
								
								if(ArenaManager.getManager().getAllAvailable().size() > 0) {
									rank.getGameType().getRanked().remove(rank.getPlayer().getName());
									r.getGameType().getRanked().remove(r.getPlayer().getName());
									
									rank.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MATCH_FOUND").replace("%opponentelo%", "" + r.getElo()).replace("%opponent%", r.getPlayer().getName())));
									r.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MATCH_FOUND").replace("%opponentelo%", "" + rank.getElo()).replace("%opponent%", rank.getPlayer().getName())));
									
									DuelManager.getManager().createRankedDuel(rank.getPlayer(), r.getPlayer(), rank.getGameType());
								}

							}
						}
					}
				}
				
			}
		}
	}
	
	public void leaveRanked(Player player) {
		for(GameType game : GameTypeManager.getManager().getAllGameTypes()) {
			if(game.getRanked().contains(player.getName())) {
				game.getRanked().remove(player.getName());
				player.sendMessage(ChatColor.RED + "Left Ranked queue for GameType " + ChatColor.GOLD + game.getName());
			}
		}
	}
	
	public void leaveUnranked(Player player) {
		for(GameType game : GameTypeManager.getManager().getAllGameTypes()) {
			if(game.getUnranked().contains(player.getName())) {
				game.removeUnranked(player.getName());
				player.sendMessage(ChatColor.RED + "Left Unranked queue for GameType " + ChatColor.GOLD + game.getName());
			}
		}
	}
	
	public boolean isInUnranked(Player player) {
		for(GameType game : GameTypeManager.getManager().getAllGameTypes()) {
			if(game.getUnranked().contains(player.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean isInRanked(Player player) {
		for(GameType game : GameTypeManager.getManager().getAllGameTypes()) {
			if(game.getRanked().contains(player.getName())) {
				return true;
			}
		}
		
		return false;
	}
}
