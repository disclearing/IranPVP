package me.iran.iranpvp.type;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class RankedManager {

	public ArrayList<Ranked> allRanked = new ArrayList<>();
	
	private static RankedManager rm;

	private RankedManager() {}

	public static RankedManager getManager() {
		if (rm == null)
			rm = new RankedManager();

		return rm;
	}
	
	public void createRanked(int elo, Player player, GameType game) {
		Ranked rank = new Ranked(elo, player, game);
		
		allRanked.add(rank);
	}
	
	public Ranked getRank(Player player, GameType game) {
		for(int i = 0; i < allRanked.size(); i++) {
			Player rp = allRanked.get(i).getPlayer();
			GameType rg = allRanked.get(i).getGameType();
			
			if(player.getUniqueId().toString().equalsIgnoreCase(rp.getUniqueId().toString()) && game.getName().equalsIgnoreCase(rg.getName())) {
				return allRanked.get(i);
			}
			
		}
		
		return null;
	}
	
	public void deleteRanked(Player player, GameType game) {
		Ranked rank = getRank(player, game);
		
		if(!allRanked.contains(rank)) {
			return;
		}
		
		allRanked.remove(rank);
		
	}
	
	public ArrayList<Ranked> getAllRanked() {
		return allRanked;
	}
	
}
