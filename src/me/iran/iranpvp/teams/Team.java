package me.iran.iranpvp.teams;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private String name;
	private String leader;
	private ArrayList<String> bard;
	private List<String> members;
	private List<String> invites;
	
	public Team(String name, String leader) {
		this.name = name;
		this.leader = leader;
		
		bard = new ArrayList<String>();
		
		members = new ArrayList<String>();
		invites = new ArrayList<String>();
		
		members.add(leader);
	}
	
	public String getTeamName() {
		return name;
	}
	
	public ArrayList<String> getBard() {
		return bard;
	}
	
	public String getTeamLeader() {
		return leader;
	}
	
	public List<String> getTeamMembers() {
		return members;
	}
	
	public List<String> getInvites() {
		return invites;
	}
	
	public void setTeamLeader(String leader) {
		this.leader = leader;
	}
	
	public void setTeamName(String name) {
		this.name = name;
	}
	
	public void addBard(String bard) {
		this.bard.add(bard);
	}
	
	public void setMemebers(List<String> members) {
		this.members = members;
	}
	
	public void setInvites(List<String> invites) {
		this.invites = invites;
	}
	
	public void addMember(String uuid) {
		this.members.add(uuid);
	}
	
	public void addInvite(String uuid) {
		this.invites.add(uuid);
	}
	
	public void removeMember(String uuid) {
		this.members.remove(uuid);
	}
}
