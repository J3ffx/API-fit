package org.uha.ensisa.fanfan.APIfit.model;

public class Challenge {

	int id;
	String name;
	int players;
	String desc;
	
	public Challenge(int id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.players = 1;
		this.desc = desc;
		/*
		PPassage start = new PPassage();
		PPassage finish = new PPassage();
		PPassage inter = new PPassage();
		
		Segment seg1 = new Segment(start, inter, 10);
		Segment seg2 = new Segment(inter, finish, 20);
		
		Obstacle ob1 = new Obstacle("wait 10 mins");
		Obstacle ob2 = new Obstacle("go to start and come back");
		
		seg1.add(ob1);
		seg2.add(ob2);
		seg2.add(ob1);
		*/
	}

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

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}

	@Override
	public String toString() {
		return "{id: " + id + ", name: " + name + ", players: " + players + ", description: "+ desc +"}";
	}
	
	
}
