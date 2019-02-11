package me.iran.iranpvp.events.teamfight;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.duel.team.TeamDuel;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.kit.KitManager;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.utils.ScoreboardUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KothDeathEvent implements Listener {

	IranPvP plugin;
	
	public static HashMap<String, Integer> lives = new HashMap<>();
	public static HashMap<String, Integer> cooldown = new HashMap<>();
	
	public static ScoreboardUtils sb = new ScoreboardUtils();
	public static KitManager km = new KitManager();
	
	public KothDeathEvent(IranPvP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		Player player = event.getEntity();
		
		System.out.println("1");
		
		if(TeamManager.getManager().isInTeam(player)) {
			System.out.println("2");
			
			Team team = TeamManager.getManager().getTeamByPlayer(player);
			
			if(TeamDuelManager.getManager().isInDuel(team) && ArenaManager.getManager().getArenaByPlayer(player).isActive()) {
				System.out.println("3");
				cooldown.put(player.getName(), IranPvP.plugin.getConfig().getInt("KOTH.COOLDOWN"));
				System.out.println(cooldown.get(player.getName()));
			}
		}
		
	}
	
	public static HashMap<String, Integer> getLives() {
		return lives;
	}
	
	public static HashMap<String, Integer> getCooldown() {
		return lives;
	}
	
	public static void removeSpectator(Player player) {
		
		if(TeamManager.getManager().isInTeam(player)) {
			Team team = TeamManager.getManager().getTeamByPlayer(player);
			
			if(TeamDuelManager.getManager().isInDuel(team) && ArenaManager.getManager().getArenaByPlayer(player).isActive()) {
				
				Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
				
				if(!ArenaManager.getManager().getAllArena().contains(arena)) {
					return;
				}
				
				TeamDuel duel = TeamDuelManager.getManager().getDuelByPlayer(player);
				
				if(!TeamDuelManager.getManager().getAllDuels().contains(duel)) {
					return;
				}
				
				
				if(duel.getTeam1().getTeamMembers().contains(player.getName())) {
					
					int x1 = arena.getXTeam1();
					int y1 = arena.getYTeam1();
					int z1 = arena.getZTeam1();
					
					float pitch1 = arena.getPitchTeam1();
					float yaw1 = arena.getYawTeam1();
					
					Location location = new Location(Bukkit.getWorld(arena.getWorld()), x1, y1, z1);
					
					location.setPitch(pitch1);
					location.setYaw(yaw1);
					
					player.setAllowFlight(false);
					player.teleport(location);
					
					km.getKits(player, duel.getGameType());
					
					sb.setKOTHBoard(player);
					
				} else if(duel.getTeam2().getTeamMembers().contains(player.getName())) {
					
					int x2 = arena.getXTeam2();
					int y2 = arena.getYTeam2();
					int z2 = arena.getZTeam2();
					
					float pitch2 = arena.getPitchTeam2();
					float yaw2 = arena.getYawTeam2();
					
					Location location = new Location(Bukkit.getWorld(arena.getWorld()), x2, y2, z2);
					
					location.setPitch(pitch2);
					location.setYaw(yaw2);
					
					player.setAllowFlight(false);
					player.teleport(location);
					
					sb.setKOTHBoard(player);
					
					km.getKits(player, duel.getGameType());
				}
				
			}
		}
		
	}
	
}
