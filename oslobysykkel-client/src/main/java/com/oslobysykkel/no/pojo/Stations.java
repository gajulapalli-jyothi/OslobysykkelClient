package com.oslobysykkel.no.pojo;

public class Stations {
	private String id;
	private String title;
	private String locks;
	private String bikes;

	@Override
	public String toString() {
		return "Stations [id=" + id + ", title=" + title + ", locks=" + locks + ", bikes=" + bikes + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocks() {
		return locks;
	}

	public void setLocks(String locks) {
		this.locks = locks;
	}

	public String getBikes() {
		return bikes;
	}

	public void setBikes(String bikes) {
		this.bikes = bikes;
	}

}
