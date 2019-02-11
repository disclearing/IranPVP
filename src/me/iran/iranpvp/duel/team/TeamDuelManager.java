package me.iran.iranpvp.duel.team;

import java.util.ArrayList;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.events.teamfight.KothDeathEvent;
import me.iran.iranpvp.kit.KitManager;
import me.iran.iranpvp.runnable.Run;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.utils.EnderpearlCooldown;
import me.iran.iranpvp.utils.OnJoinItems;
import me.iran.iranpvp.utils.ScoreboardUtils;
import me.iran.iranpvp.utils.TeleportSpawn;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;

public class TeamDuelManager {
	
	IranPvP plugin;
	
	ScoreboardUtils sb = new ScoreboardUtils();
	
	private static ArrayList<TeamDuel> duels = new ArrayList<>();

	private static TeamDuelManager tdm;
	private TeamDuelManager() {}
	
	
	public static TeamDuelManager getManager() {
		if (tdm == null)
			tdm = new TeamDuelManager();

		return tdm;
	}
	
	public ArrayList<TeamDuel> getAllDuels() {
		return duels;
	}
	
	
	Run run = new Run(plugin);
	OnJoinItems items = new OnJoinItems();
	TeleportSpawn spawn = new TeleportSpawn();
	KitManager km = new KitManager();
	
	@SuppressWarnings("deprecation")
	public void createKothDuel(Team team1, Team team2, GameType game) {

		if (team1 == null || team2 == null) {
			return;
		}

		TeamDuel duel = new TeamDuel(team1, team2, game);
		
		duel.setKothStatus(true);

		duels.add(duel);
		
		ArenaManager.getManager().teleportKothTeams(team1, team2);

		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team1.getTeamMembers().contains(p.getName())) {
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				
				p.setScoreboard(sb.setKOTHBoard(p));
				
				p.setHealth(20.0);
				p.setFoodLevel(20);
				
				km.getKits(p, game);
				
				KothDeathEvent.getLives().put(p.getName(), IranPvP.plugin.getConfig().getInt("KOTH.LIVES"));
				
				for(Player pl : Bukkit.getOnlinePlayers()) {
					if(team2.getTeamMembers().contains(pl.getName())) {
						p.showPlayer(pl);
						pl.showPlayer(pl);
					}
				}
				
			}
			
			if(team2.getTeamMembers().contains(p.getName())) {
				
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				
				p.setScoreboard(sb.setKOTHBoard(p));
				
				p.setHealth(20.0);
				p.setFoodLevel(20);
				
				KothDeathEvent.getLives().put(p.getName(), IranPvP.plugin.getConfig().getInt("KOTH.LIVES"));
				
				km.getKits(p, game);
				
				for(Player pl : Bukkit.getOnlinePlayers()) {
					if(team1.getTeamMembers().contains(pl.getName())) {
						p.showPlayer(pl);
						pl.showPlayer(pl);
					}
				}
				
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void createDuel(Team team1, Team team2, GameType game) {

		if (team1 == null || team2 == null) {
			return;
		}

		TeamDuel duel = new TeamDuel(team1, team2, game);
		
		duel.setKothStatus(false);

		duels.add(duel);
		
		ArenaManager.getManager().teleportTeams(team1, team2);
		
		TeamFightManager.getManager().createTeamFight(team1, team2);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(team1.getTeamMembers().contains(p.getName())) {
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				
				p.setHealth(20.0);
				p.setFoodLevel(20);
				
				p.setScoreboard(sb.setBoard(p));
				
				km.getKits(p, game);	
				
				for(Player pl : Bukkit.getOnlinePlayers()) {
					if(team2.getTeamMembers().contains(pl.getName())) {
						p.showPlayer(pl);
						pl.showPlayer(pl);
					}
				}
			}
			
			if(team2.getTeamMembers().contains(p.getName())) {
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				
				p.setHealth(20.0);
				p.setFoodLevel(20);
				
				p.setScoreboard(sb.setBoard(p));
				
				km.getKits(p, game);
				
				for(Player pl : Bukkit.getOnlinePlayers()) {
					if(team1.getTeamMembers().contains(pl.getName())) {
						p.showPlayer(pl);
						pl.showPlayer(pl);
					}
				}
			}
		}
	}
	
