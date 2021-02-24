package com.budget.problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * Provides sufficient test coverage for {@link DigitalIndia} class.
 * 
 * @author gaurav.vishal
 */
public class DigitalIndiaTest {

	/**
	 * Interactive test that checks if valid parameters are passed or not while
	 * creating {@link Team} object.
	 * 
	 * @exception NullPointerException
	 * 
	 * @passCriteria {@link TeamName} should be passed.
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void team_constructor_exception_case1() {
		new Team(null, 1);
	}

	/**
	 * Interactive test that checks if valid parameters are passed or not while
	 * creating {@link Team} object.
	 * 
	 * @exception RuntimeException
	 * 
	 * @passCriteria budget should be greater than 0.
	 * 
	 */
	@Test(expected = RuntimeException.class)
	public void team_constructor_exception_case2() {
		new Team(null, 0);
	}

	/**
	 * Interactive test that checks if valid parameters are passed or not while
	 * creating {@link DigitalIndia} object.
	 * 
	 * @exception RuntimeException
	 * 
	 * @passCriteria company should have at least 1 team in it.
	 * 
	 */
	@Test(expected = RuntimeException.class)
	public void company_constructor_exception_case1() {
		new DigitalIndia(null);
	}

	/**
	 * Interactive test that checks if valid parameters are passed or not while
	 * creating {@link Employee} object.
	 * 
	 * @exception NullPointerException
	 * 
	 * @passCriteria valid employee name should be passed.
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void employee_constructor_exception_case1() {
		Team redTeam = new Team(TeamName.RED, 5);
		new Employee(null, redTeam, 10);
	}

	/**
	 * Interactive test that checks if valid parameters are passed or not while
	 * creating {@link Employee} object.
	 * 
	 * @exception NullPointerException
	 * 
	 * @passCriteria valid {@link TeamName} name should be passed.
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void employee_constructor_exception_case2() {
		new Employee("Gaurav", null, 10);
	}

	/**
	 * Interactive test that checks if valid parameters are passed or not while
	 * creating {@link Employee} object.
	 * 
	 * @exception RuntimeException
	 * 
	 * @passCriteria Employee's salary must be in +ve digits.
	 * 
	 */
	@Test(expected = RuntimeException.class)
	public void employee_constructor_exception_case3() {
		Team redTeam = new Team(TeamName.RED, 5);
		new Employee("Gaurav", redTeam, -10);
	}

	/**
	 * Interactive test that checks if {@link Team} has enough budget to accommodate
	 * new Employee with asked salary or not.
	 * 
	 * @testcase
	 *           <p>
	 *           If Team has a budget of 5($) and it has no subTeam or employees
	 *           than the team can hire an employee with salary 5.
	 *           <p>
	 *           If Team has a budget of 5($) and it has already an employee whose
	 *           salary is 5 than team can't hire another employee.
	 *           <p>
	 * 
	 * @exception None
	 * 
	 * @passCriteria Employee's salary must be in +ve digits.
	 * 
	 */
	@Test
	public void validate_and_add_employee_case1() {
		Team redTeam = new Team(TeamName.RED, 5);
		Company company = new DigitalIndia(Arrays.asList(redTeam));
		Employee employee1 = new Employee("Gaurav", redTeam, 5);
		Boolean canHireEmployee = company.isBudgetOk(employee1);
		Assert.assertTrue(canHireEmployee);
		if (canHireEmployee) {
			company.addEmployee(employee1);
		}
		Employee employee2 = new Employee("Gaurav", redTeam, 5);
		canHireEmployee = company.isBudgetOk(employee2);
		Assert.assertFalse(canHireEmployee);
	}

