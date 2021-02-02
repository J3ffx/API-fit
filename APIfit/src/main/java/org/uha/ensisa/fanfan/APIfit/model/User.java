package org.uha.ensisa.fanfan.APIfit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
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
	List<Tag> tags;

	public User() {
	}

	public User(int id, String username, String password, boolean admin, Integer chalId) {
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.tags = new ArrayList<Tag>();
		if (chalId != null)
			this.tags.add(new Tag(chalId, 0));
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
		List<Integer> list = new ArrayList<Integer>();
		for (Tag tag : tags) {
			list.add(tag.getChalId());
		}
		return (ArrayList<Integer>) list;
	}

	public void deleteChallenge(Integer chalId) {
		for (Tag tag : tags) {
			if (tag.chalId == chalId)
				tags.remove(tag);
		}
	}

	public String chalsToString() {
		String result = "{";
		for (Tag tag : tags) {
			if (tag.getChalId() == tags.get(tags.size() - 1).getChalId())
				result += "chalId: " + tag.getChalId() + "distTraveled: " + tag.getDistTraveled();
			else
				result += "chalId: " + tag.getChalId() + "distTraveled: " + tag.getDistTraveled() + ", ";
		}
		result += "}";
		return result;
	}

	public void addChall(int chalId) {
		tags.add(new Tag(chalId, 0));

	}

	@Override
	public String toString() {
		String result = "{username: " + username + ", password: " + password + ", challenges: " + chalsToString() + "}";
		return result;
	}

	public void move(int chalId, int move) {
		for (Tag tag : tags) {
			if (tag.chalId == chalId)
				tag.setDistTraveled(tag.getDistTraveled() + move);
		}

	}

}
