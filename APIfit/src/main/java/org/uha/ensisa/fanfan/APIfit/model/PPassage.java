package org.uha.ensisa.fanfan.APIfit.model;

import java.util.ArrayList;

public class PPassage {
	ArrayList<Segment> segAv;
	ArrayList<Segment> segAp;
	
	public PPassage() {
		this.segAv = new ArrayList<Segment>();
		this.segAp = new ArrayList<Segment>();
	}
	
	public void addSegAv(Segment seg) {
		this.segAv.add(seg);
	}
	
	public void addSegAp(Segment seg) {
		this.segAp.add(seg);
	}
}