	/**
	 * Interactive test that checks if {@link Team} has enough budget to accommodate
	 * new Employee with asked salary or not.
	 * 
	 * @testcase
	 *           <p>
	 *           If Team has a budget of 5($) and it has no subTeam or employees
	 *           than the team can hire an employee with salary 3.
	 *           <p>
	 *           If Team has a budget of 5($) and it has already an employee whose
	 *           salary is 3 than team can hire maximum of 2 {@link Employee} whose
	 *           salary can be 1($) each or a max of one {@link Employee} with
	 *           salary offered as 2$.
	 *           <p>
	 * 
	 * 
	 * @exception None
	 * 
	 * @passCriteria Employee's salary must be in +ve digits.
	 * 
	 */
	@Test
	public void validate_and_add_employee_case2() {
		Team redTeam = new Team(TeamName.RED, 5);
		Company company = new DigitalIndia(Arrays.asList(redTeam));
		Employee employee1 = new Employee("Gaurav", redTeam, 3);
		Boolean canHireEmployee = company.isBudgetOk(employee1);
		Assert.assertTrue(canHireEmployee);
		if (canHireEmployee) {
			company.addEmployee(employee1);
		}
		Employee employee2 = new Employee("Vishal", redTeam, 2);
		canHireEmployee = company.isBudgetOk(employee2);
		Assert.assertTrue(canHireEmployee);
		if (canHireEmployee) {
			company.addEmployee(employee1);
		}
		// 3rd Employee can't get hired, budget issues.
		Employee employee3 = new Employee("Vishal", redTeam, 1);
		canHireEmployee = company.isBudgetOk(employee3);
		Assert.assertFalse(canHireEmployee);
	}

	/**
	 * Interactive test that checks if {@link Team} has enough budget to accommodate
	 * new Employee with asked salary or not.
	 * 
	 * @testcase
	 *           <p>
	 *           <strong>Company Name</strong> Digital India
	 *           <p>
	 *           <strong>Head Team</strong> {@link TeamName#RED} with Budget 100.
	 *           <p>
	 *           <strong>SubTeams</strong> <br>
	 *           {@link TeamName#GREEN} with Budget 40. <br>
	 *           {@link TeamName#BLUE} with Budget 30. <br>
	 *           {@link TeamName#BLACK} with Budget 30.
	 * 
	 * @exception None
	 * 
	 * @passCriteria Employee's salary must be in +ve digits.
	 * 
	 */
	@Test
	public void validate_and_add_employee_case3() {
		Team redTeam = new Team(TeamName.RED, 100);
		Team greenSubTeam = new Team(redTeam, TeamName.GREEN, 40);
		Team blueSubTeam = new Team(redTeam, TeamName.BLUE, 30);
		Team blackSubTeam = new Team(redTeam, TeamName.BLACK, 30);
		Company company = new DigitalIndia(Arrays.asList(redTeam));
		// Adding 7 Employees of salary 5 each in greenSubTeam, it will have budget of
		// 5 after hiring 7 employees.
		for (int i = 1; i <= 7; i++) {
			Employee employee = new Employee("Gaurav-" + i, greenSubTeam, 5);
			Boolean canHireEmployee = company.isBudgetOk(employee);
			if (canHireEmployee) {
				company.addEmployee(employee);
			}
		}
		Assert.assertEquals(greenSubTeam.getEmployees().size(), 7);
		// Adding 4 Employees of salary 7 each in blueSubTeam, it will have budget of 12
		// left after hiring 4 employees.
		for (int i = 1; i <= 4; i++) {
			Employee employee = new Employee("Vishal-" + i, blueSubTeam, 7);
			Boolean canHireEmployee = company.isBudgetOk(employee);
			if (canHireEmployee) {
				company.addEmployee(employee);
			}
		}
		Assert.assertEquals(blueSubTeam.getEmployees().size(), 4);
		// Adding 4 Employees of salary 8 each in blackSubTeam, since blackSubTeam has
		// maximum of 30$ budget it can hire only 3 employees.
		for (int i = 1; i <= 4; i++) {
			Employee employee = new Employee("Gaurav-" + i, blackSubTeam, 8);
			Boolean canHireEmployee = company.isBudgetOk(employee);
			if (canHireEmployee) {
				company.addEmployee(employee);
			}
		}
		Assert.assertEquals(blackSubTeam.getEmployees().size(), 3);
		// Parent Team is left with 100 - (7X5) - (4X7) - (3X8) = 13.
		// Adding 5 Employees of salary 3 each in redTeam, since redTeam has
		// maximum of 13$ budget it can hire only 4 employees.
		for (int i = 1; i <= 5; i++) {
			Employee employee = new Employee("Gaurav-" + i, redTeam, 3);
			Boolean canHireEmployee = company.isBudgetOk(employee);
			if (canHireEmployee) {
				company.addEmployee(employee);
			}
		}
		Assert.assertEquals(redTeam.getEmployees().size(), 4);
	}

