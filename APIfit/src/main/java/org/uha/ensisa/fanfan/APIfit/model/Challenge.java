package org.uha.ensisa.fanfan.APIfit.model;

public class Challenge {

	public Challenge() {
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
	}
}
