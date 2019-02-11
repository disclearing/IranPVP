package me.iran.iranpvp.kit.bard;

import java.util.ArrayList;

import me.iran.iranpvp.type.GameTypeManager;

import org.bukkit.entity.Player;

public class BardManager {

	private static ArrayList<BardClass> bards = new ArrayList<>();
	
	private static BardManager bm;

	private BardManager() {}

	public static BardManager getManager() {
		if (bm == null)
			bm = new BardManager();

		return bm;
	}
	
	public void createBard(Player player) {
		
		if(isBard(player)) {
			return;
		}
		
		BardClass bard = new BardClass(player.getName());
		
		bards.add(bard);
		
		bard.setEnergy(50);
		
	}
	
	public boolean isBard(Player player) {
		
		for(BardClass bard : bards) {
			if(bard.getPlayer().equalsIgnoreCase(player.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	public BardClass getBardByName(Player player) {
		
		for(BardClass bard : bards) {
			if(bard.getPlayer().equalsIgnoreCase(player.getName())) {
				return bard;
			}
		}
		
		return null;
	}
	
	public void removeBard(Player player) {
		
		if(isBard(player)) {
			bards.remove(getBardByName(player));
			
		}
		
	}

	public ArrayList<BardClass> getAllBards() {
		return bards;
	}
	
}
