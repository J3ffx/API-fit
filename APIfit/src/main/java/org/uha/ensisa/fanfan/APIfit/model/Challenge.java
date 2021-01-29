package org.uha.ensisa.fanfan.APIfit.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Challenge {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	int id;
	int cid;
	String name;
	int players;
	String description;
	@ElementCollection
	List<PPassage> pps = new ArrayList<PPassage>();
	@ElementCollection
	List<Segment> segs = new ArrayList<Segment>();
	
	public Challenge() {
	}
	
	public Challenge(int cid, String name, String desc) {
		this.cid = cid;
		this.name = name;
		this.players = 1;
		this.description = desc;
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

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
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

	public void addPP(PPassage pp) {
		this.pps.add(pp);
	}
	
	public void addSeg(Segment seg) {
		this.segs.add(seg);
	}
	
	public String getDesc() {
		return description;
	}

	public void setDesc(String desc) {
		this.description = desc;
	}

	public List<PPassage> getPps() {
		return pps;
	}

	public void setPps(List<PPassage> pps) {
		this.pps = pps;
	}

	public List<Segment> getSegs() {
		return segs;
	}

	public void setSegs(List<Segment> segs) {
		this.segs = segs;
	}

	@Override
	public String toString() {
		return "{cid: " + cid + ", name: " + name + ", players: " + players + ", description: "+ description +"}";
	}
	
	
}
