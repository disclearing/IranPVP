package me.iran.iranpvp.type;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class GameType {

	private String name;
	private ItemStack[] inv;
	private ItemStack[] armor;
	private ItemStack display;
	private ArrayList<String> unranked;
	private ArrayList<String> ranked;
	private boolean edit;
	
	public GameType(String name, ItemStack[] inv, ItemStack[] armor, ItemStack display, boolean edit) {
		this.name = name;
		this.edit = edit;
		this.inv = inv;
		this.armor = armor;
		this.display = display;
		
		unranked = new ArrayList<String>();
		ranked = new ArrayList<String>();
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	
	public boolean getEdit() {
		return this.edit;
	}
	
	public void addUnranked(String name) {
		unranked.add(name);
	}
	
	public void removeUnranked(String name) {
		unranked.remove(name);
	}
	
	public void setInv(ItemStack[] inv) {
		this.inv = inv;
	}
	
	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}
	
	public void setDisplay(ItemStack display) {
		this.display = display;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ItemStack[] getInv() {
		return this.inv;
	}
	
	public ItemStack[] getArmor() {
		return this.armor;
	}
	
	public ItemStack getDisplay() {
		return this.display;
	}
	
	public ArrayList<String> getUnranked() {
		return this.unranked;
	}
	
	public ArrayList<String> getRanked() {
		return this.ranked;
	}
}
