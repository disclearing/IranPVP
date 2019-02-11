package me.iran.iranpvp.events.duel;

import me.iran.iranpvp.IranPvP;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class ProtocolEvents implements Listener {

/*	
	 * TODO
	 * 1) hide all entities (done)
	 * 2) hide all dropped entities, and cancel pickup for invisible
	 * 3) play effects of potions where they land
	 * 4) play sound effects for players
	 
	
	IranPvP plugin;
	
	public ProtocolEvents (IranPvP plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLaunch(ProjectileLaunchEvent event) {
		
		final Player player = (Player) event.getEntity().getShooter();
		
		ProtocolLibrary.getProtocolManager().addPacketListener(
				new PacketAdapter(IranPvP.plugin, PacketType.Play.Server.WORLD_EVENT) {
				    public void onPacketSending(PacketEvent e) {
				        if (e.getPacket().getType() == PacketType.Play.Server.WORLD_EVENT) {
				        	
				        	//Person that the packet is recieving
				        	Player p = e.getPlayer();
				        	
				        	if(!IranPvP.getHider().canSee(player, p)) {
				        		e.setCancelled(true);
				        	}
				        }
				    }
				});
		

		
		
	}*/
	
}
