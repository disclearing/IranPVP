package me.iran.iranpvp.events.inhanditems;

import java.util.HashMap;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.runnable.Run;
import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.teams.TeamManager;
import me.iran.iranpvp.utils.CustomInventory;
import me.iran.iranpvp.utils.OnJoinItems;
import me.iran.iranpvp.utils.Queue;
import me.iran.iranpvp.utils.ScoreboardUtils;
import me.iran.iranpvp.utils.TeleportSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractWithItemsInHand implements Listener {

	IranPvP plugin;

	public InteractWithItemsInHand(IranPvP plugin) {
		this.plugin = plugin;
	}

	HashMap<String, Long> timer = new HashMap<>();

	Queue queue = new Queue();
	OnJoinItems items = new OnJoinItems();
	CustomInventory inv = new CustomInventory();
	TeleportSpawn spawn = new TeleportSpawn();

	ScoreboardUtils sb = new ScoreboardUtils();
	
	Run run = new Run(plugin);

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (event.getAction() == null) {
			return;
		}

		if (player.getItemInHand() == null) {
			return;
		}

		if (!player.getItemInHand().hasItemMeta()) {
			return;
		}

		if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
			return;
		}

		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (timer.containsKey(player.getName())
					&& timer.get(player.getName()) > System.currentTimeMillis()
					&& !player.isOp()) {
				player.sendMessage(ChatColor.RED + "Stop spamming items");
				event.setCancelled(true);
				return;
			} else {

				
				timer.put(player.getName(),
						System.currentTimeMillis() + 2 * 1000);

				if (event.getAction() == Action.RIGHT_CLICK_AIR
						|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {

					if (player
							.getItemInHand()
							.getItemMeta()
							.getDisplayName()
							.equals(ChatColor.RED.toString() + ChatColor.BOLD
									+ "Leave Unranked queue")) {
						
						sb.remove(player, player.getScoreboard(), "unranked", ChatColor.BLUE);
						
						queue.leaveUnranked(player);
						items.onJoin(player);
						return;
					}

					if (player
							.getItemInHand()
							.getItemMeta()
							.getDisplayName()
							.equals(ChatColor.RED.toString() + ChatColor.BOLD
									+ "Leave Ranked queue")) {
						
						sb.remove(player, player.getScoreboard(), "ranked", ChatColor.BLUE);
						
						queue.leaveRanked(player);
						items.onJoin(player);
						return;
					}
					
					if (player.getItemInHand().getItemMeta().getDisplayName()
							.equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("UNRANKED_SWORD")))) {
						player.openInventory(inv.queue(player));
						return;
					}

					if (player.getItemInHand().getItemMeta().getDisplayName()
							.equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("RANKED_SWORD")))) {
						player.openInventory(inv.ranked(player));
						return;
					}
					
					if (player.getItemInHand().getItemMeta().getDisplayName()
							.equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("CREATE_TEAM")))) {
						if (!TeamManager.getManager().isInTeam(player)) {
							if (queue.isInUnranked(player)) {
								player.sendMessage(ChatColor.RED
										+ "Can't create a team while in a Queue");
								return;
							}
							TeamManager.getManager().createTeam(
									player.getName(), player);
						}
					} else if (player.getItemInHand().getItemMeta()
							.getDisplayName()
							.equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_INFO")))) {
						if (TeamManager.getManager().isInTeam(player)) {

							Team team = TeamManager.getManager()
									.getTeamByPlayer(player);

							player.sendMessage(ChatColor.AQUA.toString()
									+ "------" + ChatColor.BOLD
									+ team.getTeamName() + ChatColor.AQUA
									+ "------");
							player.sendMessage("");
							for (String p : team.getTeamMembers()) {
								player.sendMessage(ChatColor.GREEN + p);
							}
							player.sendMessage("");
						}
					} else if (player.getItemInHand().getItemMeta()
							.getDisplayName()
							.equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("LEAVE_TEAM")))) {
						if (TeamManager.getManager().isInTeam(player)) {
							TeamManager.getManager().leaveTeam(player);
							spawn.teleportSpawn(player);
						}
					} else if (player.getItemInHand().getItemMeta()
							.getDisplayName()
							.equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("ALL_TEAMS")))) {
						if (TeamManager.getManager().isInTeam(player)) {
							player.openInventory(run.allTeamsInv(player));
						}
					} else if (player.getItemInHand().getItemMeta()
							.getDisplayName()
							.equals(ChatColor.RED + "Make Leader")) {
						if (TeamManager.getManager().isInTeam(player)) {
							Team team = TeamManager.getManager()
									.getTeamByPlayer(player);

							if (team.getTeamLeader().equals(player.getName())) {
								TeamManager.getManager().disbandTeam(player);
							}

						}
					} else if (player.getItemInHand().getItemMeta()
							.getDisplayName()
							.equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("KIT_EDITOR")))) {
						if (TeamManager.getManager().isInTeam(player)) {
							player.sendMessage(ChatColor.RED
									+ "Can't do this while in a in Team");
						} else {
							player.openInventory(inv.kitEditorInv(player));
						}

					} else if (player.getItemInHand().getItemMeta()
							.getDisplayName()
							.equals(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("DISBAND_TEAM")))) {
						if (TeamManager.getManager().isInTeam(player)) {
							Team team = TeamManager.getManager()
									.getTeamByPlayer(player);

							if (team.getTeamLeader().equals(player.getName())) {
								TeamManager.getManager().disbandTeam(player);
							}
						}
					} else if (player.getItemInHand().getItemMeta()
							.getDisplayName()
							.equals(ChatColor.RED + "Leave Spectator")) {
						if (ArenaManager.getManager().isSpectator(player)) {
							Arena arena = ArenaManager.getManager()
									.getArenaBySpectator(player);
							arena.getSpec().remove(player.getName());
							for (Player p : Bukkit.getOnlinePlayers()) {
								p.showPlayer(player);
							}

							player.setGameMode(GameMode.SURVIVAL);
							player.setAllowFlight(false);
							player.setHealth(20.0);
							player.setFoodLevel(20);
							spawn.teleportSpawn(player);
						}

					}
				}
			}
		}
	}
}
