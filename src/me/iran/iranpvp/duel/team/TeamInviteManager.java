package me.iran.iranpvp.duel.team;

import java.util.ArrayList;

import me.iran.iranpvp.teams.Team;
import me.iran.iranpvp.type.GameType;

public class TeamInviteManager {

	public static ArrayList<TeamInvite> invites = new ArrayList<>();
	
	private static TeamInviteManager ti;

	private TeamInviteManager() {}

	public static TeamInviteManager getManager() {
		if (ti == null)
			ti = new TeamInviteManager();

		return ti;
	}
	
	public void createInvite(Team team1, Team team2, GameType gametype, String type) {
		
		if(invites.contains(getTeamInvite(team1, team2))) {
			return;
		}
		
		TeamInvite invite = new TeamInvite(team1, team2, gametype, type);
		
		invites.add(invite);
		
	}
	
	public TeamInvite getTeamInvite(Team team1, Team team2) {
		for(TeamInvite invite : invites) {
			if(invite.getTeam1().equals(team1) && invite.getTeam2().equals(team2)) {
				return invite;
			}
		}
		return null;
	}

	public boolean hasInvited(Team team1, Team team2) {
		
		TeamInvite invite = getTeamInvite(team1, team2);
		
		if(!invites.contains(invite)) {
			return false;
		}
		
		if(invite.getTeam1().equals(team1) && invite.getTeam2().equals(team2)) {
			return true;
		}
		
		return false;
	}
	
	public void clearInvites(Team team) {
	
		for(int i = 0; i < invites.size(); i++) {
			TeamInvite invite = invites.get(i);
			
			if(invite.getTeam1().equals(team) || invite.getTeam2().equals(team)) {
				invite.setGameType(null);
				invite.setTeam1(null);
				invite.setTeam2(null);
				invite.setType(null);
				
				invites.remove(invite);
			}
		}
		
	}

}
