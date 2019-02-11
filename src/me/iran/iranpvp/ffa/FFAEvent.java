package me.iran.iranpvp.ffa;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.utils.TeleportSpawn;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.DisplaySlot;

public class FFAEvent implements Listener {

	IranPvP plugin;
	
	public FFAEvent (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	TeleportSpawn spawn = new TeleportSpawn();
	
	private static HashMap<String, Integer> firework = new HashMap<>();
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		if(event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player) {
			Player player = event.getEntity();
			
			if(FFACommand.fighters.contains(player.getName())) {
				
				FFACommand.fighters.remove(player.getName());
				
				Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.RED + " has been eliminated from the FFA Event! (" + FFACommand.fighters.size() + ")");
			
				if(event.getEntity().getKiller() instanceof Player) {
					
					Player killer = event.getEntity().getKiller();
					
					if(FFACommand.fighters.size() < 2) {
						Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + killer.getName() + " HAS WON THE FFA EVENT!!!");
						for(String p : FFACommand.fighters) {
							Player pl = Bukkit.getPlayer(p);
							
							pl.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
							
							spawn.teleportSpawn(pl);
						
						}
						
						firework.put(killer.getName(), 20);
						FFACommand.enabled = false;
						FFACommand.setTimer(60);
						FFACommand.gameType = "";
						FFACommand.fighters.clear();
					}
				} else {
					if(FFACommand.fighters.size() < 2) {
						Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + FFACommand.fighters.get(0) + " HAS WON THE FFA EVENT!!!");
						
						for(String p : FFACommand.fighters) {
							Player pl = Bukkit.getPlayer(p);
							
							pl.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
							
							spawn.teleportSpawn(pl);
						
						}
						
						firework.put(FFACommand.fighters.get(0), 20);
						
						FFACommand.enabled = false;
						FFACommand.setTimer(60);
						FFACommand.gameType = "";
						FFACommand.fighters.clear();
					}
				}
			}
		} else if(event.getEntity() instanceof Player && !(event.getEntity().getKiller() instanceof Player)) {
			
			Player player = (Player) event.getEntity();
			
			if(FFACommand.fighters.contains(player.getName())) {
				
				FFACommand.fighters.remove(player.getName());
				
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				
				Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.RED + " has been eliminated from the FFA Event! (" + FFACommand.fighters.size() + ")");
			
				if(FFACommand.fighters.size() < 2) {
					
					Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + FFACommand.fighters.get(0) + " HAS WON THE FFA EVENT!!!");
					
					Player killer = Bukkit.getPlayer(FFACommand.fighters.get(0));
					
					killer.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					
					spawn.teleportSpawn(killer);
					
					firework.put(killer.getName(), 20);
					FFACommand.enabled = false;
					FFACommand.setTimer(60);
					FFACommand.gameType = "";
					FFACommand.fighters.clear();
				}
		
			}
		}
		
	}
	
	@EventHandler
	public void onDamager(EntityDamageByEntityEvent event) {
		
		if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			
			Player damager = (Player) event.getDamager();
			
			Player hit = (Player) event.getEntity();
			
			if(FFACommand.enabled) {
				if(FFACommand.fighters.contains(damager.getName()) && FFACommand.fighters.contains(hit.getName())) {
					if(FFACommand.getTimer() > 0) {
						damager.sendMessage(ChatColor.RED + "You can't damage anyone for another " + FFACommand.getTimer() + " seconds");
						event.setCancelled(true);
					}
				}
			}
			
		}
		
		if(event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {
			
			Player hit = (Player) event.getEntity();
			
			if(FFACommand.enabled) {
				if(FFACommand.fighters.contains(hit.getName())) {
					if(FFACommand.getTimer() > 0) {
						event.setCancelled(true);
					}
				}
			}
		}
		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(FFACommand.fighters.contains(player.getName())) {
			if(FFACommand.enabled) {
				
				FFACommand.fighters.remove(player.getName());
				Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.RED + " has left FFA (" + FFACommand.fighters.size() + "/80) [Disconnected]");
				
				//if (FFACommand.getTimer() == 0) {
					if (FFACommand.fighters.size() < 2) {
						Bukkit.broadcastMessage(ChatColor.AQUA.toString()
								+ ChatColor.BOLD + FFACommand.fighters.get(0)
								+ " HAS WON THE FFA EVENT!!!");
						for (String p : FFACommand.fighters) {
							Player pl = Bukkit.getPlayer(p);

							pl.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
							
							spawn.teleportSpawn(pl);

						}
						firework.put(FFACommand.fighters.get(0), 20);
						FFACommand.enabled = false;
						FFACommand.setTimer(60);
						FFACommand.gameType = "";
						FFACommand.fighters.clear();
					}
				//}
				
			}
		}
	}
	
	
	public static HashMap<String, Integer> getFirework() {
		return firework;
	}
	
	public static void spawnFirework(Player player) {
		Firework f = (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
        
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder()
                        .flicker(false)
                        .trail(true)
                        .with(Type.CREEPER)
                        .withColor(Color.GREEN)
                        .withFade(Color.BLUE)
                        .build());
        fm.setPower(3);
        f.setFireworkMeta(fm);
	}
}
