package org.uha.ensisa.fanfan.APIfit.model;

import java.util.ArrayList;

public class Segment {
	
	ArrayList<Obstacle> obs;
	PPassage ppAv;
	PPassage ppAp;
	int size;

	public Segment(PPassage ppAv, PPassage ppAp, int size) {
		this.obs = new ArrayList<Obstacle>();
		this.ppAv = ppAv;
		ppAv.addSegAp(this);
		
		this.ppAp = ppAp;
		ppAp.addSegAv(this);
		
		this.size = size;
	}

	public ArrayList<Obstacle> getObs() {
		return obs;
	}

	public void add(Obstacle ob) {
		this.obs.add(ob);
	}
}
