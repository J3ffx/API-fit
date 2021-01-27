package org.uha.ensisa.fanfan.APIfit.model;

public class Suggestion {

	int id;
	String username;
	String theme;
	
	public Suggestion(int id, String username, String theme) {
		this.id = id;
		this.username = username;
		this.theme = theme;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {
		return "{id: " + id + ", username: " + username + ", theme: " + theme + "}";
	}
}
