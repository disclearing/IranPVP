package me.iran.iranpvp.runnable;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.duel.Duel;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.duel.team.TeamDuel;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.duel.team.TeamFight;
import me.iran.iranpvp.duel.team.TeamFightManager;
import me.iran.iranpvp.events.duel.DeathInventory;
import me.iran.iranpvp.events.teamfight.KothDeathEvent;
import me.iran.iranpvp.ffa.FFACommand;
import me.iran.iranpvp.ffa.FFAEvent;
import me.iran.iranpvp.kit.bard.Bard;
import me.iran.iranpvp.kit.bard.BardClass;
import me.iran.iranpvp.kit.bard.BardManager;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.type.GameTypeManager;
import me.iran.iranpvp.utils.EnderpearlCooldown;
import me.iran.iranpvp.utils.Queue;
import me.iran.iranpvp.utils.ScoreboardUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Run extends BukkitRunnable {

	IranPvP plugin;
	
	public Run(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	private Queue queue = new Queue();
	
	private Bard bard = new Bard();
	
	private ScoreboardUtils sb = new ScoreboardUtils();
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		queue.unrankedMatch();
		queue.rankedMatch();
		
		kothTimer();
		
		for(Duel duel : DuelManager.getManager().getAllDuels()) {
			duel.setTimer(duel.getTimer() + 1);
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			if(KothDeathEvent.cooldown.containsKey(p.getName())) {
				
				KothDeathEvent.cooldown.put(p.getName(), KothDeathEvent.cooldown.get(p.getName()) - 1);
				
				if(KothDeathEvent.cooldown.get(p.getName()) <= 0) {

					KothDeathEvent.cooldown.remove(p.getName());
					
					if(KothDeathEvent.lives.containsKey(p.getName()) && KothDeathEvent.lives.get(p.getName()) > 0) {
						
						KothDeathEvent.lives.put(p.getName(), KothDeathEvent.lives.get(p.getName()) - 1);
						
						p.sendMessage(ChatColor.RED + "You have " + KothDeathEvent.lives.get(p.getName()) + " live(s) left");
						
						KothDeathEvent.removeSpectator(p);
						
					} else {
						p.sendMessage(ChatColor.RED + "You have run out of lives");
					}
					
				}
			}
			
			
			if(DuelManager.getManager().getGames().containsKey(p.getName())) {
				
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("MATCH_WAIT_MESSAGE").replace("%timer%", "" + DuelManager.getManager().getGames().get(p.getName()))));
				
				DuelManager.getManager().getGames().put(p.getName() , DuelManager.getManager().getGames().get(p.getName()) - 1);
				
				if(DuelManager.getManager().getGames().get(p.getName()) <= 0) {
					DuelManager.getManager().getGames().remove(p.getName());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("MATCH_START_MESSAGE")));
				}
			}
			
			
			kothLogic(p);
			
			if(FFAEvent.getFirework().containsKey(p.getName())) {
				FFAEvent.getFirework().put(p.getName(), FFAEvent.getFirework().get(p.getName()) - 1);
				FFAEvent.spawnFirework(p);
				
				if(FFAEvent.getFirework().get(p.getName()) == 0) {
					FFAEvent.getFirework().remove(p.getName());
				}
			}
			
			if(DeathInventory.getTimer().containsKey(p.getName()) && DeathInventory.getTimer().get(p.getName()) > 0) {
				DeathInventory.getTimer().put(p.getName(), DeathInventory.getTimer().get(p.getName()) - 1);
				
				if(DeathInventory.getTimer().get(p.getName()) <= 0) {
					DeathInventory.getTimer().remove(p.getName());
					DeathInventory.getArmor().remove(p.getName());
					DeathInventory.getInv().remove(p.getName());
					DeathInventory.getPots().remove(p.getName());
				}
			}
			
			bard.bard(p);
			
			if(p.getScoreboard().getTeam("koth") != null) {
				
				p.getScoreboard().getTeam("koth").setSuffix(setSuffix(p));
				p.getScoreboard().getTeam("capper").setSuffix(setCapperSuffix(p));
			
				if(p.getScoreboard().getTeam("energy") != null) {
					p.getScoreboard().getTeam("energy").setSuffix(ChatColor.WHITE.toString() + BardManager.getManager().getBardByName(p).getEnerg());
					
				}
				
			} else if(p.getScoreboard().getTeam("alive") != null) {
				
				p.getScoreboard().getTeam("alive").setSuffix(setAliveSuffix(p));
				
				if(p.getScoreboard().getTeam("energy") != null) {
					p.getScoreboard().getTeam("energy").setSuffix(ChatColor.WHITE.toString() + BardManager.getManager().getBardByName(p).getEnerg());
					
				}
				
			} else if(p.getScoreboard().getTeam("ffa") != null) {
			
				p.getScoreboard().getTeam("ffa").setSuffix(ffaSuffix(p));
				
				for(Player pl : Bukkit.getOnlinePlayers()) {
					
					if(FFACommand.fighters.contains(pl.getName())) {
						p.getScoreboard().getTeam("fighters").addPlayer(pl);
					}
				}
				
				if(p.getScoreboard().getTeam("timer") != null) {
					p.getScoreboard().getTeam("timer").setSuffix("" + FFACommand.getTimer());
					
					if(FFACommand.getTimer() <= 0) {
						sb.remove(p, p.getScoreboard(), "timer", ChatColor.GREEN);
					}
					
				}
				
			} else if(p.getScoreboard().getTeam("online") != null && p.getScoreboard().getTeam("queue") != null) {
				p.getScoreboard().getTeam("online").setSuffix(lobbyOnline(p));
				p.getScoreboard().getTeam("queue").setSuffix(lobbyQueue(p));
			} else if(p.getScoreboard().getTeam("timer") != null) {
				p.getScoreboard().getTeam("timer").setSuffix(duelTimer(p));
			} else if(p.getScoreboard().getTeam("spectimer") != null) {
				p.getScoreboard().getTeam("spectimer").setSuffix(specDuelTimer(p));
			} else if(p.getScoreboard().getTeam("speckoth") != null) {
				p.getScoreboard().getTeam("speckoth").setSuffix(specKothTimer(p));
			}
			
			if(EnderpearlCooldown.getEnderpearl().containsKey(p.getName())) {
				
				EnderpearlCooldown.getEnderpearl().put(p.getName(), EnderpearlCooldown.getEnderpearl().get(p.getName()) - 1);
				
				updatePearl(p, p.getScoreboard(), "pearl", EnderpearlCooldown.getEnderpearl().get(p.getName()));
				
				if(EnderpearlCooldown.getEnderpearl().get(p.getName()) <= 0) {
					EnderpearlCooldown.getEnderpearl().remove(p.getName());
					sb.remove(p, p.getScoreboard(), "pearl", ChatColor.LIGHT_PURPLE);
				}
			}

		}
		
		if(BardManager.getManager().getAllBards().size() > 0) {
			
			for(BardClass bard : BardManager.getManager().getAllBards()) {
				
				if(bard.getEnerg() < bard.getMax()) {
					bard.setEnergy(bard.getEnerg() + 1);
				}
			}
		}
		
	}
	
	private String specKothTimer(Player player) {
		
		String suffix = "";
		
		if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
			
			Arena arena = ArenaManager.getManager().getArenaBySpectator(player);
			
			if(arena != null) {
				
				TeamDuel duel = TeamDuelManager.getManager().getDuelByPlayer(Bukkit.getPlayer(arena.getFighters().get(0)));
				
				if(duel != null && arena.isActive()) {
					suffix = ": " + ChatColor.WHITE + toMMSS(arena.getTimer());
				}
				
			}
	
		}
		
		return suffix;
	}

	private String duelTimer(Player player) {

		String suffix = "";
		
		if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
			
			Duel duel = DuelManager.getManager().getDuelByPlayer(player);
			
			if(duel != null) {
				suffix = ChatColor.WHITE + toMMSS(duel.getTimer());
			}
		}
		
		
		return suffix;
	}

	
	private String specDuelTimer(Player player) {

		String suffix = "";
		
		if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
			
			Arena arena = ArenaManager.getManager().getArenaBySpectator(player);
			
			if(arena != null) {
				
				Duel duel = DuelManager.getManager().getDuelByPlayer(Bukkit.getPlayer(arena.getFighters().get(0)));
				
				if(duel != null) {
					suffix = ChatColor.WHITE + toMMSS(duel.getTimer());
				}
				
			}
	
		}
		
		
		return suffix;
	}
	
	
	public void updatePearl(Player player, Scoreboard board, String name, int suffix) {
		
		if(board.getObjective(DisplaySlot.SIDEBAR) != null) {
			
			Team team = board.getTeam(name);
			
			if(team != null) {
				team.setSuffix(ChatColor.GREEN.toString() + suffix);
			}
		}
	}
	
	public Inventory allTeamsInv(Player player) {
		
		final Inventory teams = Bukkit.createInventory(player, 54, ChatColor.AQUA + "All Teams");
		
		new BukkitRunnable() {
			public void run() {
				ItemStack skull = new ItemStack(Material.DIAMOND);
				ItemMeta skullMeta = skull.getItemMeta();
				if (TeamManager.getManager().getAllTeams().size() <= 54) {
					teams.clear();
					for (int i = 0; i < TeamManager.getManager().getAllTeams().size(); i++) {
						skullMeta.setDisplayName(ChatColor.LIGHT_PURPLE + TeamManager.getManager().getAllTeams().get(i).getTeamName() + ChatColor.AQUA + " (" + TeamManager.getManager().getAllTeams().get(i).getTeamMembers().size() + ")");
						skullMeta.setLore(TeamManager.getManager().getAllTeams().get(i).getTeamMembers());
						skull.setItemMeta(skullMeta);
						teams.setItem(i, skull);
					}
				}
			
			}
		}.runTaskTimer(IranPvP.plugin, 0L, 20L);
		
		return teams;
	}

	//Arena timer
	@SuppressWarnings("deprecation")
	public void kothTimer() {
		if(ArenaManager.getManager().getAllArena().size() > 0) {
			for(Arena arena : ArenaManager.getManager().getAllArena()) {
				if(arena.isActive()) {
					if(arena.getCappers().size() > 0) {
						arena.setTimer(arena.getTimer() - 1);
						Player player = Bukkit.getPlayer(arena.getCapper());
						
						if(player == null) {
							arena.setTimer(arena.getArenaTimer());
							arena.getCappers().clear();
							return;
						}
						
						Arena pArena = ArenaManager.getManager().getArenaByPlayer(player);
						
						
						if(arena.getTimer() <= 5) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(arena.getFighters().contains(p.getName())) {
									p.sendMessage(ChatColor.GOLD +  "[" + ChatColor.GRAY + pArena.getName() + ChatColor.GOLD + "]" + ChatColor.YELLOW + " is at " + ChatColor.RED + pArena.getTimer() + ChatColor.YELLOW + " Seconds");
								}
							}
							
							if(arena.getTimer() <= 0) {
								for(Player p : Bukkit.getOnlinePlayers()) {
									if(arena.getFighters().contains(p.getName())) {
										p.sendMessage(ChatColor.GOLD +  "[" + ChatColor.GRAY + pArena.getName() + ChatColor.GOLD + "]" + ChatColor.YELLOW + " has been Captured!");
									}
								}
								TeamDuelManager.getManager().endDuel(player);
							}
							
						}
						
					} else {
						arena.setTimer(arena.getArenaTimer());
					}
				}
			}
		}
	}
	
	//Anti Flicker
	public String setSuffix(Player player) {
		String suffix = "";

		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);

		if (!ArenaManager.getManager().getAllArena().contains(arena)) {
			return "null prefix";
		}

		suffix = ChatColor.GREEN + toMMSS((long) arena.getTimer());
		return suffix;
	}
	
	//Capper Board
	public String setCapperSuffix(Player player) {
		String suffix = "";

		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);

		if (!ArenaManager.getManager().getAllArena().contains(arena)) {
			return "null prefix";
		}

		me.iran.iranpvp.teams.Team team = TeamManager.getManager()
				.getTeamByPlayer(player);

		if (!TeamManager.getManager().getAllTeams().contains(team)) {
			return "null suffix";
		}

		if (arena.getCappers().size() > 0) {
			if (team.getTeamMembers().contains(arena.getCapper())) {
				suffix = arena.getCapper();
			} else {
				suffix = "Other Team";
			}
		} else {
			suffix = "None";
		}

		return suffix;
	}

	public void kothLogic(Player player) {
		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
		
		if(!ArenaManager.getManager().getAllArena().contains(arena)) {
			return;
		}

		me.iran.iranpvp.teams.Team team = TeamManager.getManager().getTeamByPlayer(player);
		
		if(!TeamManager.getManager().getAllTeams().contains(team)) {
			return;
		}
		
		if(arena.isActive()) {
			
			if(arena.getCappers().size() < 1) {
				arena.setTimer(arena.getArenaTimer());
			}
			
			if(ArenaManager.getManager().insideCube(player, player.getLocation())) {
				if(!arena.getCappers().contains(player.getName())) {
					arena.getCappers().add(player.getName());
					
				}
				
			} else if(!ArenaManager.getManager().insideCube(player, player.getLocation())) {
				if(arena.getCappers().contains(player.getName())) {
					
					if(arena.getCapper().equals(player.getName())) {
						arena.setTimer(arena.getArenaTimer());
					}
					
					arena.getCappers().remove(player.getName());

				}
				
			}
			
		}
	}
	
	public String setAliveSuffix(Player player) {
		String suffix = "";

		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);

		if (!ArenaManager.getManager().getAllArena().contains(arena)) {
			return "null prefix";
		}

		me.iran.iranpvp.teams.Team team = TeamManager.getManager()
				.getTeamByPlayer(player);

		if (!TeamManager.getManager().getAllTeams().contains(team)) {
			return "null suffix";
		}

		if(TeamDuelManager.getManager().isInDuel(team)) {
			TeamFight duel = TeamFightManager.getManager().getFightByTeam(team);
			
			if(duel.getTeam1() == team) {
				suffix = "" + duel.getAliveTeam2().size();
			} else if(duel.getTeam2() == team) {
				suffix = "" + duel.getAliveTeam1().size();
			}
			
		} else {
			suffix = "none";
		}

		return suffix;
	}

	public String ffaSuffix(Player player) {
		String suffix = "";
		
		if(!FFACommand.fighters.contains(player.getName())) {
			return "null prefix";
		}
		
		suffix = "" + FFACommand.fighters.size();
		
		return suffix;
	}
	
	public String lobbyOnline(Player player) {
		String suffix = "";
		
		if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
			
			suffix = ChatColor.WHITE.toString() + Bukkit.getServer().getOnlinePlayers().length;
			
		}
		
		return suffix;
	}
	
	
	public String lobbyQueue(Player player) {
		int counter = 0;
		
		if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
			
			for(GameType game : GameTypeManager.getManager().getAllGameTypes()) {
				counter = counter + game.getRanked().size();
				counter = counter + game.getUnranked().size();
			}
		}
		
		return "" + counter;
	}
	
	//timer fixer
	public static String toMMSS(long dura){
		int minute = (int)(dura / 60.0D);
		long second = dura - (minute * 60);
		String formatted = "";
		{
			if(minute < 10){
				formatted += "";
			}
			formatted += minute;
			formatted += ":";
			if(second < 10){
				formatted += "0";
			}
			formatted += second;
		}
		return formatted;
	}
}
