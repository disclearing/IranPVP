package me.iran.iranpvp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.cmd.arena.ArenaCommands;
import me.iran.iranpvp.cmd.pvp.EloCommands;
import me.iran.iranpvp.cmd.pvp.PvPCommands;
import me.iran.iranpvp.cmd.pvp.specevent.SpectatorEvents;
import me.iran.iranpvp.cmd.team.TeamCommands;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.events.duel.ClickDuelInventoryEvent;
import me.iran.iranpvp.events.duel.DeathInventory;
import me.iran.iranpvp.events.duel.PlayerBreakBlockEvent;
import me.iran.iranpvp.events.duel.PlayerDeathWhileInPvP;
import me.iran.iranpvp.events.duel.PlayerDisconnectWhileInPvP;
import me.iran.iranpvp.events.duel.ProtocolEvents;
import me.iran.iranpvp.events.duel.RespawnEvent;
import me.iran.iranpvp.events.duel.TeamClickInventoryEvent;
import me.iran.iranpvp.events.inhanditems.AddItemsOnJoin;
import me.iran.iranpvp.events.inhanditems.DropItemsInHandEvent;
import me.iran.iranpvp.events.inhanditems.InteractWithItemsInHand;
import me.iran.iranpvp.events.teamfight.DisconnectWhileInTeam;
import me.iran.iranpvp.events.teamfight.KothDeathEvent;
import me.iran.iranpvp.events.teamfight.TeamDamageTeam;
import me.iran.iranpvp.events.teamfight.TeamFightEvent;
import me.iran.iranpvp.ffa.FFACommand;
import me.iran.iranpvp.ffa.FFAEvent;
import me.iran.iranpvp.ffa.FFARunnable;
import me.iran.iranpvp.kit.KitEvents;
import me.iran.iranpvp.kit.KitManager;
import me.iran.iranpvp.kit.bard.Bard;
import me.iran.iranpvp.kit.bard.BardEvent;
import me.iran.iranpvp.runnable.Run;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamChat;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.type.GameTypeCommands;
import me.iran.iranpvp.type.GameTypeManager;
import me.iran.iranpvp.type.RankedEvent;
import me.iran.iranpvp.utils.EnderpearlCooldown;
import me.iran.iranpvp.utils.Queue;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.example.EntityHider;
import com.comphenix.example.EntityHider.Policy;

public class IranPvP extends JavaPlugin implements Listener {

	File file = null;
	
	public static Plugin plugin;
	
	private Run run = new Run(this);
	
	private TeleportSpawn spawn = new TeleportSpawn();
	private KitManager km = new KitManager();
	private Bard bard = new Bard();
	private FFARunnable ffaRun = new FFARunnable(this);
	
	private Queue queue = new Queue();
	
	private RankedEvent rank = new RankedEvent(this);
	
	private static ArrayList<String> toggle = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		
		IranPvP.plugin = this;
		
		ArenaManager.getManager().loadArena();
		GameTypeManager.getManager().loadGameTypes();
		
		file = new File(this.getDataFolder(), "gametype.yml");
		
