package me.iran.iranpvp.utils;

import me.iran.iranpvp.IranPvP;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Player;

public class BungeeChat {

	public void joinTeam(Player player, Player target) {
		TextComponent click = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_INVITE_MESSAGE.CLICK")));
		TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_INVITE_MESSAGE.MESSAGE").replace("%team%", player.getName())));
		TextComponent blank = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_INVITE_MESSAGE.LINE")));
		click.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team join " + player.getName()));
		click.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Join Team " + player.getName()).create()));
		
		target.spigot().sendMessage(blank);
		target.spigot().sendMessage( message);
		target.spigot().sendMessage(click);
		target.spigot().sendMessage(blank);
	}
	
	public void acceptTeamDuel(Player player, Player target, String type) {
		TextComponent click = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_RECIEVE_INVITE.CLICK")));
		TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_RECIEVE_INVITE.MESSAGE").replace("%team%", player.getName()).replace("%gametype%", type)));
		
		TextComponent blank = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("TEAM_RECIEVE_INVITE.LINE")));
		
		click.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team accept " + player.getName()));
		
		click.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Accept Team Fight").create()));
	
		target.spigot().sendMessage(blank);
		target.spigot().sendMessage( message);
		target.spigot().sendMessage(click);
		target.spigot().sendMessage(blank);
	}
	
	public void acceptKothDuel(Player player, Player target, String type) {
		TextComponent click = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("KOTH_RECIEVE_INVITE.CLICK")));
		TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("KOTH_RECIEVE_INVITE.MESSAGE").replace("%team%", player.getName()).replace("%gametype%", type)));
		
		TextComponent blank = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("KOTH_RECIEVE_INVITE.LINE")));
		
		click.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team accept " + player.getName()));
		
		click.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Accept KOTH Fight").create()));
	
		target.spigot().sendMessage(blank);
		target.spigot().sendMessage( message);
		target.spigot().sendMessage(click);
		target.spigot().sendMessage(blank);
	}
	
	public void acceptPlayer(Player player, Player target, String type) {
		TextComponent click = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("DUEL_RECIEVE_INVITE.CLICK")));
		TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("DUEL_RECIEVE_INVITE.MESSAGE").replace("%player%", player.getName()).replace("%gametype%", type)));
	
		TextComponent blank = new TextComponent(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("DUEL_RECIEVE_INVITE.LINE")));
		
		click.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/iranpvp:accept " + player.getName()));
		
		click.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Accept Duel").create()));
		
		target.spigot().sendMessage(blank);
		target.spigot().sendMessage( message);
		target.spigot().sendMessage(click);
		target.spigot().sendMessage(blank);
	}
	
	public void viewInv(Player winner, Player loser) {
		TextComponent clickWinner = new TextComponent(ChatColor.GREEN + "Winner: " + ChatColor.GOLD + winner.getName());
		TextComponent clickLoser = new TextComponent(ChatColor.RED + "Loser: " + ChatColor.GOLD + loser.getName());
		
		clickWinner.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + winner.getName()));
		clickLoser.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + loser.getName()));
		
		winner.sendMessage("");
		winner.spigot().sendMessage(clickWinner);
		winner.spigot().sendMessage(clickLoser);
		winner.sendMessage("");
		
		loser.sendMessage("");
		loser.spigot().sendMessage(clickWinner);
		loser.spigot().sendMessage(clickLoser);
		loser.sendMessage("");
	}
	
	public void viewInvSpec(Player winner, Player loser, Player spec) {
		TextComponent clickWinner = new TextComponent(ChatColor.GREEN + "Winner: " + ChatColor.GOLD + winner.getName());
		TextComponent clickLoser = new TextComponent(ChatColor.RED + "Loser: " + ChatColor.GOLD + loser.getName());
		
		clickWinner.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + winner.getName()));
		clickLoser.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + loser.getName()));
		
		spec.sendMessage("");
		spec.spigot().sendMessage(clickWinner);
		spec.spigot().sendMessage(clickLoser);
		spec.sendMessage("");

	}
}
