package me.iran.iranpvp.events.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.utils.TeleportSpawn;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class DeathInventory implements Listener {

	IranPvP plugin;
	
	public DeathInventory (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	TeleportSpawn spawn = new TeleportSpawn();
	
	static HashMap<String, ItemStack[]> inv = new HashMap<String, ItemStack[]>();
	static HashMap<String, ItemStack[]> armor = new HashMap<String, ItemStack[]>();
	static HashMap<String, List<String>> pots = new HashMap<String, List<String>>();
	static HashMap<String, Integer> viewTimer = new HashMap<String, Integer>();
	static HashMap<String, Double> playerHealth = new HashMap<String, Double>();
	
	public static Inventory deadInv(Player player) {
		Inventory inventory = Bukkit.createInventory(player, 54, "Fight");
		
		ItemStack[] playerItems = inv.get(player.getName());
		ItemStack[] playerArmor = armor.get(player.getName());
	
		ItemStack potion = new ItemStack(Material.BREWING_STAND_ITEM);
		ItemMeta potionMeta = potion.getItemMeta();
	
		ItemStack health = new ItemStack(Material.SPECKLED_MELON);
		ItemMeta healthMeta = health.getItemMeta();
		
		int potCount = 0;
		ItemStack hp = new ItemStack(Material.POTION, 1, (short) 16421);
		
		
		for(int i = 0; i < playerItems.length; i++) {
			ItemStack items = playerItems[i];
			inventory.setItem(i, items);
			
		}
		
		for(int i = 0; i < playerArmor.length; i++) {
			ItemStack items = playerArmor[i];
			inventory.setItem(i + 45, items);
		}
		
		potionMeta.setDisplayName(ChatColor.AQUA + "Active Potions");
		potionMeta.setLore(pots.get(player.getName()));
		potion.setItemMeta(potionMeta);
		
		healthMeta.setDisplayName("" + ChatColor.RED + Math.round(playerHealth.get(player.getName())));
		health.setItemMeta(healthMeta);
		
		inventory.setItem(52, health);
		inventory.setItem(53, potion);
		
		System.out.println(potCount);
		
		return inventory;
	}
	
	public static HashMap<String, Integer> getTimer() {
		return viewTimer;
	}
	
	public static HashMap<String, ItemStack[]> getInv() {
		return inv;
	}
	
	public static HashMap<String, ItemStack[]> getArmor() {
		return armor;
	}
	
	public static HashMap<String, List<String>> getPots() {
		return pots;
	}
	
	public static HashMap<String, Double> getHealth() {
		return playerHealth;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) { 
		
		if(event.getEntity().getKiller() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			viewTimer.put(player.getName(), 30);
			
			inv.put(player.getName(), player.getInventory().getContents());
			
			armor.put(player.getName(), player.getInventory().getArmorContents());
			
			pots.put(player.getName(), new ArrayList<String>());
			
			playerHealth.put(player.getName(), ((CraftPlayer) player).getHealth());
			
			for(Iterator<PotionEffect> it = player.getActivePotionEffects().iterator(); it.hasNext();) {
				
				PotionEffect potion = it.next();
				String info = ChatColor.GOLD + potion.getType().getName() + ": " + potion.getDuration();
				
				pots.get(player.getName()).add(info);
			}
		}
	}
	
}
