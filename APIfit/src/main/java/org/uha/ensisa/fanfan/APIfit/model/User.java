package org.uha.ensisa.fanfan.APIfit.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	int id;
	String username;
	String password;
	boolean admin;
	@ElementCollection
	ArrayList<Integer> challenges;

	public User() {
	}
	
	public User(int id, String username, String password, boolean admin, Integer chalId) {
		this.username = username;
		this.password = password;
		this.admin= admin;
		this.challenges = new ArrayList<Integer>();
		if(chalId != null)this.challenges.add(chalId);
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

	public ArrayList<Integer> getChallenges() {
		return challenges;
	}

	public void setChallenges(ArrayList<Integer> challenges) {
		this.challenges = challenges;
	}

	public void addChallenge(Integer chalId) {
		this.challenges.add(chalId);
	}

	public void deleteChallenge(Integer chalId) {
		this.challenges.remove(chalId);
	}

	public String chalsToString() {
		String result = "{";
		for (Integer chal : challenges) {
			if (chal == challenges.get(challenges.size() - 1))
				result += "chalId: " + chal;
			else
				result += "chalId: " + chal + ", ";
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
