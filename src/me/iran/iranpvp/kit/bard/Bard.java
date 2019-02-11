package me.iran.iranpvp.kit.bard;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.utils.ScoreboardUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Bard extends BukkitRunnable {

	ScoreboardUtils sb = new ScoreboardUtils();
	
	public void bard(Player player) {
		
		ItemStack helmet = player.getInventory().getHelmet();
		ItemStack chest = player.getInventory().getChestplate();
		ItemStack leggings = player.getInventory().getLeggings();
		ItemStack boots = player.getInventory().getBoots();
		
		if(TeamManager.getManager().isInTeam(player)) {
			Team team = TeamManager.getManager().getTeamByPlayer(player);
			
			
			if(helmet != null && chest != null && leggings != null && boots != null) {
				if (helmet.getType() == Material.GOLD_HELMET && chest.getType() == Material.GOLD_CHESTPLATE && leggings.getType() == Material.GOLD_LEGGINGS && boots.getType() == Material.GOLD_BOOTS) {
					if(team.getBard().contains(player.getName()) && BardManager.getManager().isBard(player)) {
						
						sb.add(player, player.getScoreboard(), ChatColor.DARK_RED, "energy", ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.ENERGY")));
						
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000, 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000, 1));
				     	player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000000, 0));
					    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100000000, 0));
					
					} else if(!team.getBard().equals(player.getName())) {
						
						sb.remove(player, player.getScoreboard(), "energy", ChatColor.DARK_RED);
						
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.NIGHT_VISION);
						player.removePotionEffect(PotionEffectType.REGENERATION);
					}

				} 
			} else {
				if(team.getBard().contains(player.getName())) {
					
					BardManager.getManager().getBardByName(player).setEnergy(25);
					
					sb.remove(player, player.getScoreboard(), "energy", ChatColor.DARK_RED);
					
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.NIGHT_VISION);
					player.removePotionEffect(PotionEffectType.REGENERATION);
				}
			}

		}

	}

	public void passiveEffects(Player player) {

		if (player.getItemInHand() == null) {
			return;
		}

		Team team = TeamManager.getManager().getTeamByPlayer(player);

		if (!TeamManager.getManager().getAllTeams().contains(team)) {
			return;
		}

		if (BardManager.getManager().isBard(player)) {

			// SPEED
			if (player.getItemInHand().getType() == Material.SUGAR) {

				PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 100, 1);
			

				for(Entity p : player.getNearbyEntities(24, 24, 24)) {
					if(p instanceof Player) {
						Player near = (Player) p;
						if(team.getTeamMembers().contains(near.getName())) {
							near.addPotionEffect(effect);
							}
						}
					}
			}
			
			if (player.getItemInHand().getType() == Material.BLAZE_POWDER) {

				PotionEffect effect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0);

				for(Entity p : player.getNearbyEntities(24, 24, 24)) {
					if(p instanceof Player) {
						Player near = (Player) p;
						if(team.getTeamMembers().contains(near.getName())) {
							near.addPotionEffect(effect);
							}
						}
					}

			}
			
			if (player.getItemInHand().getType() == Material.GHAST_TEAR) {

				PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 100, 0);

				for(Entity p : player.getNearbyEntities(24, 24, 24)) {
					if(p instanceof Player) {
						Player near = (Player) p;
						if(team.getTeamMembers().contains(near.getName())) {
							near.addPotionEffect(effect);
							}
						}
					}

			}
			
			if (player.getItemInHand().getType() == Material.FEATHER) {

				PotionEffect effect = new PotionEffect(PotionEffectType.JUMP, 100, 1);

				for(Entity p : player.getNearbyEntities(24, 24, 24)) {
					if(p instanceof Player) {
						Player near = (Player) p;
						if(team.getTeamMembers().contains(near.getName())) {
							near.addPotionEffect(effect);
							}
						}
					}

			}
			
			if (player.getItemInHand().getType() == Material.IRON_INGOT) {

				PotionEffect effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0);

				for(Entity p : player.getNearbyEntities(24, 24, 24)) {
					if(p instanceof Player) {
						Player near = (Player) p;
						if(team.getTeamMembers().contains(near.getName())) {
							near.addPotionEffect(effect);
							}
						}
					}

			}

			if (player.getItemInHand().getType() == Material.MAGMA_CREAM) {

				PotionEffect effect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0);

				for(Entity p : player.getNearbyEntities(24, 24, 24)) {
					if(p instanceof Player) {
						Player near = (Player) p;
						if(team.getTeamMembers().contains(near.getName())) {
							near.addPotionEffect(effect);
							}
						}
					}

			}
			
		}

	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			passiveEffects(p);
		}
		
	}
}
