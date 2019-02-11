package me.iran.iranpvp.kit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.type.GameType;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitManager {

	File file = null;
	
	public void deleteKit(Player player, GameType gameType, String name) {
		file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
		
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kConfig = YamlConfiguration.loadConfiguration(file);
			
			kConfig.set(gameType.getName() + "." + name, null);
			kConfig.set(gameType.getName() + ".size", kConfig.getInt(gameType.getName() + ".size") - 1);
			
			try {
				kConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			if(kConfig.getInt(gameType.getName() + ".size") <= 0) {
				file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
				file.delete();
			}
			
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			
		}
	}
	
	public void createKit(Player player, GameType gameType, String name) {
		file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
		
		if(!file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kConfig = YamlConfiguration.loadConfiguration(file);
			
			//Debuff > name > items
			
			
			kConfig.createSection(gameType.getName() + "." + name + ".inv");
			kConfig.createSection(gameType.getName() + "." + name + ".armor");
			kConfig.createSection(gameType.getName() + ".size");
			kConfig.createSection(gameType.getName() + "." + name + ".name");
			
			kConfig.set(gameType.getName() + "." + name + ".inv", player.getInventory().getContents());
			kConfig.set(gameType.getName() + "." + name + ".armor", player.getInventory().getArmorContents());
			kConfig.set(gameType.getName() + ".size", 1);
			kConfig.set(gameType.getName() + "." + name + ".name", name);
			
			if(name.equalsIgnoreCase("name") || name.equalsIgnoreCase("size") || name.equalsIgnoreCase("inv") || name.equalsIgnoreCase("armor")) {
				player.sendMessage(ChatColor.RED + "Sorry you can't name your kit the following names: name, size, inv, armor");
				return;
			}
			
			try {
				kConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			player.sendMessage(ChatColor.GREEN + "Kit " + ChatColor.YELLOW + name + ChatColor.GREEN +" Created for GameType: " + ChatColor.GOLD + gameType.getName());
			
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			
		} else {
			
			file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kConfig = YamlConfiguration.loadConfiguration(file);
			
			if(kConfig.getInt(gameType.getName() + ".size") == 4) {
				player.sendMessage(ChatColor.RED + "Reached the Maximum amount of kits you can create");
				return;
			}
			
			if(kConfig.contains(gameType.getName() + "." + name)) {
				player.sendMessage(ChatColor.RED + "You already have a kit with that name");
				return;
			}
			
			if(name.equalsIgnoreCase("name") || name.equalsIgnoreCase("size") || name.equalsIgnoreCase("inv") || name.equalsIgnoreCase("armor")) {
				player.sendMessage(ChatColor.RED + "Sorry you can't name your kit the following names: name, size, inv, armor");
				return;
			}
			
			kConfig.set(gameType.getName() + "." + name + ".inv", player.getInventory().getContents());
			kConfig.set(gameType.getName() + "." + name + ".armor", player.getInventory().getArmorContents());
			kConfig.set(gameType.getName() + ".size", kConfig.getInt(gameType.getName() + ".size") + 1);
			kConfig.set(gameType.getName() + "." + name + ".name", name);
			
			try {
				kConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			player.sendMessage(ChatColor.GREEN + "Kit " + ChatColor.YELLOW + name + ChatColor.GREEN +" Created for GameType: " + ChatColor.GOLD + gameType.getName());
			
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
		}
	}

	public void getKits(Player player, GameType gameType) {
		
		file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
		
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kConfig = YamlConfiguration.loadConfiguration(file);

			List<String> kits = new ArrayList<>();
		
			ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta bookMeta = book.getItemMeta();
			
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			
			for(String name : kConfig.getConfigurationSection(gameType.getName()).getKeys(false)) { 
				
				kits.add(name.toLowerCase());
				
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
				
				if(TeamManager.getManager().isInTeam(player)) {
					
					Team team = TeamManager.getManager().getTeamByPlayer(player);
					
					if(team.getBard().contains(player.getName())) {
						
						List<String> bard = new ArrayList<String>();
						
						for(int i = 0; i < kits.size(); i++) {

							if(kits.get(i).contains("bard")) {
								bard.add(kits.get(i));
							}
						}
						
						if(bard.size() <= 0) {
							
							player.getInventory().clear();
							player.getInventory().setArmorContents(null);
							
							if(kits.size() <= 0) {
								
								player.getInventory().clear();
								player.getInventory().setArmorContents(null);
								
								player.getInventory().setContents(gameType.getInv());
								player.getInventory().setArmorContents(gameType.getArmor());
								
							} else {
								
								player.getInventory().clear();
								player.getInventory().setArmorContents(null);
								
								for(int i = 0; i < kits.size(); i++) {
									
									bookMeta.setDisplayName(kits.get(i));
									book.setItemMeta(bookMeta);
									player.getInventory().setItem(i, book);
									
								}
							}
							
						} else {
							
							player.getInventory().clear();
							player.getInventory().setArmorContents(null);
							
							for(int i = 0; i < bard.size(); i++) {
								
								bookMeta.setDisplayName(bard.get(i));
								book.setItemMeta(bookMeta);
								player.getInventory().setItem(i, book);
								
							}
						}
						
					
					} else {
						
						for(int i = 0; i < kits.size(); i++) {
							
							if(kits.get(i).contains("bard")) {
								kits.remove(kits.get(i));
							}
					
						}
						
						if(kits.size() <= 0) {
						
							player.getInventory().clear();
							player.getInventory().setArmorContents(null);
							
							player.getInventory().setContents(gameType.getInv());
							player.getInventory().setArmorContents(gameType.getArmor());
						
						} else {
							
							player.getInventory().clear();
							player.getInventory().setArmorContents(null);
							
							for(int i = 0; i < kits.size(); i++) {
								bookMeta.setDisplayName(kits.get(i));
								book.setItemMeta(bookMeta);
								player.getInventory().setItem(i, book);
							
							}
						}
					}
	
				} else {
					
					if(kits.size() <= 0) {
						
						player.getInventory().clear();
						player.getInventory().setArmorContents(null);
						
						player.getInventory().setContents(gameType.getInv());
						player.getInventory().setArmorContents(gameType.getArmor());
						
					} else {
						
						
						player.getInventory().clear();
						player.getInventory().setArmorContents(null);
						
						for(int i = 0; i < kits.size(); i++) {
							
							if(kits.get(i).contains("bard")) {
								kits.remove(kits.get(i));
							} else {
								bookMeta.setDisplayName(kits.get(i));
								book.setItemMeta(bookMeta);
								player.getInventory().setItem(i, book);
							}
						}
					}
				}
			}
		} else {
			
			
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			
			player.getInventory().setContents(gameType.getInv());
			player.getInventory().setArmorContents(gameType.getArmor());	
		}
	}
	
	public void setChest(Player player, GameType gameType, Inventory inv) {
		file = new File(IranPvP.plugin.getDataFolder(), gameType.getName() + "_chest.yml");
		
		if(!file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), gameType.getName() + "_chest.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kConfig = YamlConfiguration.loadConfiguration(file);
			
			kConfig.createSection(gameType.getName() + ".inv");
			
			
			kConfig.set(gameType.getName() + ".inv", inv.getContents());
			
			try {
				kConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			file = new File(IranPvP.plugin.getDataFolder(), gameType.getName() + "_chest.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kConfig = YamlConfiguration.loadConfiguration(file);
			
			kConfig.set(gameType.getName() + ".inv", inv.getContents());
			
			try {
				kConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		player.updateInventory();
		
	}
	
	public void loadKit(Player player, GameType gameType, String name) {
		
		file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
		
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder() + "/kits/" + player.getUniqueId().toString(), gameType.getName() + "_" + player.getUniqueId().toString() + ".yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kConfig = YamlConfiguration.loadConfiguration(file);

			ItemStack[] inv = player.getInventory().getContents();
			ItemStack[] armor = player.getInventory().getArmorContents();
			
			if(!kConfig.contains(gameType.getName() + "." + name)) {
				player.sendMessage(ChatColor.RED + "You don't own that kit");
				return;
			}
			
			List<?> items = kConfig.getList(gameType.getName() + "." + name + ".inv");
			List<?> armorItems = kConfig.getList(gameType.getName() + "." + name + ".armor");

			for(int i = 0; i < items.size(); i++) {
				inv[i] = (ItemStack) items.get(i);
			}
			for(int i = 0; i < armorItems.size(); i++) {
				armor[i] = (ItemStack) armorItems.get(i);
			}
			
			player.getInventory().clear();
			player.getInventory().setContents(inv);
			player.getInventory().setArmorContents(armor);
			player.updateInventory();
			player.sendMessage(ChatColor.GREEN + "Loaded your Kit!");
			
		} else {
			player.getInventory().setContents(gameType.getInv());
			player.getInventory().setArmorContents(gameType.getArmor());	
		}
	}
	
	public Inventory openChest(Player player, GameType gameType) {
		
		Inventory inv = Bukkit.createInventory(null, 54, gameType.getName());
		
		file = new File(IranPvP.plugin.getDataFolder(), gameType.getName() + "_chest.yml");
		
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), gameType.getName() + "_chest.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kitConfig = YamlConfiguration.loadConfiguration(file);

			if (gameType.getEdit()) {

				List<?> items = kitConfig.getList(gameType.getName() + ".inv");

				for (int i = 0; i < items.size(); i++) {
					ItemStack item = (ItemStack) items.get(i);
					inv.setItem(i, item);
				}
			}
		}
		
		return inv;
	}

	public Inventory openEditorChest(Player player, GameType gameType) {
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Edit Chest");
		
		file = new File(IranPvP.plugin.getDataFolder(), gameType.getName() + "_chest.yml");
		
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), gameType.getName() + "_chest.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration kitConfig = YamlConfiguration.loadConfiguration(file);

			if (gameType.getEdit()) {

				List<?> items = kitConfig.getList(gameType.getName() + ".inv");

				for (int i = 0; i < items.size(); i++) {
					ItemStack item = (ItemStack) items.get(i);
					inv.setItem(i, item);
				}
			}
		} 
		
		return inv;
	}
}