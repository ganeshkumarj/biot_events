package com.innovation.iot.domain;

public class Device {

	private String id;
	private String locationMap;
	private String location;
	private String owner;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Device(String id, String locationMap, String location, String owner) {
		super();
		this.id = id;
		this.locationMap = locationMap;
		this.location = location;
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLocationMap() {
		return locationMap;
	}

	public void setLocationMap(String locationMap) {
		this.locationMap = locationMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Device [id=");
		builder.append(id);
		builder.append(", locationMap=");
		builder.append(locationMap);
		builder.append(", location=");
		builder.append(location);
		builder.append(", owner=");
		builder.append(owner);
		builder.append("]");
		return builder.toString();
	}

}
