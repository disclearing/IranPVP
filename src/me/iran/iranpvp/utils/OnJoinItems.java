package me.iran.iranpvp.utils;

import me.iran.iranpvp.IranPvP;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OnJoinItems {

	public void onJoin(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		ItemStack skull = new ItemStack(Material.NAME_TAG);
		ItemStack kit = new ItemStack(Material.BOOK);
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		ItemStack gold = new ItemStack(Material.DIAMOND_SWORD);
		
		ItemMeta kitMeta = kit.getItemMeta();
		ItemMeta skullMeta = skull.getItemMeta();
		ItemMeta swordMeta = sword.getItemMeta();
		ItemMeta goldMeta = gold.getItemMeta();
		
		skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("CREATE_TEAM")));
		kitMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("KIT_EDITOR")));
		swordMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("UNRANKED_SWORD")));
		goldMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("RANKED_SWORD")));
		
		kit.setItemMeta(kitMeta);
		skull.setItemMeta(skullMeta);
		sword.setItemMeta(swordMeta);
		gold.setItemMeta(goldMeta);
		
		player.getInventory().setItem(0, kit);
		player.getInventory().setItem(4, skull);
		player.getInventory().setItem(7, sword);
		player.getInventory().setItem(8, gold);
		
		player.updateInventory();
	}
	
	public void teamItems(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		ItemStack book = new ItemStack(Material.PAPER);
		ItemStack fire = new ItemStack(Material.FLINT_AND_STEEL);
		ItemStack bookshelf = new ItemStack(Material.BOOKSHELF);
		
		ItemMeta bookMeta = book.getItemMeta();
		ItemMeta fireMeta = fire.getItemMeta();
		ItemMeta shelfMeta = bookshelf.getItemMeta();
		
		bookMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_INFO")));
		fireMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("LEAVE_TEAM")));
		shelfMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ALL_TEAMS")));
		
		book.setItemMeta(bookMeta);
		fire.setItemMeta(fireMeta);
		bookshelf.setItemMeta(shelfMeta);
		
		player.getInventory().setItem(0, book);
		player.getInventory().setItem(4, fire);
		player.getInventory().setItem(8, bookshelf);
		player.updateInventory();
	}
	
	public void teamLeaderItems(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		ItemStack book = new ItemStack(Material.PAPER);
		ItemStack bookshelf = new ItemStack(Material.BOOKSHELF);
		ItemStack fire = new ItemStack(Material.FLINT_AND_STEEL);
		
		ItemMeta bookMeta = book.getItemMeta();
		ItemMeta shelfMeta = bookshelf.getItemMeta();
		ItemMeta fireMeta = fire.getItemMeta();
		
		
		bookMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_INFO")));
		shelfMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ALL_TEAMS")));
		fireMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("DISBAND_TEAM")));
		
		book.setItemMeta(bookMeta);
		bookshelf.setItemMeta(shelfMeta);
		fire.setItemMeta(fireMeta);
		
		player.getInventory().setItem(0, book);
		player.getInventory().setItem(4, fire);
		player.getInventory().setItem(8, bookshelf);
		player.updateInventory();
	}
	
	public void unrankedQueue(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		ItemStack red = new ItemStack(Material.REDSTONE);
		ItemMeta redMeta = red.getItemMeta();
		
		redMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Leave Unranked queue");
		red.setItemMeta(redMeta);
		
		player.getInventory().setItem(8, red);
		player.updateInventory();
	}
	
	public void rankedQueue(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		ItemStack red = new ItemStack(Material.REDSTONE);
		ItemMeta redMeta = red.getItemMeta();
		
		redMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Leave Ranked queue");
		red.setItemMeta(redMeta);
		
		player.getInventory().setItem(8, red);
		player.updateInventory();
	}
	
	public void clearInv(Player player) {
		
		for(int i = 0; i < player.getInventory().getContents().length; i++) {
			player.getInventory().setItem(i, new ItemStack(Material.AIR));
			player.updateInventory();
		}
		
		player.getInventory().setArmorContents(null);
	}
	
	public void spectatorMode(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		ItemStack eye = new ItemStack(Material.REDSTONE_TORCH_ON);
		ItemMeta eyeMeta = eye.getItemMeta();
		
		eyeMeta.setDisplayName(ChatColor.RED + "Leave Spectator");
		eye.setItemMeta(eyeMeta);
		
		player.getInventory().setItem(0, eye);
		
	}
	
}
