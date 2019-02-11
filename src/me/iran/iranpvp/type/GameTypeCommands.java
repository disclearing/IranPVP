package me.iran.iranpvp.type;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.kit.KitManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class GameTypeCommands implements CommandExecutor, Listener {

	IranPvP plugin;
	
	public GameTypeCommands(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	KitManager km = new KitManager();
	
	private static HashMap<String, GameType> edit = new HashMap<String, GameType>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("gametype")) {
			
			if(!player.hasPermission("iranpvp.gametype")) {
				return true;
			}
			
			if(args.length < 1) {
				player.sendMessage(ChatColor.RED + "/gametype create <name>");
				player.sendMessage(ChatColor.RED + "/gametype setdisplay <name>");
				player.sendMessage(ChatColor.RED + "/gametype delete <name>");
				player.sendMessage(ChatColor.RED + "/gametype setkit <name>");
				player.sendMessage(ChatColor.RED + "/gametype setedit <name>");
				player.sendMessage(ChatColor.RED + "/gametype editable <name> true|false");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/gametype create <name>");
					return true;
				}
				
				GameTypeManager.getManager().createGameType(args[1], player);
				
				
			}
			
			
			if(args[0].equalsIgnoreCase("delete")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/gametype delete <name>");
					return true;
				}
				
				GameTypeManager.getManager().deleteGameType(args[1]);
				
				player.sendMessage(ChatColor.RED + "GameType " + ChatColor.GOLD + args[1] + ChatColor.RED + " Deleted Successfully");
			}
			
			if(args[0].equalsIgnoreCase("setdisplay")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/gametype setdisplay <name>");
					return true;
				}
				
				GameTypeManager.getManager().setItemDisplay(player, args[1]);
				
				player.sendMessage(ChatColor.GREEN + "Set Display Item for GameType " + ChatColor.GOLD + args[1]);
			}
			
			if(args[0].equalsIgnoreCase("setkit")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/gametype setkit <name>");
					return true;
				}
				
				GameTypeManager.getManager().setDefaultArmor(player, args[1]);

				GameTypeManager.getManager().setDefaultInv(player, args[1]);
				
				player.sendMessage(ChatColor.GREEN + "Set Default kit for GameType " + ChatColor.GOLD + args[1]);
				
			}
			
			if(args[0].equalsIgnoreCase("setedit")) {
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/gametype setedit <name>");
					return true;
				}
				
				if(GameTypeManager.getManager().getGameTypeByName(args[1]) != null) {
				
					edit.put(player.getName(), GameTypeManager.getManager().getGameTypeByName(args[1]));
					
					player.openInventory(km.openEditorChest(player, GameTypeManager.getManager().getGameTypeByName(args[1])));
					
				/*	Inventory inv = Bukkit.createInventory(player, 54, ChatColor.BLUE + "Edit Chest");
					
					player.openInventory(inv);*/
				}
				
			}
			
			if(args[0].equalsIgnoreCase("editable")) {
				if(args.length < 3) {
					player.sendMessage(ChatColor.RED + "/gametype editable <name> true|false");
					return true;
				}
				
				if(args[2].contains("true")) {
					GameTypeManager.getManager().setEdit(player, true, args[1]);
				} else if(args[2].contains("false")) {
					GameTypeManager.getManager().setEdit(player, false, args[1]);
				}
				
				
			}
		}
		
		return true;
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		
		if(event.getInventory() != null && event.getInventory().getTitle().equals(ChatColor.BLUE + "Edit Chest")) {
			
			if(event.getPlayer() instanceof Player) {
				
				Player player = (Player) event.getPlayer();
				
				if(edit.containsKey(player.getName())) {
					
					km.setChest(player, edit.get(player.getName()), event.getInventory());
					
					edit.remove(player.getName());
					player.sendMessage(ChatColor.GREEN + "Chest setup!");
				}
			}
		}
		
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		
		if(edit.containsKey(player.getName())) {
			edit.remove(player.getName());
		}
	}
	
}
