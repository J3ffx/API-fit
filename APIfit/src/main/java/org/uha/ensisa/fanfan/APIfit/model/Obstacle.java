package org.uha.ensisa.fanfan.APIfit.model;

import javax.persistence.Embeddable;

@Embeddable
public class Obstacle {
	
	int oid;
	String epreuve;
	
	public Obstacle() {
		
	}
	
	public Obstacle(int oid, String epreuve) {
		this.oid = oid;
		this.epreuve = epreuve;
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

	@Override
	public String toString() {
		return "Obstacle [epreuve=" + epreuve + "]";
	}
	
	
}
