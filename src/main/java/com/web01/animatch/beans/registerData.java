package com.web01.animatch.beans;

import java.io.Serializable;
import java.util.Vector;

public class registerData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector<registerBean> registers = new Vector<>();
	registerBean currrent = null;
	int index = 0;
	
	public void add(registerBean register) {
		registers.addElement(register);
	}
	
	public boolean next() {
		if(index < registers.size()) {
			currrent = registers.elementAt(index);
			index++;
			return true;
		}else {
			return false;
		}
	}
	
	public int getId () {
		return currrent.getId();
	}

	public String getName () {
		return currrent.getName();
	}

	public int getAge () {
		return currrent.getAge();
	}
}
