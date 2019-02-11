package me.iran.iranpvp.kit;

import java.util.HashMap;
import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.duel.Duel;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.duel.team.TeamDuel;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.ffa.FFACommand;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.type.GameTypeManager;
import me.iran.iranpvp.utils.CustomInventory;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class KitEvents implements Listener {

	IranPvP plugin;
	
	public KitEvents (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	KitManager km = new KitManager();
	TeleportSpawn spawn = new TeleportSpawn();
	CustomInventory inv = new CustomInventory();
	
	private static HashMap<String, GameType> editing = new HashMap<>();
	private static HashMap<String, GameType> naming = new HashMap<>();
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if(event.getItemDrop().getItemStack().getType() == Material.ENCHANTED_BOOK) {
			event.setCancelled(true);
		}
	
		if(event.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(editing.containsKey(player.getName()) && naming.containsKey(player.getName())) {
			GameType game = naming.get(player.getName());
			
			km.createKit(player, game, event.getMessage().replace(" ", "_").toLowerCase());
			
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 10.0f, 10.0f);
			
			naming.remove(player.getName());
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		 if(event.getClickedBlock() == null) {
		    	return;
		    }

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if ((b.getType() == Material.SIGN_POST) || (b.getType() == Material.WALL_SIGN)) {
				Sign s = (Sign) b.getState();
				if (s.getLine(0).equalsIgnoreCase("[Right Click]"))
					if(editing.containsKey(player.getName()) && !naming.containsKey(player.getName())) {
						editing.remove(player.getName());
						spawn.teleportSpawn(player);
						
					} else if(editing.containsKey(player.getName()) && naming.containsKey(player.getName())) {
						player.sendMessage(ChatColor.RED + "Enter a kit name!");
						player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 10.0f, 10.0f);
					}
					
			}
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if(editing.containsKey(player.getName())) {
				Block block = event.getClickedBlock();
				if (block.getType() == Material.CHEST) {
					
					GameType game = editing.get(player.getName());

					event.setCancelled(true);
					player.openInventory(km.openChest(player, game));
					player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10.0f, 10.0f);
				}
			}
			
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if(editing.containsKey(player.getName())) {
				Block block = event.getClickedBlock();
				if (block.getType() == Material.ANVIL) {
					
					player.openInventory(inv.saveKitInventory(player));
					
					event.setCancelled(true);
					
				}
			}
			
		}
	}
	
	@EventHandler
	public void loadKits(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (event.getAction() == null) {
			return;
		}

		if (player.getItemInHand() == null) {
			return;
		}

		if (!player.getItemInHand().hasItemMeta()) {
			return;
		}

		if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
			return;
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
			
			if(player.getItemInHand().getType() == Material.ENCHANTED_BOOK) {
				if(DuelManager.getManager().isInDuel(player)) {
					Duel duel = DuelManager.getManager().getDuelByPlayer(player);
					String name = player.getItemInHand().getItemMeta().getDisplayName();
					
					km.loadKit(player, duel.getGameType(), name);
				} else if(TeamManager.getManager().isInTeam(player)) {
					Team team = TeamManager.getManager().getTeamByPlayer(player);
					
					if(TeamDuelManager.getManager().isInDuel(team)) {
						
						TeamDuel duel = TeamDuelManager.getManager().getDuelByTeam(team);
						
						String name = player.getItemInHand().getItemMeta().getDisplayName();
						km.loadKit(player, duel.getGameType(), name);
					}
				} else if(FFACommand.fighters.contains(player.getName())) {
					String name = player.getItemInHand().getItemMeta().getDisplayName();
					km.loadKit(player, GameTypeManager.getManager().getGameTypeByName(FFACommand.gameType), name);
				}

			}
		}
	}
	
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
		
		if(!event.getCurrentItem().hasItemMeta()) {
			return;
		}
		
		if(event.getCurrentItem().getItemMeta().getDisplayName() == null) {
			return;
		}
		
		if(event.getClickedInventory().getTitle().contains(ChatColor.GOLD + "Fighter")) {
			event.setCancelled(true);
		}
		
		if(event.getClickedInventory().getTitle().equals(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Kit Options")) {
			if(editing.containsKey(player.getName())) {
				
				String option = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				
				GameType game = editing.get(player.getName());
				
				if(option.equalsIgnoreCase("cancel")) {

					event.setCancelled(true);
				
					player.closeInventory();
				
				} else if(option.equalsIgnoreCase("save")) {
						
						naming.put(player.getName(), game);
						
						player.sendMessage(ChatColor.GREEN + "Type a name for your kit in chat");
						
						player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 10.0f, 10.0f);
						
						event.setCancelled(true);
					
						player.closeInventory();
				} else if(option.equalsIgnoreCase("delete")) {
						
						event.setCancelled(true);
						player.closeInventory();
						player.openInventory(inv.deleteKit(player, game));
				}
				
			}
		}

		if(event.getClickedInventory().getTitle().equals(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Delete Kit")) {
			if(editing.containsKey(player.getName())) {
				
				String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				
				GameType game = editing.get(player.getName());
				
				km.deleteKit(player, game, name);
				event.setCancelled(true);
				player.closeInventory();
				player.openInventory(inv.deleteKit(player, game));
				player.sendMessage(ChatColor.RED + "Deleted kit " + ChatColor.YELLOW + name);
			}
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(editing.containsKey(player.getName())) {
			editing.remove(player.getName());
		}
		
		if(naming.containsKey(player.getName())) {
			naming.remove(player.getName());
		}
		
	}
	
	public static HashMap<String, GameType> getEditing() {
		return editing;
	}
	
	public static HashMap<String, GameType> getNaming() {
		return naming;
	}
}