package me.iran.iranpvp.type;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iran.iranpvp.IranPvP;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameTypeManager {

	private static ArrayList<GameType> gameType = new ArrayList<>();

	File file = null;
	
	//Pick GameType
	//Player put in Queue for specific gametype
	//Queue picks 2 random people with the same gametype and they fight
	
	
	//GameType inv and armor are defaults set by admin
	
	private static GameTypeManager gm;

	private GameTypeManager() {}

	public static GameTypeManager getManager() {
		if (gm == null)
			gm = new GameTypeManager();

		return gm;
	}

	public void loadGameTypes() {
		file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");
		
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration gConfig = YamlConfiguration.loadConfiguration(file);
			
			for(String s : gConfig.getConfigurationSection("gametype").getKeys(false)) {
				String name = gConfig.getString("gametype." + s + ".name");
				
				List<?> items = gConfig.getList("gametype." + s + ".inv");
				List<?> armor = gConfig.getList("gametype." + s + ".armor");
				
				ItemStack[] arrayItem = items.toArray(new ItemStack[items.size()]);
				ItemStack[] arrayArmor = armor.toArray(new ItemStack[armor.size()]);
				
				ItemStack display = (ItemStack) gConfig.get("gametype." + s + ".display");
				
				boolean edit = gConfig.getBoolean("gametype." + s + ".edit");
				
				GameType game = new GameType(name, arrayItem, arrayArmor, display, edit);
				
				gameType.add(game);
				
			}
		}
	}
	
	public void saveGameType() {
		file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");
		
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration gConfig = YamlConfiguration.loadConfiguration(file);
			
			ArrayList<ItemStack> invList = new ArrayList<ItemStack>();
			ArrayList<ItemStack> armorList = new ArrayList<ItemStack>();
			
			for(int i = 0; i < gameType.size(); i++) {
				String name = gameType.get(i).getName();
				
				GameType game = getGameTypeByName(name);
				
				ItemStack[] inv = game.getInv();
				ItemStack[] armor = game.getArmor();
				
				for(int j = 0; j < inv.length; j++) {
					ItemStack item = inv[j];
					if(item != null) {
						invList.add(item);
					}
				}
				
				for(int k = 0; k < armor.length; k++) {
					ItemStack arm = armor[k];
					if(arm != null) {
						armorList.add(arm);
					}
				}
				
				gConfig.set("gametype." + name + ".name", name);
				gConfig.set("gametype." + name + ".inv", invList);
				gConfig.set("gametype." + name + ".armor", armorList);
				gConfig.set("gametype." + name + ".display", game.getDisplay());
				gConfig.set("gametype." + name + ".edit", game.getEdit());
				
				try {
					gConfig.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void deleteGameType(String name) {
		file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

		if (file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

			new YamlConfiguration();

			YamlConfiguration gConfig = YamlConfiguration
					.loadConfiguration(file);

			gConfig.set("gametype." + name, null);

			try {
				gConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createGameType(String name, Player player) {

		if(gameType.contains(getGameTypeByName(name))) {
			player.sendMessage(ChatColor.RED + "That gametype already exists");
			return;
		}
		
		GameType game = new GameType(name, player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getItemInHand(), true);
		
		gameType.add(game);
		
		file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");
		
		if(file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration gConfig = YamlConfiguration.loadConfiguration(file);
			
			gConfig.createSection("gametype." + name + ".name");
			gConfig.createSection("gametype." + name + ".inv");
			gConfig.createSection("gametype." + name + ".armor");
			gConfig.createSection("gametype." + name + ".display");
			gConfig.createSection("gametype." + name + ".edit");
			
			ArrayList<ItemStack> invList = new ArrayList<ItemStack>();
			ArrayList<ItemStack> armorList = new ArrayList<ItemStack>();
			
			ItemStack[] inv = game.getInv();
			ItemStack[] armor = game.getArmor();
			
			for(int i = 0; i < inv.length; i++) {
				ItemStack item = inv[i];
				if(item != null) {
					invList.add(item);
				}
			}
			for(int i = 0; i < armor.length; i++) {
				ItemStack arm = armor[i];
				if(arm != null) {
					armorList.add(arm);
				}
			}
			
			gConfig.set("gametype." + name + ".name", name);
			gConfig.set("gametype." + name + ".inv", invList);
			gConfig.set("gametype." + name + ".armor", armorList);
			gConfig.set("gametype." + name + ".display", game.getDisplay());
			gConfig.set("gametype." + name + ".edit", game.getEdit());
			
			try {
				gConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			player.sendMessage(ChatColor.GREEN + "GameType " + ChatColor.GOLD + name + ChatColor.GREEN + " Created Successfully");
		}
	}
	
	public void setItemDisplay(Player player, String name) {
		GameType game = getGameTypeByName(name);
		
		if(!getAllGameTypes().contains(game)) {
			System.out.println("game was null");
			return;
		}

		game.setDisplay(player.getItemInHand());

		file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

		if (file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

			new YamlConfiguration();

			YamlConfiguration gConfig = YamlConfiguration.loadConfiguration(file);

			gConfig.set("gametype." + name + ".display", game.getDisplay());

			try {
				gConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Couldn't Find this GameType in the File.");
		}
	}
	
	public void setDefaultInv(Player player, String name) {
		GameType game = getGameTypeByName(name);
		
		if(!getAllGameTypes().contains(game)) {
			System.out.println("game was null");
			return;
		}
		
		game.setInv(player.getInventory().getContents());

		file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

		if (file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

			new YamlConfiguration();

			YamlConfiguration gConfig = YamlConfiguration.loadConfiguration(file);

			ArrayList<ItemStack> invList = new ArrayList<ItemStack>();

			ItemStack[] inv = game.getInv();

			for (int j = 0; j < inv.length; j++) {
				ItemStack item = inv[j];
				if (item != null) {
					invList.add(item);
				}
			}

			gConfig.set("gametype." + name + ".inv", invList);

			try {
				gConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Couldn't Find this GameType in the File.");
		}
	}
	
	public void setDefaultArmor(Player player, String name) {
		GameType game = getGameTypeByName(name);
		
		if(!getAllGameTypes().contains(game)) {
			System.out.println("game was null");
			return;
		}
		
		game.setArmor(player.getInventory().getArmorContents());
		
		file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

		if (file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

			new YamlConfiguration();

			YamlConfiguration gConfig = YamlConfiguration.loadConfiguration(file);

			ArrayList<ItemStack> armorList = new ArrayList<ItemStack>();

			ItemStack[] armor = game.getArmor();

			for (int j = 0; j < armor.length; j++) {
				ItemStack item = armor[j];
				if (item != null) {
					armorList.add(item);
				}
			}

			gConfig.set("gametype." + name + ".armor", armorList);

			try {
				gConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Couldn't Find this GameType in the File.");
		}
	}
	
	public void setEdit(Player player, boolean edit, String name) {
		GameType game = getGameTypeByName(name);
		
		if(!getAllGameTypes().contains(game)) {
			System.out.println("game was null");
			return;
		}
		
		game.setEdit(edit);

		file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

		if (file.exists()) {
			file = new File(IranPvP.plugin.getDataFolder(), "gametype.yml");

			new YamlConfiguration();

			YamlConfiguration gConfig = YamlConfiguration.loadConfiguration(file);


			gConfig.set("gametype." + name + ".edit", game.getEdit());

			try {
				gConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			player.sendMessage(ChatColor.GREEN + game.getName() + " was set to " + edit);
		} else {
			System.out.println("Couldn't Find this GameType in the File.");
		}
	}
	
	public void joinUnranked(Player player, String name) {
		GameType game = getGameTypeByName(name);
		
		if(!getAllGameTypes().contains(game)) {
			System.out.println("game was null");
			return;
		}
		
		game.addUnranked(player.getName());
	}
	
	public void leaveUnranked(Player player, String name) {
		GameType game = getGameTypeByName(name);
		
		if(!getAllGameTypes().contains(game)) {
			System.out.println("game was null");
			return;
		}
		
		game.removeUnranked(player.getName());
	}
	
	public void joinRanked(Player player, String name) {
		GameType game = getGameTypeByName(name);
		
		if(!getAllGameTypes().contains(game)) {
			System.out.println("game was null");
			return;
		}
		
		game.getRanked().add(player.getName());
	}
	
	public void leaveRanked(Player player, String name) {
		GameType game = getGameTypeByName(name);
		
		if(!getAllGameTypes().contains(game)) {
			System.out.println("game was null");
			return;
		}
		
		game.getRanked().remove(player.getName());
	}
	
	public boolean inUnrankedQueue(Player player, String name) {
	GameType game = getGameTypeByName(name);
		
		if(!getAllGameTypes().contains(game)) {
			System.out.println("game was null");
			return false;
		}
		
		if(game.getUnranked().contains(player.getName())) {
			return true;
		}
		
		return false;
	}
	
	public GameType getGameTypeByName(String name) {
		for(GameType game : gameType) {
			if(game.getName().equalsIgnoreCase(name)) {
				return game;
			}
		}
		
		return null;
	}
	
	public ArrayList<GameType> getAllGameTypes() {
		return gameType;
	}
}
