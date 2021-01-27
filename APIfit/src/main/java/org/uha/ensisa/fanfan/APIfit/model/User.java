package org.uha.ensisa.fanfan.APIfit.model;

import java.util.ArrayList;

public class User {

	int id;
	String username;
	String password;
	boolean admin;
	ArrayList<Challenge> challenges;

	public User(int id, String username, String password) {
		this.username = username;
		this.password = password;
		this.admin= false;
		this.challenges = new ArrayList<Challenge>();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public ArrayList<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(ArrayList<Challenge> challenges) {
		this.challenges = challenges;
	}

	public void addChallenge(Challenge chal) {
		this.challenges.add(chal);
	}

	public void deleteChallenge(Challenge chal) {
		this.challenges.remove(chal);
	}

	public String chalsToString() {
		String result = "{";
		for (Challenge chal : challenges) {
			if (chal.getId() == challenges.get(challenges.size() - 1).getId())
				result += "chal" + chal.getId() + ": " + chal.getName();
			else
				result += "chal" + chal.getId() + ": " + chal.getName() + ", ";
		}
		result += "}";
		return result;
	}

	@Override
	public String toString() {
		String result = "{username: " + username + ", password: " + password + ", challenges: " + chalsToString() + "}";
		return result;
	}

}
