package me.iran.iranpvp.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.events.duel.DeathInventory;
import me.iran.iranpvp.kit.KitManager;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.type.Ranked;
import me.iran.iranpvp.type.RankedManager;
import me.iran.iranpvp.utils.BungeeChat;
import me.iran.iranpvp.utils.EnderpearlCooldown;
import me.iran.iranpvp.utils.OnJoinItems;
import me.iran.iranpvp.utils.ScoreboardUtils;
import me.iran.iranpvp.utils.TeleportSpawn;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class DuelManager {
	
	IranPvP plugin;
	
	TeleportSpawn spawn = new TeleportSpawn();
	
	OnJoinItems item = new OnJoinItems();
	
	KitManager km = new KitManager();
	
	BungeeChat chat = new BungeeChat();
	
	ScoreboardUtils sb = new ScoreboardUtils();

	private static HashMap<String, Integer> games = new HashMap<>();
	
	private static ArrayList<Duel> duels = new ArrayList<>();

	private static DuelManager dm;
	
	private DuelManager() {}
	
	
	public static DuelManager getManager() {
		if (dm == null)
			dm = new DuelManager();

		return dm;
	}
	
	public ArrayList<Duel> getAllDuels() {
		return duels;
	}
	
	@SuppressWarnings("deprecation")
	public void createDuel(Player player1, Player player2, GameType game) {

		if (player1 == null || player2 == null) {
			return;
		}

		Duel duel = new Duel(player1, player2, game);
		
		duel.setKothStatus(false);

		duels.add(duel);
		
		ArenaManager.getManager().teleportPlayers(player1, player2);

		player1.setHealth(20.0);
		player1.setFoodLevel(20);

		player2.setHealth(20.0);
		player2.setFoodLevel(20);

		player1.getInventory().clear();
		player1.getInventory().setArmorContents(null);
		
		player2.getInventory().clear();
		player2.getInventory().setArmorContents(null);
		
		km.getKits(player1, game);
		km.getKits(player2, game);
		
/*		for(Player p : Bukkit.getOnlinePlayers()) {
			IranPvP.getHider().hideEntity(player1, p);
			IranPvP.getHider().hideEntity(player2, p);
		}
		
		IranPvP.getHider().showEntity(player1, player2);
		IranPvP.getHider().showEntity(player2, player1);*/
		
		player1.showPlayer(player2);
		player2.showPlayer(player1);
	
		player1.setScoreboard(sb.duelBoard(player1));
		player2.setScoreboard(sb.duelBoard(player2));
		
		games.put(player1.getName(), IranPvP.plugin.getConfig().getInt("MATCH_TIMER"));
		games.put(player2.getName(), IranPvP.plugin.getConfig().getInt("MATCH_TIMER"));
	}
	
	@SuppressWarnings("deprecation")
	public void createRankedDuel(Player player1, Player player2, GameType game) {

		if (player1 == null || player2 == null) {
			return;
		}

		Duel duel = new Duel(player1, player2, game);
		
		duel.setKothStatus(false);

		duel.setRanked(true);
		
		duels.add(duel);
		
		ArenaManager.getManager().teleportPlayers(player1, player2);

		player1.setHealth(20.0);
		player1.setFoodLevel(20);

		player2.setHealth(20.0);
		player2.setFoodLevel(20);

		player1.getInventory().clear();
		player1.getInventory().setArmorContents(null);
		
		player2.getInventory().clear();
		player2.getInventory().setArmorContents(null);
		
		km.getKits(player1, game);
		km.getKits(player2, game);
		
		player1.showPlayer(player2);
		player2.showPlayer(player1);
		
		player1.setScoreboard(sb.duelBoard(player1));
		player2.setScoreboard(sb.duelBoard(player2));
		
		games.put(player1.getName(), IranPvP.plugin.getConfig().getInt("MATCH_TIMER"));
		games.put(player2.getName(), IranPvP.plugin.getConfig().getInt("MATCH_TIMER"));
		
	}
	
	public void endDuel(Player player) {
		Duel duel = getDuelByPlayer(player);
		
		if(duel == null) {
			System.out.println("duel is null");
			return;
		}
		
		Player player1 = duel.getPlayer1();
		Player player2 = duel.getPlayer2();
		
		if(player1 != null || player2 != null) {
			duel.setWinner(player);
		}
		
		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			if(arena.getSpec().contains(p.getName())) {
				player1.showPlayer(p);
				player2.showPlayer(p);
			}
		}
		
		if(duel.getWinner() == player1) {
			
			if(EnderpearlCooldown.getEnderpearl().containsKey(player1.getName())) {
				EnderpearlCooldown.getEnderpearl().remove(player1.getName());
			}
			
			if(EnderpearlCooldown.getEnderpearl().containsKey(player2.getName())) {
				EnderpearlCooldown.getEnderpearl().remove(player2.getName());
			}
			
			chat.viewInv(player1, player2);
			
			DeathInventory.getTimer().put(player1.getName(), 30);
			DeathInventory.getInv().put(player1.getName(), player.getInventory().getContents());
			DeathInventory.getArmor().put(player1.getName(), player.getInventory().getArmorContents());
			DeathInventory.getPots().put(player1.getName(), new ArrayList<String>());
			DeathInventory.getHealth().put(player.getName(), ((CraftPlayer) player).getHealth());
			
			for(Iterator<PotionEffect> it = player1.getActivePotionEffects().iterator(); it.hasNext();) {
				
				PotionEffect potion = it.next();
				String info = ChatColor.GOLD + potion.getType().getName() + ": " + potion.getDuration();
				
				DeathInventory.getPots().get(player1.getName()).add(info);
			}
			
			player1.setHealth(20.0);
			player1.setFoodLevel(20);
			
			for(PotionEffect effect : player1.getActivePotionEffects()) {
				player1.removePotionEffect(effect.getType());
			}
			
			for(PotionEffect effect : player2.getActivePotionEffects()) {
				player2.removePotionEffect(effect.getType());
			}
			
			spawn.teleportSpawn(player1);
			
		} else 	if(duel.getWinner() == player2) {
			
			DeathInventory.getTimer().put(player2.getName(), 30);
			DeathInventory.getInv().put(player2.getName(), player.getInventory().getContents());
			DeathInventory.getArmor().put(player2.getName(), player.getInventory().getArmorContents());
			DeathInventory.getPots().put(player2.getName(), new ArrayList<String>());
			DeathInventory.getHealth().put(player.getName(), ((CraftPlayer) player).getHealth());
			
			if(EnderpearlCooldown.getEnderpearl().containsKey(player1.getName())) {
				EnderpearlCooldown.getEnderpearl().remove(player1.getName());
			}
			
			if(EnderpearlCooldown.getEnderpearl().containsKey(player2.getName())) {
				EnderpearlCooldown.getEnderpearl().remove(player2.getName());
			}
			
			for(Iterator<PotionEffect> it = player2.getActivePotionEffects().iterator(); it.hasNext();) {
				
				PotionEffect potion = it.next();
				String info = ChatColor.GOLD + potion.getType().getName() + ": " + potion.getDuration();
				
				DeathInventory.getPots().get(player2.getName()).add(info);
			}
			
			chat.viewInv(player2, player1);
			
			player2.setHealth(20.0);
			player2.setFoodLevel(20);
	
			for(PotionEffect effect : player1.getActivePotionEffects()) {
				player1.removePotionEffect(effect.getType());
			}
			
			for(PotionEffect effect : player2.getActivePotionEffects()) {
				player2.removePotionEffect(effect.getType());
			}
			
			spawn.teleportSpawn(player2);
		}
		
		
		if(duel.getWinner() == player1) {
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(arena.getSpec().contains(p.getName())) {
					chat.viewInvSpec(player1, player2, p);
				}
			}
			
		} else if(duel.getWinner() == player2) {
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(arena.getSpec().contains(p.getName())) {
					chat.viewInvSpec(player2, player1, p);
				}
			}
			
		}
		
		player1.setFireTicks(0);
		player2.setFireTicks(0);
		
		ArenaManager.getManager().removePlayer(player);
		
		for(Location loc : duel.getBlocks()) {
			
			loc.getBlock().setType(Material.AIR);
		}
		
		duels.remove(duel);
	}

	public void endRankedDuel(Player player) {
		Duel duel = getDuelByPlayer(player);
		
		if(duel == null) {
			System.out.println("duel is null");
			return;
		}
		
		Player player1 = duel.getPlayer1();
		Player player2 = duel.getPlayer2();
		
		if(player1 != null || player2 != null) {
			duel.setWinner(player);
		}
		
		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			if(arena.getSpec().contains(p.getName())) {
				player1.showPlayer(p);
				player2.showPlayer(p);
			}
		}
		
		ArenaManager.getManager().removePlayer(player);
		
		if(duel.getWinner() == player1) {
			
			if(EnderpearlCooldown.getEnderpearl().containsKey(player1.getName())) {
				EnderpearlCooldown.getEnderpearl().remove(player1.getName());
			}
			
			if(EnderpearlCooldown.getEnderpearl().containsKey(player2.getName())) {
				EnderpearlCooldown.getEnderpearl().remove(player2.getName());
			}
			
			chat.viewInv(player1, player2);
			
			DeathInventory.getTimer().put(player1.getName(), 30);
			DeathInventory.getInv().put(player1.getName(), player.getInventory().getContents());
			DeathInventory.getArmor().put(player1.getName(), player.getInventory().getArmorContents());
			DeathInventory.getPots().put(player1.getName(), new ArrayList<String>());
			DeathInventory.getHealth().put(player.getName(), ((CraftPlayer) player).getHealth());
			
			for(Iterator<PotionEffect> it = player1.getActivePotionEffects().iterator(); it.hasNext();) {
				
				PotionEffect potion = it.next();
				String info = ChatColor.GOLD + potion.getType().getName() + ": " + potion.getDuration();
				
				DeathInventory.getPots().get(player1.getName()).add(info);
			} 
			
			player1.setHealth(20.0);
			player1.setFoodLevel(20);
			
			for(PotionEffect effect : player1.getActivePotionEffects()) {
				player1.removePotionEffect(effect.getType());
			}
			
			for(PotionEffect effect : player2.getActivePotionEffects()) {
				player2.removePotionEffect(effect.getType());
			}
			
			GameType game = duel.getGameType();
			
			Ranked rank1 = RankedManager.getManager().getRank(player1, game);
			
			Ranked rank2 = RankedManager.getManager().getRank(player2, game);
			

			double calc = 1.0 / (1.0 + Math.pow(10.0, (rank2.getElo() - rank1.getElo()) / 400.0));
			int elo = (int) Math.round(5 * (3 - calc));
			
			rank1.setElo(rank1.getElo() + elo);
			
			rank2.setElo(rank2.getElo() - elo);
			
			player1.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
			player1.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_1").replace("%gametype%", duel.getGameType().getName()).replace("%winner%", player1.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank1.getElo()))));
			player1.sendMessage("");
			player1.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_2").replace("%gametype%", duel.getGameType().getName()).replace("%loser%", player2.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank2.getElo()))));
			player1.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
			
			
			player2.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
			player2.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_1").replace("%gametype%", duel.getGameType().getName()).replace("%winner%", player1.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank1.getElo()))));
			player2.sendMessage("");
			player2.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_2").replace("%gametype%", duel.getGameType().getName()).replace("%loser%", player2.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank2.getElo()))));
			player2.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(arena.getSpec().contains(p.getName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_1").replace("%gametype%", duel.getGameType().getName()).replace("%winner%", player2.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank2.getElo()))));
					p.sendMessage("");
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_2").replace("%gametype%", duel.getGameType().getName()).replace("%loser%", player1.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank1.getElo()))));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
					
				}
			}
			
			spawn.teleportSpawn(player1);
			
		} else 	if(duel.getWinner() == player2) {
			
			DeathInventory.getTimer().put(player2.getName(), 30);
			DeathInventory.getInv().put(player2.getName(), player.getInventory().getContents());
			DeathInventory.getArmor().put(player2.getName(), player.getInventory().getArmorContents());
			DeathInventory.getPots().put(player2.getName(), new ArrayList<String>());
			DeathInventory.getHealth().put(player.getName(), ((CraftPlayer) player).getHealth());
			
			if(EnderpearlCooldown.getEnderpearl().containsKey(player1.getName())) {
				EnderpearlCooldown.getEnderpearl().remove(player1.getName());
			}
			
			if(EnderpearlCooldown.getEnderpearl().containsKey(player2.getName())) {
				EnderpearlCooldown.getEnderpearl().remove(player2.getName());
			}
			
			for(Iterator<PotionEffect> it = player2.getActivePotionEffects().iterator(); it.hasNext();) {
				
				PotionEffect potion = it.next();
				String info = ChatColor.GOLD + potion.getType().getName() + ": " + potion.getDuration();
				
				DeathInventory.getPots().get(player2.getName()).add(info);
			}
			
			chat.viewInv(player2, player1);
			
			player2.setHealth(20.0);
			player2.setFoodLevel(20);
	
			for(PotionEffect effect : player1.getActivePotionEffects()) {
				player1.removePotionEffect(effect.getType());
			}
			
			for(PotionEffect effect : player2.getActivePotionEffects()) {
				player2.removePotionEffect(effect.getType());
			}
			
			GameType game = duel.getGameType();
			
			Ranked rank1 = RankedManager.getManager().getRank(player1, game);
			
			Ranked rank2 = RankedManager.getManager().getRank(player2, game);
			
			double calc = 1.0 / (1.0 + Math.pow(10.0, (rank1.getElo() - rank2.getElo()) / 400.0));
			int elo = (int) Math.round(5 * (3 - calc));
			
			rank1.setElo(rank1.getElo() - elo);
			
			rank2.setElo(rank2.getElo() + elo);
			
			player1.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
			player1.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_1").replace("%gametype%", duel.getGameType().getName()).replace("%winner%", player2.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank2.getElo()))));
			player1.sendMessage("");
			player1.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_2").replace("%gametype%", duel.getGameType().getName()).replace("%loser%", player1.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank1.getElo()))));
			player1.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
			
			
			player2.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
			player2.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_1").replace("%gametype%", duel.getGameType().getName()).replace("%winner%", player2.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank2.getElo()))));
			player2.sendMessage("");
			player2.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_2").replace("%gametype%", duel.getGameType().getName()).replace("%loser%", player1.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank1.getElo()))));
			player2.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));

			for(Player p : Bukkit.getOnlinePlayers()) {
				if(arena.getSpec().contains(p.getName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_1").replace("%gametype%", duel.getGameType().getName()).replace("%winner%", player2.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank2.getElo()))));
					p.sendMessage("");
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.MESSAGE_2").replace("%gametype%", duel.getGameType().getName()).replace("%loser%", player1.getName()).replace("%elo%", "" + elo).replace("%newelo%", "" + (rank1.getElo()))));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ELO_MESSAGE.LINE")));
					
				}
			}
			
			spawn.teleportSpawn(player2);
		}
		
		if(duel.getWinner() == player1) {
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(arena.getSpec().contains(p.getName())) {
					chat.viewInvSpec(player1, player2, p);
				
				}
			}
			
		} else if(duel.getWinner() == player2) {
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(arena.getSpec().contains(p.getName())) {
					chat.viewInvSpec(player2, player1, p);
				}
			}
			
		}
		
		player1.setFireTicks(0);
		player2.setFireTicks(0);
		
		for(Location loc : duel.getBlocks()) {
			
			loc.getBlock().setType(Material.AIR);
		}
		
		duels.remove(duel);
	}
	
	public Duel getDuelByPlayer(Player player) {
		for(Duel duel : duels) {
			if(duel.getPlayer1() == player || duel.getPlayer2() == player) {
				return duel;
			}
		}
		
		return null;
	}
	
	public boolean isInDuel(Player player) {
		for(Duel duel : duels) {
			if(duel.getPlayer1().equals(player) || duel.getPlayer2().equals(player)) {
				return true;
			}
		}
		return false;
	}
	
	public HashMap<String, Integer> getGames() {
		return games;
	}
	
}
