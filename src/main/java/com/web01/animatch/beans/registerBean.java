package com.web01.animatch.beans;

import java.io.Serializable;

public class registerBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
    String name;
    int age;
    
    public registerBean() {}
    
    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }
    
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
    
    public void setAge(int age) { this.age = age; }
    public int getAge() { return this.age; }
}
