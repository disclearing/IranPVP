package me.iran.iranpvp.utils;

import me.iran.iranpvp.IranPvP;
import me.iran.iranpvp.arena.Arena;
import me.iran.iranpvp.arena.ArenaManager;
import me.iran.iranpvp.duel.Duel;
import me.iran.iranpvp.duel.DuelManager;
import me.iran.iranpvp.duel.team.TeamDuel;
import me.iran.iranpvp.duel.team.TeamDuelManager;
import me.iran.iranpvp.duel.team.TeamFightManager;
import me.iran.iranpvp.ffa.FFACommand;
import me.iran.iranpvp.kit.bard.BardManager;
import me.iran.iranpvp.teams.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardUtils {

	public void remove(Player player, Scoreboard board, String team, ChatColor color) {
		
		if(board.getObjective(DisplaySlot.SIDEBAR) != null) {
			
			if(board.getTeam(team) != null) {
				board.resetScores(color.toString());
				
				board.getTeam(team).unregister();
			}
		}
	}
	
	
	public void add(Player player, Scoreboard board, ChatColor entry, String t, String name, String msg) {
		
		if(board.getObjective(DisplaySlot.SIDEBAR) != null) {
			
			if(board.getTeam(t) == null) {
				Team team = board.registerNewTeam(t);
				
				Objective obj = board.getObjective(DisplaySlot.SIDEBAR);
				
				team.addEntry(entry.toString());
				team.setPrefix(ChatColor.GREEN.toString() + ChatColor.BOLD + msg);
				team.setSuffix(ChatColor.WHITE + name);
				
				obj.getScore(ChatColor.BLUE.toString()).setScore(board.getEntries().size() + 1);
			}
		}
	}
	
	public void add(Player player, Scoreboard board, ChatColor entry, String t, String msg) {
		
		if(board.getObjective(DisplaySlot.SIDEBAR) != null) {
			
			if(board.getTeam(t) == null) {
				Team team = board.registerNewTeam(t);
				
				Objective obj = board.getObjective(DisplaySlot.SIDEBAR);
				
				team.addEntry(entry.toString());
				team.setPrefix(msg);
				team.setSuffix(ChatColor.GREEN + "");
				
				obj.getScore(entry.toString()).setScore(board.getEntries().size() + 1);
			}
		}
	}
	
	
	
	@SuppressWarnings("deprecation")
	public Scoreboard setKOTHBoard(Player player) {
		
		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
		TeamDuel duel = TeamDuelManager.getManager().getDuelByPlayer(player);
		
		
		if(arena == null) {
			return null;
		}
		
		if(duel == null) {
			return null;
		}

		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		if(board == null) {
			System.out.println("Board is null");
			return null;
		}
		
		Objective obj = board.registerNewObjective("koth", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TITLE")));
		
		Team topWrapper = board.registerNewTeam("top");
		topWrapper.addEntry(ChatColor.WHITE.toString());
		topWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		topWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		obj.getScore(ChatColor.WHITE.toString()).setScore(0);
		
		Team bottomWrapper = board.registerNewTeam("bottom");
		bottomWrapper.addEntry(ChatColor.BLACK.toString());
		bottomWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		bottomWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		obj.getScore(ChatColor.BLACK.toString()).setScore(4);
		
		Team arenaBoard = board.registerNewTeam("koth");
		arenaBoard.addEntry(ChatColor.RED.toString());
		arenaBoard.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.KOTH.ARENA").replace("%arena%", arena.getName())));
		arenaBoard.setSuffix(toMMSS((long) arena.getTimer()));
		obj.getScore(ChatColor.RED.toString()).setScore(2);
		me.iran.iranpvp.teams.Team team = TeamManager.getManager().getTeamByPlayer(player);
		
		Team opponent = board.registerNewTeam("opponent");
		opponent.addEntry(ChatColor.RESET.toString());
		opponent.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.KOTH.OPPONENT")));
	
		Team capper = board.registerNewTeam("capper");
		capper.addEntry(ChatColor.YELLOW.toString());
		capper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.KOTH.CAPPER")));
		capper.setSuffix(ChatColor.YELLOW + "None");
		obj.getScore(ChatColor.YELLOW.toString()).setScore(1);
		
		if(duel.getTeam1() == team) {
			opponent.setSuffix(duel.getTeam2().getTeamName());
		} else {
			opponent.setSuffix(duel.getTeam1().getTeamName());
		}

		obj.getScore(ChatColor.RESET.toString()).setScore(3);
		
		Team friend;
		
		if(board.getTeam("friend") != null) {
			friend = board.getTeam("friend");
		} else {
			friend = board.registerNewTeam("friend");
		}
		
		friend.setPrefix(ChatColor.GREEN.toString());
		
		Team enemy;

		if(board.getTeam("enemy") != null) {
			enemy = board.getTeam("enemy");
		} else {
			enemy = board.registerNewTeam("enemy");
		}
		
		enemy.setPrefix(ChatColor.WHITE.toString());
		enemy.setCanSeeFriendlyInvisibles(false);
		for(Player p : Bukkit.getOnlinePlayers()) {
				enemy.addPlayer(p);
			}
		if(TeamManager.getManager().getAllTeams().contains(team)) {
			
			for(int i = 0; i < team.getTeamMembers().size(); i++) {
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(team.getTeamMembers().contains(p.getName())) {
						
						friend.addPlayer(p);
						friend.canSeeFriendlyInvisibles();
						
					}
				}
			}
		}
		
		player.setScoreboard(board);
		
		return board;
	}
	
	@SuppressWarnings("deprecation")
	public Scoreboard lobbyBoard(Player player) {
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		if(board == null) {
			System.out.println("Board is null");
			return null;
		}
		
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
		Objective obj = board.registerNewObjective("lobby", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TITLE")));
		
		Team topWrapper = board.registerNewTeam("top");
		topWrapper.addEntry(ChatColor.WHITE.toString());
		topWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		topWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		obj.getScore(ChatColor.WHITE.toString()).setScore(1);
		
		Team bottomWrapper = board.registerNewTeam("bottom");
		bottomWrapper.addEntry(ChatColor.BLACK.toString());
		bottomWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		bottomWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		obj.getScore(ChatColor.BLACK.toString()).setScore(4);
		
		Team online = board.registerNewTeam("online");
		online.addEntry(ChatColor.AQUA.toString());
		online.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LOBBY.ONLINE")));
		online.setSuffix(ChatColor.WHITE.toString() + 0);
		obj.getScore(ChatColor.AQUA.toString()).setScore(3);
		
		Team queue = board.registerNewTeam("queue");
		queue.addEntry(ChatColor.RESET.toString());
		queue.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LOBBY.QUEUE")));
		queue.setSuffix(ChatColor.WHITE.toString() + 0);
		obj.getScore(ChatColor.RESET.toString()).setScore(2);	
		
		Team white;
		
		if(board.getTeam("white") != null) {
			white = board.getTeam("white");
		} else {
			white = board.registerNewTeam("white");
		}
		
		white.setPrefix(ChatColor.WHITE.toString());
		
		if(!white.getPlayers().contains(player)) {
			white.addPlayer(player);
		}
		
		player.setScoreboard(board);
		
		return board;
	
	}
	
	@SuppressWarnings("deprecation")
	public Scoreboard setBoard(Player player) {
		
		Arena arena = ArenaManager.getManager().getArenaByPlayer(player);
		TeamDuel duel = TeamDuelManager.getManager().getDuelByPlayer(player);
		
		
		if(arena == null) {
			return null;
		}
		
		if(duel == null) {
			return null;
		}
		
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		if(board == null) {
			System.out.println("Board is null");
			return null;
		}
		
		Objective obj = board.registerNewObjective("koth", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TITLE")));
		
		Team topWrapper = board.registerNewTeam("top");
		topWrapper.addEntry(ChatColor.WHITE.toString());
		topWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		topWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		obj.getScore(ChatColor.WHITE.toString()).setScore(1);
		
		Team bottomWrapper = board.registerNewTeam("bottom");
		bottomWrapper.addEntry(ChatColor.BLACK.toString());
		bottomWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		bottomWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		obj.getScore(ChatColor.BLACK.toString()).setScore(4);
		
		Team arenaBoard = board.registerNewTeam("arena");
		arenaBoard.addEntry(ChatColor.AQUA.toString());
		arenaBoard.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TEAM.ARENA")));
		arenaBoard.setSuffix(arena.getName());
		obj.getScore(ChatColor.AQUA.toString()).setScore(2);
		me.iran.iranpvp.teams.Team team = TeamManager.getManager().getTeamByPlayer(player);
		
		Team opponent = board.registerNewTeam("opponent");
		opponent.addEntry(ChatColor.RESET.toString());
		opponent.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TEAM.OPPONENT")));
	
		Team capper = board.registerNewTeam("alive");
		capper.addEntry(ChatColor.YELLOW.toString());
		capper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TEAM.ALIVE")));
		capper.setSuffix(ChatColor.YELLOW + "None");
		obj.getScore(ChatColor.YELLOW.toString()).setScore(2);

		if(duel.getTeam1() == team) {
			opponent.setSuffix(duel.getTeam2().getTeamName());
		} else {
			opponent.setSuffix(duel.getTeam1().getTeamName());
		}
		
		obj.getScore(ChatColor.RESET.toString()).setScore(3);
		
		Team friend;
		
		if(board.getTeam("friend") != null) {
			friend = board.getTeam("friend");
		} else {
			friend = board.registerNewTeam("friend");
		}
		
		friend.setPrefix(ChatColor.GREEN.toString());
		
		Team enemy;

		if(board.getTeam("enemy") != null) {
			enemy = board.getTeam("enemy");
		} else {
			enemy = board.registerNewTeam("enemy");
		}
		
		enemy.setPrefix(ChatColor.WHITE.toString());
		enemy.setCanSeeFriendlyInvisibles(false);
		for(Player p : Bukkit.getOnlinePlayers()) {
				enemy.addPlayer(p);
			}
		if(TeamManager.getManager().getAllTeams().contains(team)) {
			
			for(int i = 0; i < team.getTeamMembers().size(); i++) {
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(team.getTeamMembers().contains(p.getName())) {
						
						friend.addPlayer(p);
						friend.canSeeFriendlyInvisibles();
						
					}
				}
			}
		}
		
		player.setScoreboard(board);
		
		return board;
	}
	
	public Scoreboard ffaBoard(Player player) {
		
		if(!FFACommand.fighters.contains(player.getName())) {
			return null;
		}
		
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective obj = board.registerNewObjective("ffa", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TITLE")));
		
		// 1
		Team topWrapper = board.registerNewTeam("top");
		topWrapper.addEntry(ChatColor.WHITE.toString());
		topWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		topWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		obj.getScore(ChatColor.WHITE.toString()).setScore(1);

		// 4
		Team bottomWrapper = board.registerNewTeam("bottom");
		bottomWrapper.addEntry(ChatColor.BLACK.toString());
		bottomWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		bottomWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		obj.getScore(ChatColor.BLACK.toString()).setScore(4);

		// 2
		Team ffa = board.registerNewTeam("ffa");
		ffa.addEntry(ChatColor.RED.toString());
		ffa.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.FFA.ALIVE")));
		ffa.setSuffix("" + FFACommand.fighters.size());
		obj.getScore(ChatColor.RED.toString()).setScore(3);
		
		Team timer = board.registerNewTeam("timer");
		timer.addEntry(ChatColor.GREEN.toString());
		timer.setPrefix(ChatColor.GREEN + "Starting in ");
		timer.setSuffix("" + FFACommand.getTimer());
		obj.getScore(ChatColor.GREEN.toString()).setScore(2);
		
		Team fighters;
		
		if(board.getTeam("fighters") != null) {
			fighters = board.getTeam("fighters");
		} else {
			fighters = board.registerNewTeam("fighters");
		}
		
		fighters.setPrefix(ChatColor.RED.toString());
		
		player.setScoreboard(board);
		
		return board;
	}
	
	public Scoreboard duelBoard(Player player) {
		
		Duel duel = DuelManager.getManager().getDuelByPlayer(player);
		
		if(duel == null) {
			return null;
		}
		
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective obj = board.registerNewObjective("duel", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TITLE")));
		
		// 1
		Team topWrapper = board.registerNewTeam("top");
		topWrapper.addEntry(ChatColor.WHITE.toString());
		topWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		topWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		obj.getScore(ChatColor.WHITE.toString()).setScore(1);

		// 4
		Team bottomWrapper = board.registerNewTeam("bottom");
		bottomWrapper.addEntry(ChatColor.BLACK.toString());
		bottomWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		bottomWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		obj.getScore(ChatColor.BLACK.toString()).setScore(4);

		// 2
		Team fight = board.registerNewTeam("fight");
		fight.addEntry(ChatColor.RESET.toString());
		fight.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.DUEL.OPPONENT")));
		
		if(duel.getPlayer1().getName().equals(player.getName())) {
			fight.setSuffix(ChatColor.stripColor(duel.getPlayer2().getName()));
		} else if(duel.getPlayer2().getName().equals(player.getName())) {
			fight.setSuffix(ChatColor.stripColor(duel.getPlayer1().getName()));
		}
		
		obj.getScore(ChatColor.RESET.toString()).setScore(3);
		
		
		Team timer = board.registerNewTeam("timer");
		
		timer.addEntry(ChatColor.DARK_GREEN.toString());
		timer.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.DUEL.DURATION")));
		timer.setSuffix(ChatColor.WHITE + toMMSS(duel.getTimer()));
		
		obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(2);
		
		player.setScoreboard(board);
		
		return board;
	}
	
	@SuppressWarnings("deprecation")
	public Scoreboard specDuelBoard(Player player, Player target) {
		
		Duel duel = DuelManager.getManager().getDuelByPlayer(target);
		
		if(duel == null) {
			System.out.println("duel is null");
			return null;
		}
		
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective obj = board.registerNewObjective("specduel", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TITLE")));
		
		// 1
		Team topWrapper = board.registerNewTeam("top");
		topWrapper.addEntry(ChatColor.WHITE.toString());
		topWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		topWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		obj.getScore(ChatColor.WHITE.toString()).setScore(0);

		Team bottomWrapper = board.registerNewTeam("bottom");
		bottomWrapper.addEntry(ChatColor.BLACK.toString());
		bottomWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		bottomWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		obj.getScore(ChatColor.BLACK.toString()).setScore(5);

		Team player1 = board.registerNewTeam("player1");
		player1.addEntry(ChatColor.RED.toString());
		player1.setPrefix(ChatColor.RED.toString() + ChatColor.BOLD + "Player1: ");
		player1.setSuffix(duel.getPlayer1().getName());
		
		obj.getScore(ChatColor.RED.toString()).setScore(4);
		
		Team player2 = board.registerNewTeam("player2");
		player2.addEntry(ChatColor.GOLD.toString());
		player2.setPrefix(ChatColor.GOLD.toString() + ChatColor.BOLD + "Player2: ");
		player2.setSuffix(duel.getPlayer2().getName());
		
		obj.getScore(ChatColor.GOLD.toString()).setScore(3);
		
		Team kit = board.registerNewTeam("kit");
		kit.addEntry(ChatColor.DARK_AQUA.toString());
		kit.setPrefix(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Kit: ");
		kit.setSuffix(duel.getGameType().getName());
		
		obj.getScore(ChatColor.DARK_AQUA.toString()).setScore(2);
		
		Team timer = board.registerNewTeam("spectimer");
		
		timer.addEntry(ChatColor.DARK_GREEN.toString());
		timer.setPrefix(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Timer: ");
		timer.setSuffix(ChatColor.WHITE + toMMSS(duel.getTimer()));
		
		obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(1);
		
		Team p1;
		
		if(board.getTeam("p1") != null) {
			p1 = board.getTeam("p1");
		} else {
			p1 = board.registerNewTeam("p1");
		}
		
		p1.setPrefix(ChatColor.RED.toString());
		
		p1.addPlayer(duel.getPlayer1());
		
		Team p2;
		
		if(board.getTeam("p2") != null) {
			p2 = board.getTeam("p2");
		} else {
			p2 = board.registerNewTeam("p2");
		}
		
		p2.setPrefix(ChatColor.GOLD.toString());
		
		p2.addPlayer(duel.getPlayer2());
		
		player.setScoreboard(board);
		
		return board;
	}
	
	@SuppressWarnings("deprecation")
	public Scoreboard specTeamBoard(Player player, Player target) {
		
		TeamDuel duel = TeamDuelManager.getManager().getDuelByPlayer(target);
		
		if(duel == null) {
			System.out.println("duel is null");
			return null;
		}
		
		Arena arena = ArenaManager.getManager().getArenaByPlayer(target);
		
		if(arena == null) {
			System.out.println("arena is null");
			return null;
		}
		
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective obj = board.registerNewObjective("specteam", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.TITLE")));
		
		// 1
		Team topWrapper = board.registerNewTeam("top");
		topWrapper.addEntry(ChatColor.WHITE.toString());
		topWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		topWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_TOP")));
		obj.getScore(ChatColor.WHITE.toString()).setScore(0);

		Team bottomWrapper = board.registerNewTeam("bottom");
		bottomWrapper.addEntry(ChatColor.BLACK.toString());
		bottomWrapper.setPrefix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		bottomWrapper.setSuffix(ChatColor.translateAlternateColorCodes('&', IranPvP.plugin.getConfig().getString("SCOREBOARD.LINE_BOTTOM")));
		obj.getScore(ChatColor.BLACK.toString()).setScore(5);

		Team team1 = board.registerNewTeam("team1");
		team1.addEntry(ChatColor.RED.toString());
		team1.setPrefix(ChatColor.RED.toString() + ChatColor.BOLD + "Team1: ");
		team1.setSuffix(duel.getTeam1().getTeamName());
		
		obj.getScore(ChatColor.RED.toString()).setScore(4);
		
		Team team2 = board.registerNewTeam("team2");
		team2.addEntry(ChatColor.GOLD.toString());
		team2.setPrefix(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Team2: ");
		team2.setSuffix(duel.getTeam2().getTeamName());
		
		obj.getScore(ChatColor.GOLD.toString()).setScore(3);
		
		Team kit = board.registerNewTeam("kit");
		kit.addEntry(ChatColor.DARK_AQUA.toString());
		kit.setPrefix(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Kit: ");
		kit.setSuffix(duel.getGameType().getName());
		
		obj.getScore(ChatColor.DARK_AQUA.toString()).setScore(2);
		
		if(arena.isActive()) {
			Team specKoth = board.registerNewTeam("speckoth");
			
			specKoth.addEntry(ChatColor.DARK_GREEN.toString());
			specKoth.setPrefix(ChatColor.YELLOW.toString() + arena.getName());
			specKoth.setSuffix(ChatColor.WHITE + ": " + toMMSS(arena.getTimer()));
			
			obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(1);
		
		} else {
			
			Team specteam = board.registerNewTeam("specteam");
			
			specteam.addEntry(ChatColor.DARK_GREEN.toString());
			specteam.setPrefix(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Arena: ");
			specteam.setSuffix(ChatColor.YELLOW.toString() + arena.getName());
			obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(1);
		}
		
	
		
		Team t1;
		
		if(board.getTeam("p1") != null) {
			t1 = board.getTeam("p1");
		} else {
			t1 = board.registerNewTeam("p1");
		}
		
		t1.setPrefix(ChatColor.RED.toString());
		
		
		Team t2;
		
		if(board.getTeam("p2") != null) {
			t2 = board.getTeam("p2");
		} else {
			t2 = board.registerNewTeam("p2");
		}
		
		t2.setPrefix(ChatColor.GOLD.toString());
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(duel.getTeam1().getTeamMembers().contains(p.getName())) {
				t1.addPlayer(p);
			} else if(duel.getTeam2().getTeamMembers().contains(p.getName())) {
				t2.addPlayer(p);
			}
		}
		
		player.setScoreboard(board);
		
		return board;
	}
	
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
