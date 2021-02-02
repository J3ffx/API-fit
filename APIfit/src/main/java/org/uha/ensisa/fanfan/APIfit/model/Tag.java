package org.uha.ensisa.fanfan.APIfit.model;

import javax.persistence.Embeddable;

@Embeddable
public class Tag {
	int chalId;
	int distTraveled;

	public Tag(){
	}
	
	public Tag(Integer chalId, int distTraveled) {
		this.chalId = chalId;
		this.distTraveled = distTraveled;
	}

	public int getChalId() {
		return chalId;
	}

	public void setChalId(int chalId) {
		this.chalId = chalId;
	}

	public int getDistTraveled() {
		return distTraveled;
	}

	public void setDistTraveled(int distTraveled) {
		this.distTraveled = distTraveled;
	}

}
