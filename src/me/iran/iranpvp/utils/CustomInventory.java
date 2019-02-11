package me.iran.iranpvp.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.kit.KitManager;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.type.GameTypeManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomInventory {

	KitManager km = new KitManager();
	
	File file = null;
	
	public Inventory gameTypes(final Player player) {
		final Inventory gameTypes = Bukkit.createInventory(player, 18, ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("DUEL_INVENTORY")));
			new BukkitRunnable() {
				public void run() {
					gameTypes.clear();
						for (int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
							GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
							
							ItemStack display = game.getDisplay();
							ItemMeta displayMeta = display.getItemMeta();
							displayMeta.setDisplayName(game.getName());
							displayMeta.setLore(Arrays.asList("", ChatColor.GRAY + "(" + ChatColor.RED.toString() + ChatColor.BOLD + "Click to Duel" + ChatColor.GRAY + ")"));
							display.setItemMeta(displayMeta);
							
							gameTypes.setItem(i, display);
						}
				
				}
			}.runTaskTimer(IranPvP.plugin, 0L, 20L);
		
		return gameTypes;
	}

	public Inventory kitEditorInv(Player player) {
		Inventory gameTypes = Bukkit.createInventory(player, 18, ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("KIT_EDITOR_INVENTORY")));
		
		List<GameType> edit = new ArrayList<GameType>();
		
		for (int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {

			GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);

			if(game.getEdit()) {
				
				edit.add(game);
				
				for(int j = 0; j < edit.size(); j++) {
					
					GameType gameEdit = edit.get(j);
					
					ItemStack display = gameEdit.getDisplay();
					ItemMeta displayMeta = display.getItemMeta();
					displayMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ITEM_COLOR.KIT_EDITOR")) + gameEdit.getName());
					display.setItemMeta(displayMeta);
					gameTypes.setItem(j, display);
				
				}
			}

		}

		return gameTypes;
	}

	public Inventory deleteKit(Player player, GameType gameType) {
		Inventory gameTypes = Bukkit.createInventory(player, 18, ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Delete Kit");
		
		file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
		
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kConfig = YamlConfiguration.loadConfiguration(file);

			List<String> kits = new ArrayList<>();
		
			ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta bookMeta = book.getItemMeta();
			
			for(String name : kConfig.getConfigurationSection(gameType.getName()).getKeys(false)) {
				
				kits.add(name);
				
				if(kits.contains("name")) {
					kits.remove("name");
				}
				
				if(kits.contains("inv")) {
					kits.remove("inv");
				}
				
				if(kits.contains("armor")) {
					kits.remove("armor");
				}
				
				if(kits.contains("size")) {
					kits.remove("size");
				}
				
				for(int i = 0; i < kits.size(); i++) {
					bookMeta.setDisplayName(kits.get(i));
					book.setItemMeta(bookMeta);
					gameTypes.setItem(i, book);
				}
			}

		}
		return gameTypes;
	}
	
	public Inventory saveKitInventory(Player player) {
		Inventory save = Bukkit.createInventory(player, 27, ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Kit Options");
		
		ItemStack saveGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemStack deleteGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemStack editGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
		
		ItemMeta saveMeta = saveGlass.getItemMeta();
		ItemMeta deleteMeta = deleteGlass.getItemMeta();
		ItemMeta editMeta = editGlass.getItemMeta();
		
		saveMeta.setDisplayName(ChatColor.GREEN + "Save");
		deleteMeta.setDisplayName(ChatColor.RED + "Delete");
		editMeta.setDisplayName(ChatColor.YELLOW + "Cancel");
		
		saveGlass.setItemMeta(saveMeta);
		deleteGlass.setItemMeta(deleteMeta);
		editGlass.setItemMeta(editMeta);
		
		save.setItem(15, saveGlass);
		save.setItem(13, deleteGlass);
		save.setItem(11, editGlass);
		
		return save;
	}
	
	public Inventory gameTypesTeam(final Player player) {
		final Inventory gameTypes = Bukkit.createInventory(player, 18, ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_DUEL_INVENTORY")));
			new BukkitRunnable() {
				public void run() {
					gameTypes.clear();
						for (int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
							GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
							
							ItemStack display = game.getDisplay();
							ItemMeta displayMeta = display.getItemMeta();
							displayMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ITEM_COLOR.TEAM_DUEL")) + game.getName());
							display.setItemMeta(displayMeta);
							
							gameTypes.setItem(i, display);
						}
				
				}
			}.runTaskTimer(IranPvP.plugin, 0L, 20L);
		
		return gameTypes;
	}
	
	public Inventory modes(Player player) {
		final Inventory gameMode = Bukkit.createInventory(player, 18, ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("GAME_MODES_INVENTORY")));
		
		ItemStack teamFight = new ItemStack(Material.CHEST);
		ItemStack kothFight = new ItemStack(Material.ENDER_CHEST);
		
		ItemMeta teamMeta = teamFight.getItemMeta();
		ItemMeta kothMeta = kothFight.getItemMeta();
		
		teamMeta.setDisplayName(ChatColor.GREEN + "Team");
		
		kothMeta.setDisplayName(ChatColor.AQUA + "KOTH");
		
		teamFight.setItemMeta(teamMeta);
		kothFight.setItemMeta(kothMeta);
		
		gameMode.setItem(0, teamFight);
		gameMode.setItem(1, kothFight);
		
		return gameMode;
	}
	
	public Inventory queue(final Player player) {
		final Inventory gameTypes = Bukkit.createInventory(player, 18, ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("UNRANKED_INVENTORY")));
			new BukkitRunnable() {
				public void run() {
					gameTypes.clear();
						for (int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
							GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
							
							ItemStack display = game.getDisplay();
							ItemMeta displayMeta = display.getItemMeta();
							displayMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ITEM_COLOR.UNRANKED")) + game.getName());
							displayMeta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ITEM_LORE.UNRANKED").replace("%size%", "" + game.getUnranked().size())), ""));
							display.setItemMeta(displayMeta);
							
							gameTypes.setItem(i, display);
						}
				
				}
			}.runTaskTimer(IranPvP.plugin, 0L, 20L);
		
		return gameTypes;
	}
	
	public Inventory ranked(final Player player) {
		final Inventory gameTypes = Bukkit.createInventory(player, 18, ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("RANKED_INVENTORY")));
			new BukkitRunnable() {
				public void run() {
					gameTypes.clear();
						for (int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
							GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
							
							ItemStack display = game.getDisplay();
							ItemMeta displayMeta = display.getItemMeta();
							displayMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ITEM_COLOR.RANKED")) + game.getName());
							displayMeta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ITEM_LORE.RANKED").replace("%size%", "" + game.getRanked().size())), ""));
							display.setItemMeta(displayMeta);
							
							gameTypes.setItem(i, display);
						}
				
				}
			}.runTaskTimer(IranPvP.plugin, 0L, 20L);
		
		return gameTypes;
	}
	
	public Inventory ffa(final Player player) {
		final Inventory gameTypes = Bukkit.createInventory(player, 18, ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("FFA_INVENTORY")));
			new BukkitRunnable() {
				public void run() {
					gameTypes.clear();
						for (int i = 0; i < GameTypeManager.getManager().getAllGameTypes().size(); i++) {
							GameType game = GameTypeManager.getManager().getAllGameTypes().get(i);
							
							ItemStack display = game.getDisplay();
							ItemMeta displayMeta = display.getItemMeta();
							displayMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ITEM_COLOR.UNRANKED")) + game.getName());
							display.setItemMeta(displayMeta);
							
							gameTypes.setItem(i, display);
						}
				
				}
			}.runTaskTimer(IranPvP.plugin, 0L, 20L);
		
		return gameTypes;
	}
}