		if(!file.exists()) {
			file = new File(this.getDataFolder(), "gametype.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration gConfig = YamlConfiguration.loadConfiguration(file);
			
			gConfig.createSection("gametype");
			
			try {
				gConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		file = new File(this.getDataFolder(), "arena.yml");
		
		if(!file.exists()) {
			
			System.out.println("[IranPvP] Could not find any Arenas, Creating new file");
			
			file = new File(this.getDataFolder(), "arena.yml");
			
			new YamlConfiguration();
			
			YamlConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
			
			arenaConfig.createSection("arena");
		
			try {
				arenaConfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new ClickDuelInventoryEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new InteractWithItemsInHand(this), this);
		Bukkit.getPluginManager().registerEvents(new DropItemsInHandEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathWhileInPvP(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDisconnectWhileInPvP(this), this);
		Bukkit.getPluginManager().registerEvents(new AddItemsOnJoin(this), this);
		Bukkit.getPluginManager().registerEvents(new TeamClickInventoryEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new TeamFightEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new EnderpearlCooldown(this), this);
		Bukkit.getPluginManager().registerEvents(new TeamDamageTeam(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerBreakBlockEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new RespawnEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new DisconnectWhileInTeam(this), this);
		Bukkit.getPluginManager().registerEvents(new BardEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new KitEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new DeathInventory(this), this);
		Bukkit.getPluginManager().registerEvents(new SpectatorEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new FFAEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new RankedEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new TeamChat(this), this);
		Bukkit.getPluginManager().registerEvents(new GameTypeCommands(this), this);
		Bukkit.getPluginManager().registerEvents(new KothDeathEvent(this), this);
		//Bukkit.getPluginManager().registerEvents(new ProtocolEvents(this), this);
		
		getCommand("ffa").setExecutor(new FFACommand(this));
		getCommand("leave").setExecutor(new FFACommand(this));
		getCommand("join").setExecutor(new FFACommand(this));
		
		getCommand("elo").setExecutor(new EloCommands(this));
		
		getCommand("duel").setExecutor(new PvPCommands(this));
		getCommand("ping").setExecutor(new PvPCommands(this));
		getCommand("accept").setExecutor(new PvPCommands(this));
		getCommand("spectate").setExecutor(new PvPCommands(this));
		getCommand("deny").setExecutor(new PvPCommands(this));
		getCommand("arena").setExecutor(new ArenaCommands(this));
		getCommand("team").setExecutor(new TeamCommands(this));
		getCommand("gametype").setExecutor(new GameTypeCommands(this));
		getCommand("iranpvpitems").setExecutor(new ItemsCommand(this));
		
		run.runTaskTimer(this, 0L, 20L);
		ffaRun.runTaskTimer(this, 0L, 20L);
		bard.runTaskTimer(this, 0L, 10L);
		
		if(Bukkit.getOnlinePlayers().length > 0) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				rank.createFiles(p);
			}
		}
		
	}	
	
	@Override
	@SuppressWarnings("deprecation")
	public void onDisable() {
		if(Bukkit.getOnlinePlayers().length > 0) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				rank.saveElo(p);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;

		if(cmd.getName().equalsIgnoreCase("setpracticespawn")) {
			
			if(!player.hasPermission("iranpvp.setloc")) { return true; }
			
			if(!this.getConfig().contains("spawn")) {
				this.getConfig().createSection("spawn.x");
				this.getConfig().createSection("spawn.y");
				this.getConfig().createSection("spawn.z");
				this.getConfig().createSection("spawn.pitch");
				this.getConfig().createSection("spawn.yaw");
				this.getConfig().createSection("spawn.world");
				
				this.getConfig().set("spawn.x", player.getLocation().getBlockX());
				this.getConfig().set("spawn.y", player.getLocation().getBlockY());
				this.getConfig().set("spawn.z", player.getLocation().getBlockZ());
				this.getConfig().set("spawn.pitch", player.getLocation().getPitch());
				this.getConfig().set("spawn.yaw", player.getLocation().getYaw());
				this.getConfig().set("spawn.world", player.getLocation().getWorld().getName());
				this.saveConfig();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("SET_SPAWN")));
			} else {
				this.getConfig().set("spawn.x", player.getLocation().getBlockX());
				this.getConfig().set("spawn.y", player.getLocation().getBlockY());
				this.getConfig().set("spawn.z", player.getLocation().getBlockZ());
				this.getConfig().set("spawn.pitch", player.getLocation().getPitch());
				this.getConfig().set("spawn.yaw", player.getLocation().getYaw());
				this.getConfig().set("spawn.world", player.getLocation().getWorld().getName());
				this.saveConfig();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("SET_SPAWN")));
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("seteditor")) {
			
			if(!player.hasPermission("iranpvp.setloc")) { 
			
				return true; 
				
			}
			
			if(!this.getConfig().contains("kit")) {
				this.getConfig().createSection("kit.x");
				this.getConfig().createSection("kit.y");
				this.getConfig().createSection("kit.z");
				this.getConfig().createSection("kit.pitch");
				this.getConfig().createSection("kit.yaw");
				this.getConfig().createSection("kit.world");
				
				this.getConfig().set("kit.x", player.getLocation().getBlockX());
				this.getConfig().set("kit.y", player.getLocation().getBlockY());
				this.getConfig().set("kit.z", player.getLocation().getBlockZ());
				this.getConfig().set("kit.pitch", player.getLocation().getPitch());
				this.getConfig().set("kit.yaw", player.getLocation().getYaw());
				this.getConfig().set("kit.world", player.getLocation().getWorld().getName());
				this.saveConfig();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("SET_EDITOR_SPAWN")));
			} else {
				this.getConfig().set("kit.x", player.getLocation().getBlockX());
				this.getConfig().set("kit.y", player.getLocation().getBlockY());
				this.getConfig().set("kit.z", player.getLocation().getBlockZ());
				this.getConfig().set("kit.pitch", player.getLocation().getPitch());
				this.getConfig().set("kit.yaw", player.getLocation().getYaw());
				this.getConfig().set("kit.world", player.getLocation().getWorld().getName());
				this.saveConfig();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("SET_EDITOR_SPAWN")));
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("items")) {
			if(TeamManager.getManager().isInTeam(player)) {
				Team team = TeamManager.getManager().getTeamByPlayer(player);
				if(TeamDuelManager.getManager().isInDuel(team)) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("IN_DUEL_ERROR")));
					return true;
				}
			}
			
			if(DuelManager.getManager().isInDuel(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("IN_DUEL_ERROR")));
				return true;
			}
			
			if(queue.isInUnranked(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("IN_QUEUE_ERROR")));
				return true;
			}
			
			if(queue.isInRanked(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("IN_QUEUE_ERROR")));
				return true;
			}
			
			if(FFACommand.fighters.contains(player.getName())) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("IN_FFA_ERROR")));
				return true;
			}
			
			if (ArenaManager.getManager().isSpectator(player)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("IN_SPECTATOR_ERROR")));
				return true;
			}
			
			spawn.teleportSpawn(player);
		}
		
		if(cmd.getName().equalsIgnoreCase("view")) {
			
			if(args.length < 1) {
				player.sendMessage(ChatColor.RED + "/view player");
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(ChatColor.RED + "Couldn't find player");
				return true;
			}
			
			if(DeathInventory.getTimer().containsKey(target.getName())) {
				player.openInventory(DeathInventory.deadInv(target));
			}
			
		}
		
		return true;
	}
	

	public ArrayList<String> getToggle() {
		return toggle;
	}
	
}