	public void endDuel(Player player) {
		TeamDuel duel = getDuelByPlayer(player);

		if (duel == null) {
			System.out.println("duel is null");
			return;
		}

		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
				
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(arena.getSpec().contains(p.getName())) {
				for(String pl : duel.getTeam1().getTeamMembers()) {
					Player team1Players = Bukkit.getPlayer(pl);
					team1Players.showPlayer(p);
				}
				
				for(String pl : duel.getTeam2().getTeamMembers()) {
					Player team2Players = Bukkit.getPlayer(pl);
					team2Players.showPlayer(p);
				}
			}
		}
		
		if(!ArenaManager.getManager().getAllArena().contains(arena)) {
			return;
		}
		
		Team team1 = duel.getTeam1();
		Team team2 = duel.getTeam2();

		if (team1 != null || team2 != null) {

			if (team1.getTeamMembers().contains(player.getName())) {

				duel.setWinner(team1);

				for (Player p : Bukkit.getOnlinePlayers()) {

					if (team1.getTeamMembers().contains(p.getName())) {
						
						if(EnderpearlCooldown.getEnderpearl().containsKey(p.getName())) {
							EnderpearlCooldown.getEnderpearl().remove(p.getName());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
						p.sendMessage("");
						p.sendMessage(ChatColor.GREEN + "You have won against Team " + ChatColor.RED + team2.getTeamName());
						p.sendMessage("");
						
						p.setFireTicks(0);
						
						p.setAllowFlight(false);
						
						for(PotionEffect effect : p.getActivePotionEffects()) {
							p.removePotionEffect(effect.getType());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
					}  
						
					if (team2.getTeamMembers().contains(p.getName())) {
						
						if(EnderpearlCooldown.getEnderpearl().containsKey(p.getName())) {
							EnderpearlCooldown.getEnderpearl().remove(p.getName());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
						p.sendMessage("");
						p.sendMessage(ChatColor.RED + "Team " + ChatColor.GOLD + team1.getTeamName() + ChatColor.RED + " has defeated you!");
						p.sendMessage("");
						
						p.setFireTicks(0);
						
						p.setAllowFlight(false);
						
						for(PotionEffect effect : p.getActivePotionEffects()) {
							p.removePotionEffect(effect.getType());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
					}

				}
				
			} else if (team2.getTeamMembers().contains(player.getName())) {

				duel.setWinner(team2);

				for (Player p : Bukkit.getOnlinePlayers()) {

					if (team2.getTeamMembers().contains(p.getName())) {
						
						if(EnderpearlCooldown.getEnderpearl().containsKey(p.getName())) {
							EnderpearlCooldown.getEnderpearl().remove(p.getName());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
						p.sendMessage("");
						p.sendMessage(ChatColor.GREEN + "You have won against Team " + ChatColor.RED + team1.getTeamName());
						p.sendMessage("");
						
						p.setFireTicks(0);
						
						p.setAllowFlight(false);
						
						for(PotionEffect effect : p.getActivePotionEffects()) {
							p.removePotionEffect(effect.getType());
						}
						
						
					} 
					
					if (team1.getTeamMembers().contains(p.getName())) {
						
						if(EnderpearlCooldown.getEnderpearl().containsKey(p.getName())) {
							EnderpearlCooldown.getEnderpearl().remove(p.getName());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
						p.sendMessage("");
						p.sendMessage(ChatColor.RED + "Team " + ChatColor.GOLD + team2.getTeamName() + ChatColor.RED + " has defeated you!");
						p.sendMessage("");
						
						p.setFireTicks(0);
						
						p.setAllowFlight(false);
						
						for(PotionEffect effect : p.getActivePotionEffects()) {
							p.removePotionEffect(effect.getType());
						}
						
					}

					
				}
			}

			ArenaManager.getManager().removeTeams(player);
			
			duels.remove(duel);
			
		}
		
	}

	public void forceEnd(Player player) {
		TeamDuel duel = getDuelByPlayer(player);

		if (duel == null) {
			System.out.println("duel is null");
			return;
		}

		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
				
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(arena.getSpec().contains(p.getName())) {
				for(String pl : duel.getTeam1().getTeamMembers()) {
					Player team1Players = Bukkit.getPlayer(pl);
					team1Players.showPlayer(p);
				}
				
				for(String pl : duel.getTeam2().getTeamMembers()) {
					Player team2Players = Bukkit.getPlayer(pl);
					team2Players.showPlayer(p);
				}
			}
		}
		
		if(!ArenaManager.getManager().getAllArena().contains(arena)) {
			return;
		}
		
		Team team1 = duel.getTeam1();
		Team team2 = duel.getTeam2();

		if (team1 != null || team2 != null) {

			if (team1.getTeamMembers().contains(player.getName())) {

				duel.setWinner(team1);

				for (Player p : Bukkit.getOnlinePlayers()) {

					if (team1.getTeamMembers().contains(p.getName())) {
						
						if(EnderpearlCooldown.getEnderpearl().containsKey(p.getName())) {
							EnderpearlCooldown.getEnderpearl().remove(p.getName());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
						p.sendMessage("");
						p.sendMessage(ChatColor.GREEN + "You have won against Team " + ChatColor.RED + team2.getTeamName());
						p.sendMessage("");
						
						p.setFireTicks(0);
						
						p.setAllowFlight(false);
						
						for(PotionEffect effect : p.getActivePotionEffects()) {
							p.removePotionEffect(effect.getType());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
					}  
						
					if (team2.getTeamMembers().contains(p.getName())) {
						
						if(EnderpearlCooldown.getEnderpearl().containsKey(p.getName())) {
							EnderpearlCooldown.getEnderpearl().remove(p.getName());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
						p.sendMessage("");
						p.sendMessage(ChatColor.RED + "Team " + ChatColor.GOLD + team1.getTeamName() + ChatColor.RED + " has defeated you!");
						p.sendMessage("");
						
						p.setFireTicks(0);
						
						p.setAllowFlight(false);
						
						for(PotionEffect effect : p.getActivePotionEffects()) {
							p.removePotionEffect(effect.getType());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
					}

				}
				
			} else if (team2.getTeamMembers().contains(player.getName())) {

				duel.setWinner(team2);

				for (Player p : Bukkit.getOnlinePlayers()) {

					if (team2.getTeamMembers().contains(p.getName())) {
						
						if(EnderpearlCooldown.getEnderpearl().containsKey(p.getName())) {
							EnderpearlCooldown.getEnderpearl().remove(p.getName());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
						p.sendMessage("");
						p.sendMessage(ChatColor.GREEN + "You have won against Team " + ChatColor.RED + team1.getTeamName());
						p.sendMessage("");
						
						p.setFireTicks(0);
						
						p.setAllowFlight(false);
						
						for(PotionEffect effect : p.getActivePotionEffects()) {
							p.removePotionEffect(effect.getType());
						}
						
						
					} 
					
					if (team1.getTeamMembers().contains(p.getName())) {
						
						if(EnderpearlCooldown.getEnderpearl().containsKey(p.getName())) {
							EnderpearlCooldown.getEnderpearl().remove(p.getName());
						}
						
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						
						p.sendMessage("");
						p.sendMessage(ChatColor.RED + "Team " + ChatColor.GOLD + team2.getTeamName() + ChatColor.RED + " has defeated you!");
						p.sendMessage("");
						
						p.setFireTicks(0);
						
						p.setAllowFlight(false);
						
						for(PotionEffect effect : p.getActivePotionEffects()) {
							p.removePotionEffect(effect.getType());
						}
						
						
					}

					
				}
			}

			ArenaManager.getManager().removeTeams(player);
			
			duels.remove(duel);
			
		}
		
	}
	
	public TeamDuel getDuelByTeam(Team team) {
		for(TeamDuel duel : duels) {
			if(duel.getTeam1() == team || duel.getTeam2() == team) {
				return duel;
			}
		}
		
		return null;
	}
	
	public TeamDuel getDuelByPlayer(Player player) {
		
		Team team = TeamManager.getManager().getTeamByPlayer(player);
		
		for(TeamDuel duel : duels) {
			if(duel.getTeam1() == team || duel.getTeam2() == team) {
				return duel;
			}
		}
		
		return null;
	}
	
	public boolean isInDuel(Team team) {
		
		for(TeamDuel duel : duels) {
			if(duel.getTeam1() == team || duel.getTeam2() == team) {
				return true;
			}
		}
		return false;
	}
}