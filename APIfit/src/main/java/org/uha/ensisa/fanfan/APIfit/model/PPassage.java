package org.uha.ensisa.fanfan.APIfit.model;

import javax.persistence.Embeddable;

@Embeddable
public class PPassage {
	
	int ppid;
	
	public PPassage() {
		
	}
	
	public PPassage(int ppid) {
		this.ppid = ppid;
	}
	
	public int getPpid() {
		return ppid;
	}

	public void setPpid(int ppid) {
		this.ppid = ppid;
	}
}
