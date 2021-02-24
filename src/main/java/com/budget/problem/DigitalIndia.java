package com.budget.problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/***
 * DigitalIndia is the name of a company which implements {@link Company}
 * interface.
 * 
 * @author gaurav.vishal
 *
 */
public class DigitalIndia implements Company {

	private String name;
	private List<Team> teams;

	public DigitalIndia(List<Team> teams, String name) {
		super();
		if (teams == null || teams.isEmpty()) {
			throw new RuntimeException("Teams can't be null or empty.");
		}
		this.teams = teams;
		this.name = name;
	}

	public DigitalIndia(List<Team> teams) {
		super();
		if (teams == null || teams.isEmpty()) {
			throw new RuntimeException("Teams can't be null or empty.");
		}
		this.teams = teams;
		this.name = "Diital India Pvt Ltd";
	}

	@Override
	public boolean isBudgetOk(Employee employee) {
		int teamBudget = employee.getAssociatedTeam().getBudget();
		// If new Employee salary itself is going out of budget of a team/subTeam.
		if (employee.getSalary() > teamBudget) {
			return false;
		}
		int teamRemainingBudget = this.getAvailableBudget(employee.getAssociatedTeam(), null, employee.getSalary());
		return teamRemainingBudget >= 0;
	}

	@Override
	public void addEmployee(Employee employee) {
		employee.getAssociatedTeam().addEmployee(employee);
	}

	public List<Team> getTeams() {
		return teams;
	}

	@Override
	public String toString() {
		return this.name;
	}

	/***
	 * <p>
	 * This method checks if the current team have budget to accommodate new
	 * employee, return false if it doesn't have enough budget else it recursively
	 * check if its parent team has enough budget or not.
	 * 
	 * @param team
	 * @param skipSubTeam
	 * @param newSalary
	 * @return
	 */
	private int getAvailableBudget(Team team, Team skipSubTeam, int newSalary) {
		List<Employee> employees = new ArrayList<>();
		Queue<Team> queue = new LinkedList<>();
		queue.offer(team);
		while (!queue.isEmpty()) {
			Team currentTeam = queue.poll();
			if (currentTeam == skipSubTeam) {
				continue;
			}
			employees.addAll(currentTeam.getEmployees());
			queue.addAll(currentTeam.getSubTeams());
		}
		int currentCost = employees.stream().mapToInt(emp -> emp.getSalary()).sum() + newSalary;
		int availableBudget = team.getBudget() - currentCost;
		if (availableBudget < 0) {
			return availableBudget;
		}
		// While check till root of the node or if any of it's parent team doesn't have
		// enough budget remaining.
		while (team.getParentTeam().isPresent() || availableBudget < 0) {
			Team parent = team.getParentTeam().get();
			currentCost = getAvailableBudget(parent, team, currentCost);
			availableBudget = parent.getBudget() - currentCost;
			if (availableBudget >= 0) {
				return availableBudget;
			}
			team = team.getParentTeam().get();
		}
		return availableBudget;
	}

}
