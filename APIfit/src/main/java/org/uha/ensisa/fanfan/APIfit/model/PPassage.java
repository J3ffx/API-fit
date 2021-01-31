package org.uha.ensisa.fanfan.APIfit.model;

import javax.persistence.Embeddable;

@Embeddable
public class PPassage {
	
	int ppid;
	String name;
	
	public PPassage() {
		
	}
	
	public PPassage(int ppid, String name) {
		this.name = name;
		this.ppid = ppid;
	}
	
	public int getPpid() {
		return ppid;
	}

	public void setPpid(int ppid) {
		this.ppid = ppid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "{ppid: " + ppid + ", name: " + name + "}";
	}
	
	
}
