package com.innovation.iot.domain;

public class Profile {
	
	public Profile(int id, String name, String owner) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
	}
	private int id;
	private String name;
	private String owner;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Profile [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", owner=");
		builder.append(owner);
		builder.append("]");
		return builder.toString();
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}


}
