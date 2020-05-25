package model;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private boolean moveHelper;
	
	public Player(String name) {
		this(name, true);
	}
	
	public Player(String name, boolean moveHelper) {
		this.name = name;
		this.moveHelper = moveHelper;
	}
	
	public void setName(String name) {
		name = name.trim();
		if(!name.isEmpty()) this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMoveHelper(boolean moveHelper) {
		this.moveHelper = moveHelper;
	}
	
	public boolean isMoveHelperActive() {
		return moveHelper;
	}
	
	@Override
	public String toString() {
		return "Player:[name=" + name + ", moveHelper=" + moveHelper + "]"; 
	}
}