	/**
	 * Interactive test that checks if {@link Team} has enough budget to accommodate
	 * new Employee with asked salary or not.
	 * 
	 * @testcase
	 *           <p>
	 *           <strong>Company Name</strong> Digital India
	 *           <p>
	 *           <strong>Red Team</strong> {@link TeamName#RED} with Budget 5.
	 *           <p>
	 *           <strong>Blue Team</strong> {@link TeamName#BLUE} with Budget 2,
	 *           Parent is {@link TeamName#RED}.
	 *           <p>
	 *           <strong>Green Team</strong> {@link TeamName#GREEN} with Budget 2,
	 *           Parent is {@link TeamName#RED}.
	 *           <p>
	 * 
	 * @exception None
	 * 
	 * @passCriteria Employee's salary must be in +ve digits.
	 * 
	 */
	@Test
	public void validate_and_add_employee_case4() {
		Team redTeam = new Team(TeamName.RED, 5);
		Team greenSubTeam = new Team(redTeam, TeamName.GREEN, 2);
		Team blueSubTeam = new Team(redTeam, TeamName.BLUE, 2);
		Company company = new DigitalIndia(Arrays.asList(redTeam));
		// Employee 1 is under the Red Team with salary of 2.
		Employee employee1 = new Employee("Gaurav", redTeam, 2);
		Boolean canHireEmployee = company.isBudgetOk(employee1);
		Assert.assertTrue(canHireEmployee);
		if (canHireEmployee) {
			company.addEmployee(employee1);
		}
		Assert.assertEquals(redTeam.getEmployees().size(), 1);
		// Employee 2 is under the Blue Team with salary of 2.
		Employee employee2 = new Employee("Gaurav", blueSubTeam, 2);
		canHireEmployee = company.isBudgetOk(employee2);
		Assert.assertTrue(canHireEmployee);
		if (canHireEmployee) {
			company.addEmployee(employee2);
		}
		Assert.assertEquals(blueSubTeam.getEmployees().size(), 1);
		// Employee 3 is under the Green Team with salary of 1.
		Employee employee3 = new Employee("Gaurav", greenSubTeam, 2);
		canHireEmployee = company.isBudgetOk(employee3);
		Assert.assertTrue(canHireEmployee);
		if (canHireEmployee) {
			company.addEmployee(employee3);
		}
		Assert.assertEquals(greenSubTeam.getEmployees().size(), 1);
		// Employee 4 is under the Green Team with salary of 1.
		// In the above situation, Employee 4 should not be added. Even though it will
		// not go over the Green team’s budget of 2 with Employees 3 and 4, it will go
		// over the Red Team’s budget of 5 with Employees 1, 2, 3, and 4.
		Employee employee4 = new Employee("Gaurav", greenSubTeam, 1);
		canHireEmployee = company.isBudgetOk(employee4);
		Assert.assertFalse(canHireEmployee);
		if (canHireEmployee) {
			company.addEmployee(employee4);
		}
		Assert.assertEquals(greenSubTeam.getEmployees().size(), 1);
	}

}
