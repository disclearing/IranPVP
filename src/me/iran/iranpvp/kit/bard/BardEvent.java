package me.iran.iranpvp.kit.bard;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BardEvent implements Listener {

	IranPvP plugin;
	
	public BardEvent (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	public static HashMap<String, Long> kitCooldown = new HashMap<String, Long>();
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if(event.getAction() == null) {
			return;
		}
		
		Player player = event.getPlayer();
		
		if(BardManager.getManager().isBard(player)) {
			
			BardClass bard = BardManager.getManager().getBardByName(player);
			
			if(TeamManager.getManager().getTeamByPlayer(player) != null && TeamDuelManager.getManager().isInDuel(TeamManager.getManager().getTeamByPlayer(player))) {
				
				Team team = TeamManager.getManager().getTeamByPlayer(player);
				
				if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					
					if(player.getInventory().getItemInHand() == null) {
						return;
					}
					
					/*
					 * Jump Boost
					 */
					
					if(player.getItemInHand().getType() == Material.FEATHER) {
						
						if(kitCooldown.containsKey(player.getName()) && kitCooldown.get(player.getName()) > System.currentTimeMillis()) {
							
							long timer = kitCooldown.get(player.getName()) - System.currentTimeMillis();
							player.sendMessage(ChatColor.RED.toString() + "Can't used Bard Effects for another " + ChatColor.BOLD + timer/1000 + "'s");
							
						} else {
							
							int power = IranPvP.plugin.getConfig().getInt("BARD.JUMP_BOOST.POWER");
							int duration = IranPvP.plugin.getConfig().getInt("BARD.JUMP_BOOST.DURATION");
							int cooldown = IranPvP.plugin.getConfig().getInt("BARD.JUMP_BOOST.COOLDOWN");
							int amp = IranPvP.plugin.getConfig().getInt("BARD.JUMP_BOOST.AMPLIFIER");
							
							String msg = ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("BARD.MESSAGE").replace("%effect%", "Jump Boost").replace("%bard%", player.getName()));
							
							if(power < bard.getEnerg()) {
								
								bard.setEnergy(bard.getEnerg() - power);
								
								kitCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
								
								player.sendMessage(msg);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * duration, amp));
								
								for (Entity p : player.getNearbyEntities(24,
										24, 24)) {
									if (p instanceof Player) {
										
										Player near = (Player) p;
										
										if (team.getTeamMembers().contains(near.getName())) {
											near.removePotionEffect(PotionEffectType.JUMP);
											near.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 3));
											near.sendMessage(msg);

										}
									}
								}
								
								if(player.getItemInHand().getAmount() > 1) {
									player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
								} else {
									player.setItemInHand(null);
								}
								
							} else {
								player.sendMessage(ChatColor.RED + "Not enough power");
							}
							
						}
						
					}
					
					
					/*
					 * Strength
					 */
					
					if(player.getItemInHand().getType() == Material.BLAZE_POWDER) {
						
						if(kitCooldown.containsKey(player.getName()) && kitCooldown.get(player.getName()) > System.currentTimeMillis()) {
							
							long timer = kitCooldown.get(player.getName()) - System.currentTimeMillis();
							player.sendMessage(ChatColor.RED.toString() + "Can't used Bard Effects for another " + ChatColor.BOLD + timer/1000 + "'s");
							
						} else {
							
							int power = IranPvP.plugin.getConfig().getInt("BARD.STRENGTH.POWER");
							int duration = IranPvP.plugin.getConfig().getInt("BARD.STRENGTH.DURATION");
							int cooldown = IranPvP.plugin.getConfig().getInt("BARD.STRENGTH.COOLDOWN");
							int amp = IranPvP.plugin.getConfig().getInt("BARD.STRENGTH.AMPLIFIER");
							
							String msg = ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("BARD.MESSAGE").replace("%effect%", "Strength").replace("%bard%", player.getName()));
							
							if(power < bard.getEnerg()) {
								
								bard.setEnergy(bard.getEnerg() - power);
								
								kitCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
								
								player.sendMessage(msg);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * duration, amp));
								
								for (Entity p : player.getNearbyEntities(24,
										24, 24)) {
									if (p instanceof Player) {
										
										Player near = (Player) p;
										
										if (team.getTeamMembers().contains(near.getName())) {
											near.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
											near.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * duration, amp));
											near.sendMessage(msg);

										}
									}
								}
								
								if(player.getItemInHand().getAmount() > 1) {
									player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
								} else {
									player.setItemInHand(null);
								}
								
							} else {
								player.sendMessage(ChatColor.RED + "Not enough power");
							}
							
						}
						
					}
					
					/*
					 * Speed
					 */
					
					if(player.getItemInHand().getType() == Material.SUGAR) {
						
						if(kitCooldown.containsKey(player.getName()) && kitCooldown.get(player.getName()) > System.currentTimeMillis()) {
							
							long timer = kitCooldown.get(player.getName()) - System.currentTimeMillis();
							player.sendMessage(ChatColor.RED.toString() + "Can't used Bard Effects for another " + ChatColor.BOLD + timer/1000 + "'s");
							
						} else {
							
							int power = IranPvP.plugin.getConfig().getInt("BARD.SPEED.POWER");
							int duration = IranPvP.plugin.getConfig().getInt("BARD.SPEED.DURATION");
							int cooldown = IranPvP.plugin.getConfig().getInt("BARD.SPEED.COOLDOWN");
							int amp = IranPvP.plugin.getConfig().getInt("BARD.SPEED.AMPLIFIER");
							
							String msg = ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("BARD.MESSAGE").replace("%effect%", "Speed").replace("%bard%", player.getName()));
							
							if(power < bard.getEnerg()) {
								
								bard.setEnergy(bard.getEnerg() - power);
								
								kitCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
								
								player.sendMessage(msg);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, amp));
								
								for (Entity p : player.getNearbyEntities(24,
										24, 24)) {
									if (p instanceof Player) {
										
										Player near = (Player) p;
										
										if (team.getTeamMembers().contains(near.getName())) {
											near.removePotionEffect(PotionEffectType.SPEED);
											near.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, amp));
											near.sendMessage(msg);

										}
									}
								}
								
								if(player.getItemInHand().getAmount() > 1) {
									player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
								} else {
									player.setItemInHand(null);
								}
								
							} else {
								player.sendMessage(ChatColor.RED + "Not enough power");
							}
							
						}
						
					}
					
					/*
					 * Regeneration
					 */
					
					if(player.getItemInHand().getType() == Material.GHAST_TEAR) {
						
						if(kitCooldown.containsKey(player.getName()) && kitCooldown.get(player.getName()) > System.currentTimeMillis()) {
							
							long timer = kitCooldown.get(player.getName()) - System.currentTimeMillis();
							player.sendMessage(ChatColor.RED.toString() + "Can't used Bard Effects for another " + ChatColor.BOLD + timer/1000 + "'s");
							
						} else {
							
							int power = IranPvP.plugin.getConfig().getInt("BARD.REGENERATION.POWER");
							int duration = IranPvP.plugin.getConfig().getInt("BARD.REGENERATION.DURATION");
							int cooldown = IranPvP.plugin.getConfig().getInt("BARD.REGENERATION.COOLDOWN");
							int amp = IranPvP.plugin.getConfig().getInt("BARD.REGENERATION.AMPLIFIER");
							
							String msg = ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("BARD.MESSAGE").replace("%effect%", "Regeneration").replace("%bard%", player.getName()));
							
							if(power < bard.getEnerg()) {
								
								bard.setEnergy(bard.getEnerg() - power);
								
								kitCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
								
								player.sendMessage(msg);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * duration, amp));
								
								for (Entity p : player.getNearbyEntities(24,
										24, 24)) {
									if (p instanceof Player) {
										
										Player near = (Player) p;
										
										if (team.getTeamMembers().contains(near.getName())) {
											near.removePotionEffect(PotionEffectType.REGENERATION);
											near.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * duration, amp));
											near.sendMessage(msg);

										}
									}
								}
								
								if(player.getItemInHand().getAmount() > 1) {
									player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
								} else {
									player.setItemInHand(null);
								}
								
							} else {
								player.sendMessage(ChatColor.RED + "Not enough power");
							}
							
						}
						
					}
					
					/*
					 * Fire Resistance
					 */
					
					if(player.getItemInHand().getType() == Material.MAGMA_CREAM) {
						
						if(kitCooldown.containsKey(player.getName()) && kitCooldown.get(player.getName()) > System.currentTimeMillis()) {
							
							long timer = kitCooldown.get(player.getName()) - System.currentTimeMillis();
							player.sendMessage(ChatColor.RED.toString() + "Can't used Bard Effects for another " + ChatColor.BOLD + timer/1000 + "'s");
							
						} else {
							
							int power = IranPvP.plugin.getConfig().getInt("BARD.FIRE_RESISTANCE.POWER");
							int duration = IranPvP.plugin.getConfig().getInt("BARD.FIRE_RESISTANCE.DURATION");
							int cooldown = IranPvP.plugin.getConfig().getInt("BARD.FIRE_RESISTANCE.COOLDOWN");
							int amp = IranPvP.plugin.getConfig().getInt("BARD.FIRE_RESISTANCE.AMPLIFIER");
							
							String msg = ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("BARD.MESSAGE").replace("%effect%", "Fire Resistance").replace("%bard%", player.getName()));
							
							if(power < bard.getEnerg()) {
								
								bard.setEnergy(bard.getEnerg() - power);
								
								kitCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
								
								player.sendMessage(msg);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * duration, amp));
								
								for (Entity p : player.getNearbyEntities(24,
										24, 24)) {
									if (p instanceof Player) {
										
										Player near = (Player) p;
										
										if (team.getTeamMembers().contains(near.getName())) {
											near.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
											near.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * duration, amp));
											near.sendMessage(msg);

										}
									}
								}
								
								if(player.getItemInHand().getAmount() > 1) {
									player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
								} else {
									player.setItemInHand(null);
								}
								
							} else {
								player.sendMessage(ChatColor.RED + "Not enough power");
							}
							
						}
						
					}
					
					
					/*
					 * Resistance
					 */
					
					if(player.getItemInHand().getType() == Material.IRON_INGOT) {
						
						if(kitCooldown.containsKey(player.getName()) && kitCooldown.get(player.getName()) > System.currentTimeMillis()) {
							
							long timer = kitCooldown.get(player.getName()) - System.currentTimeMillis();
							player.sendMessage(ChatColor.RED.toString() + "Can't used Bard Effects for another " + ChatColor.BOLD + timer/1000 + "'s");
							
						} else {
							
							int power = IranPvP.plugin.getConfig().getInt("BARD.RESISTANCE.POWER");
							int duration = IranPvP.plugin.getConfig().getInt("BARD.RESISTANCE.DURATION");
							int cooldown = IranPvP.plugin.getConfig().getInt("BARD.RESISTANCE.COOLDOWN");
							int amp = IranPvP.plugin.getConfig().getInt("BARD.RESISTANCE.AMPLIFIER");
							
							String msg = ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("BARD.MESSAGE").replace("%effect%", "Resistance").replace("%bard%", player.getName()));
							
							if(power < bard.getEnerg()) {
								
								bard.setEnergy(bard.getEnerg() - power);
								
								kitCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
								
								player.sendMessage(msg);
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * duration, amp));
								
								for (Entity p : player.getNearbyEntities(24,
										24, 24)) {
									if (p instanceof Player) {
										
										Player near = (Player) p;
										
										if (team.getTeamMembers().contains(near.getName())) {
											near.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
											near.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * duration, amp));
											near.sendMessage(msg);

										}
									}
								}
								
								if(player.getItemInHand().getAmount() > 1) {
									player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
								} else {
									player.setItemInHand(null);
								}
								
							} else {
								player.sendMessage(ChatColor.RED + "Not enough power");
							}
							
						}
						
					}
					
					
				}
				
			}
			
		}
	}
}
