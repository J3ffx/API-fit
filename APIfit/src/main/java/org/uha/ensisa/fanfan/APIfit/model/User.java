package org.uha.ensisa.fanfan.APIfit.model;

import java.util.ArrayList;

public class User {

	String username;
	String password;
	ArrayList<Challenge> subChals;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.subChals = new ArrayList<Challenge>();
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

	public ArrayList<Challenge> getSubChals() {
		return subChals;
	}

	public void setSubChals(ArrayList<Challenge> subChals) {
		this.subChals = subChals;
	}

	public void addChallenge(Challenge chal) {
		this.subChals.add(chal);
	}

	public void deleteChallenge(Challenge chal) {
		this.subChals.remove(chal);
	}

	public String chalsToString() {
		String result = "{";
		for (Challenge chal : subChals) {
			if (chal.getId() == subChals.get(subChals.size() - 1).getId())
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
