package com.budget.problem;

public class Result {

	@Override
	public String toString() {
		return "Result [name=" + name + ", id=" + id + "]";
	}

	private String name;
	private String id;

	public Result(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

}
