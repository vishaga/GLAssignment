package com.budget.problem;

import java.util.Objects;

/***
 * <p>
 * Employee can have its name for its own existence, its salary and also the
 * team under which he/she is working.
 * 
 * @author gaurav.vishal
 *
 */
public class Employee {

	private String name;
	private Team associatedTeam;
	private int salary;

	/***
	 * <p>
	 * employee name, salary and the team under which he will be working should be
	 * known before hand.
	 * 
	 * @param name
	 * @param associatedTeam
	 * @param salary
	 */
	public Employee(String name, Team associatedTeam, int salary) {
		super();
		Objects.requireNonNull(name);
		this.name = name;
		Objects.requireNonNull(associatedTeam);
		this.associatedTeam = associatedTeam;
		if (salary <= 0) {
			throw new RuntimeException("Employee's salary be greater than 0");
		}
		this.salary = salary;
	}

	/***
	 * name of the {@link Employee}.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/***
	 * name of the {@link Team}.
	 * 
	 * @return
	 */
	public Team getAssociatedTeam() {
		return associatedTeam;
	}

	public int getSalary() {
		return salary;
	}
}
