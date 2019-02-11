package me.iran.iranpvp.duel;

import java.util.ArrayList;
import java.util.Iterator;

import me.iran.iranpvp.type.GameType;
import me.iran.iranpvp.utils.BungeeChat;

import org.bukkit.entity.Player;

public class InvitePlayerManager {

	private static ArrayList<InvitePlayer> invites = new ArrayList<>();
	
	BungeeChat chat = new BungeeChat();
	
	private static InvitePlayerManager im;
	
	private InvitePlayerManager() {}
	
	public static InvitePlayerManager getManager() {
		if (im == null)
			im = new InvitePlayerManager();

		return im;
	}
	
	public void createInvite(Player player1, Player player2, GameType game) {
		InvitePlayer invite = new InvitePlayer(player1.getName(), player2.getName(), game);
		
		if(hasInvited(player1, player2)) {
			return;
		}
		
		invites.add(invite);
		
		chat.acceptPlayer(player1, player2, game.getName());
	}
	
	
	public void deleteInvites(Player player1, Player player2) {
	
		Iterator<InvitePlayer> iter = invites.iterator();
		
		while(iter.hasNext()) {
			InvitePlayer invite = (InvitePlayer) iter.next();
		
			if(invite.getPlayer1().equals(player1.getName()) || invite.getPlayer2().equals(player2.getName())) {
				invite.setGameType(null);
				invite.setPlayer1(null);
				invite.setPlayer2(null);
				invites.remove(invite);
			}
			
		}
		
	}
	
	public InvitePlayer getPlayerInvites(Player player1, Player player2) {
		for(InvitePlayer invite : invites) {
			if(invite.getPlayer1().equals(player1.getName()) && invite.getPlayer2().equals(player2.getName())) {
				return invite;
			}
		}
		
		return null;
	}
	
	public boolean hasInvited(Player player1, Player player2) {
		
		InvitePlayer invite = getPlayerInvites(player1, player2);
		
		if(!invites.contains(invite)) {
			return false;
		}
		
		if(invite.getPlayer1().equals(player1.getName()) && invite.getPlayer2().equals(player2.getName())) {
			return true;
		}
		
		return false;
		
	}
	
	public void clearInvites(Player player) {

		for(int i = 0; i < invites.size(); i++) {
			InvitePlayer invite = invites.get(i);
			
			if(invite.getPlayer1().equals(player.getName()) || invite.getPlayer2().equals(player.getName())) {
				invite.setGameType(null);
				invite.setPlayer1(null);
				invite.setPlayer2(null);
				
				invites.remove(invite);
			}
		}
		
	}
	
	public ArrayList<InvitePlayer> getAllInvites() {
		return invites;
	}
}
