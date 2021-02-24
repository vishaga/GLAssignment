package com.budget.problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/***
 * <p>
 * Team class which can club list of {@link Employee} working under the name of
 * it and every {@link Team} can be a sub team working under the umbrella of
 * Parent team and also every parent team can have its own employees working for
 * it. Since every team can have list of sub team and every sub team within team
 * should be unique {@link Team#equals(Object)} and {@link Team#hashCode()}
 * ensures uniqueness.
 * 
 * @author gaurav.vishal
 *
 */
public class Team {

	/**
	 * A {@link Team} can have only sub team and no direct employee under it, in
	 * this case employee list can be empty object to avoid null case.
	 */
	private List<Employee> employees = new ArrayList<>();

	/**
	 * A {@link Team} (lowest level of Team) can have only employees and no sub team
	 * under it, in this case subTeam can be empty object to avoid null case.
	 * <p>
	 * every subTeam under parent team must be unique.
	 * 
	 */
	private Set<Team> subTeams = new HashSet<>();
	private TeamName teamName;

	/**
	 * There can be a team which is the parent team of all or sub teams.
	 */
	private Optional<Team> parentTeam;

	/**
	 * EVery {@link Team} can have its own budget.
	 */
	private final int totalBudget;

	/***
	 * <p>
	 * Parent {@link Team} construction where it can have only its {@link TeamName}
	 * and its budget.
	 * 
	 * @param teamName
	 * @param budget
	 */
	public Team(TeamName teamName, int budget) {
		super();
		Objects.requireNonNull(teamName, "TeamName must be passed.");
		if (budget <= 0) {
			throw new RuntimeException("Budget must be greater than 0");
		}
		this.teamName = teamName;
		this.totalBudget = budget;
		this.parentTeam = Optional.empty();
	}

	/***
	 * <p>
	 * Sub {@link Team} construction where it must have its Parent {@link Team} ,
	 * its {@link TeamName} and its budget.
	 * 
	 * @param parentTeam
	 * @param teamName
	 * @param budget
	 */
	public Team(Team parentTeam, TeamName teamName, int budget) {
		super();
		Objects.requireNonNull(teamName, "TeamName must be passed.");
		this.teamName = teamName;
		this.totalBudget = budget;
		this.parentTeam = Optional.ofNullable(parentTeam);
		this.parentTeam.ifPresent(parent -> {
			parentTeam.addSubTeam(this);
		});
	}

	public TeamName getTeamName() {
		return teamName;
	}

	public int getBudget() {
		return totalBudget;
	}

	// Returns Immutable list.
	public List<Employee> getEmployees() {
		return Collections.unmodifiableList(this.employees);
	}

	// Returns Immutable list.
	public Set<Team> getSubTeams() {
		return Collections.unmodifiableSet(this.subTeams);
	}

	public void addSubTeam(Team subTeam) {
		this.subTeams.add(subTeam);
	}

	public void addEmployee(Employee employee) {
		this.employees.add(employee);
	}

	public Optional<Team> getParentTeam() {
		return parentTeam;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (teamName != other.teamName)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Team [teamName=" + teamName + ", parentTeam=" + parentTeam + "]";
	}

}
