package org.uha.ensisa.fanfan.APIfit.model;

import javax.persistence.Embeddable;

@Embeddable
public class Segment {
	int sid;
	Obstacle ob;
	PPassage ppAv;
	PPassage ppAp;
	int size;
	
	public Segment() {
		
	}

	public Segment(int sid, PPassage ppAv, PPassage ppAp, int size) {
		this.sid = sid;
		this.ob = null;
		this.ppAv = ppAv;
		this.ppAp = ppAp;
		this.size = size;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Obstacle getOb() {
		return ob;
	}

	public void setOb(Obstacle ob) {
		this.ob = ob;
	}

}
