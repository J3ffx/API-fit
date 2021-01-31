package org.uha.ensisa.fanfan.APIfit.model;

import javax.persistence.Embeddable;

@Embeddable
public class Obstacle {

	int oid;
	String name;
	String epreuve;
	int dist;

	public Obstacle() {

	}

	public Obstacle(int oid, String name, String epreuve, int dist) {
		this.name = name;
		this.oid = oid;
		this.epreuve = epreuve;
		this.dist = dist;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getEpreuve() {
		return epreuve;
	}

	public void setEpreuve(String epreuve) {
		this.epreuve = epreuve;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	@Override
	public String toString() {
		return "{name: " + name + ", epreuve: " + epreuve + ", dist: " + dist + "}";
	}



}
