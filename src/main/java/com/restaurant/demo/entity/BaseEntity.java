package com.restaurant.demo.entity;

public class BaseEntity {
	private long id;
	private String name;
	public BaseEntity(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public BaseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
