package org.uha.ensisa.fanfan.APIfit.model;

public class Challenge {

	int id;
	String name;
	
	public Challenge(int id, String name) {
		this.id = id;
		this.name = name;
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
	
	
}
