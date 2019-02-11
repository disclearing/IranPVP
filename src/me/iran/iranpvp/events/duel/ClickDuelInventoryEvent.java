package me.iran.iranpvp.events.duel;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.cmd.pvp.PvPCommands;
import me.iran.iranpvp.duel.InvitePlayerManager;
import me.iran.iranpvp.ffa.FFACommand;
import me.iran.iranpvp.kit.KitEvents;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.type.GameTypeManager;
import me.iran.iranpvp.utils.BungeeChat;
import me.iran.iranpvp.utils.CustomInventory;
import me.iran.iranpvp.utils.OnJoinItems;
import me.iran.iranpvp.utils.ScoreboardUtils;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickDuelInventoryEvent implements Listener {

	IranPvP plugin;
	
	public ClickDuelInventoryEvent (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	OnJoinItems items = new OnJoinItems();
	CustomInventory inv = new CustomInventory();
	BungeeChat chat = new BungeeChat();
	TeleportSpawn spawn = new TeleportSpawn();
	
	ScoreboardUtils sb = new ScoreboardUtils();
	
	public static HashMap<String, GameType> duelGameType = new HashMap<>();

	
	@EventHandler
	public void onClickEvent(InventoryClickEvent event) {
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

		if(event.getInventory().getTitle().equals("Fight")) {
			event.setCancelled(true);
		}
		
		if(!event.getCurrentItem().hasItemMeta()) {
			return;
		}
		
		if(event.getCurrentItem().getItemMeta().getDisplayName() == null) {
			return;
		}
		
		if(event.getClickedInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("DUEL_INVENTORY")))) {
			if(PvPCommands.invite.containsKey(player.getName())) {

				Player target = Bukkit.getPlayer(PvPCommands.invite.get(player.getName()));
				
				String game = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				
				InvitePlayerManager.getManager().createInvite(player, target, GameTypeManager.getManager().getGameTypeByName(game));
				
				GameType gameType = GameTypeManager.getManager().getGameTypeByName(game);
				
				duelGameType.put(player.getName(), gameType);
				duelGameType.put(target.getName(), gameType);
				
				player.closeInventory();
				event.setCancelled(true);
				
				player.sendMessage(ChatColor.GREEN + "Your duel has been sent");
			}
		}
		
		if(event.getClickedInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("RANKED_INVENTORY")))) {
			String game = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			
			GameTypeManager.getManager().joinRanked(player, game);
			
			items.rankedQueue(player);
			
			sb.add(player, player.getScoreboard(), ChatColor.BLUE, "ranked", game, "Ranked: ");
			
			player.closeInventory();
			event.setCancelled(true);
		}
		
		if(event.getClickedInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("UNRANKED_INVENTORY")))) {
			String game = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				
				GameTypeManager.getManager().joinUnranked(player, game);
				
				items.unrankedQueue(player);
				
				sb.add(player, player.getScoreboard(), ChatColor.BLUE, "unranked", game, "Unranked: ");
				
				player.closeInventory();
				event.setCancelled(true);
		}
		
		if(event.getClickedInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("KIT_EDITOR_INVENTORY")))) {
			String game = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			
			KitEvents.getEditing().put(player.getName(), GameTypeManager.getManager().getGameTypeByName(game));
			player.closeInventory();
			
			spawn.teleportKit(player);
			
			player.sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "*");
			player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Make sure you have a Diamond Kit if you are the Bard, Only the kits you create will be given to you");
			player.sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "*");
			
			event.setCancelled(true);
		}
		
		if(event.getClickedInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("FFA_INVENTORY")))) {
			String game = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			
			FFACommand.enabled = true;
			FFACommand.gameType = game;
			
			Bukkit.broadcastMessage(ChatColor.GOLD + "FFA Event Started! " + ChatColor.RED.toString() + ChatColor.BOLD + "/join" + ChatColor.GOLD + " (60 Seconds)");
			
			player.closeInventory();
			
			event.setCancelled(true);
		}
		
		if(event.getClickedInventory().getTitle().equals(ChatColor.AQUA + "All Teams")) {
			event.setCancelled(true);
		}
	}

	/**
	 * Cant use the following colors
	 * WHITE, BLACK, AQUA, RESET, LIGHT_PURPLE
	 * 
	 * Cant use the following team names
	 * online, top, bottom, queue, white
	 */
	
	
}
