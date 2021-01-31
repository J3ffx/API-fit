package org.uha.ensisa.fanfan.APIfit.model;

import javax.persistence.Embeddable;

@Embeddable
public class Segment {

	int sid;
	String name;
	Obstacle ob;
	PPassage ppAv;
	PPassage ppAp;
	int dist;

	public Segment() {
	}

	public Segment(int sid, String name, PPassage ppAv, PPassage ppAp, int dist) {
		this.sid = sid;
		this.name = name;
		this.ob = null;
		this.ppAv = ppAv;
		this.ppAp = ppAp;
		this.dist = dist;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public PPassage getPpAv() {
		return ppAv;
	}

	public void setPpAv(PPassage ppAv) {
		this.ppAv = ppAv;
	}

	public PPassage getPpAp() {
		return ppAp;
	}

	public void setPpAp(PPassage ppAp) {
		this.ppAp = ppAp;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public Obstacle getOb() {
		return ob;
	}

	public void setOb(Obstacle ob) {
		this.ob = ob;
	}

	@Override
	public String toString() {
		return "{sid: " + sid + ", name: " + name + ", ob: " + ob + ", ppAv: " + ppAv + ", ppAp: " + ppAp + ", dist: "
				+ dist + "}";
	}

}
