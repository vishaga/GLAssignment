package com.budget.problem;

/***
 * Company Interface, which can check its budget of its {@link Team} before
 * hiring an {@link Employee}.
 * 
 * @author gaurav.vishal
 *
 */
public interface Company {

	boolean isBudgetOk(Employee employee);

	void addEmployee(Employee employee);

}
