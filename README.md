### Global Logic Assignment for Adobe project.

## Question #1
	
You are managing the budget of a company. The company is full of teams. Each team can be assigned to 0 or 1 parent team. 
Different teams can have the same parent team. Each team has a budget. Each team can have 0 or more employees. 
Each employee is assigned to 1 team. Each employee has a salary. The sum of team’s and its children teams’ employees’ salary should not go above the budget. 
Design a Company class and necessary surrounding classes like Team and Employee, where you can check whether you can add a new employee to a specific team, 
and none of the teams go above budget. Assume there will be no circular dependency of teams (for example, team A is parent of team B and team B is parent of team A). 

```java
	public interface Company {

  		boolean isBudgetOk(Employee employee);
  		void addEmployee(Employee employee);
	}
```	
	For example:
		You are creating a company with three teams:
		Red Team has a budget of 5. No parent team. Blue and Green Teams are children.
		Blue Team has a budget of 2. Red Team is the parent team. No child team.
		Green Team has a budget of 2. Red Team is the parent team. No child team.

		You are adding these employees in order:
			Employee 1 is under the Red Team with salary of 2.
			Employee 2 is under the Blue Team with salary of 2.
			Employee 3 is under the Green Team with salary of 1.
			Employee 4 is under the Green Team with salary of 1.
		In the above situation, Employee 4 should not be added. Even though it will not go over the Green team’s 
		budget of 2 with Employees 3 and 4, it will go over the Red Team’s budget of 5 with Employees 1, 2, 3, and 4.
		
		Below is a pseudocode representing the above example.
```java
		public void pseudocode() {
		  
			  Team red;
			  Team green;
			  Team blue;
			  Company company = // Creating Company with the red, blue, green teams.
			
			  // Employee 1, salary of 2, red team
			  Employee employee1 = // Employee 1, salary of 2, red team
			  if (company.isBudgetOk(employee1)) {
			    // Should add employee
			    company.addEmployee(employee1);
			  }
			
			  Employee employee2 = // Employee 2, salary of 2, blue team
			  if (company.isBudgetOk(employee2)) {
			    // Should add employee
			    company.addEmployee(employee2);
			  }
			
			  Employee employee3 = // Employee 3, salary of 1, green team
			  if (company.isBudgetOk(employee3)) {
			    // Should add employee
			    company.addEmployee(employee3);
			  }
			
			  Employee employee4 = // Employee 4, salary of 1, green team
			  if (company.isBudgetOk(employee4)) {
			    // Should NOT add employee
			    company.addEmployee(employee4);
			  }
		}
```

## Question #2

You are creating a simple message streaming class. Each message has a key and a value. 
You will need to keep the messages in order that it comes in. There will be a producer calling the offer 
method to add new messages and the consumers will be calling the next method in order to fetch the messages one by one. 

Few conditions:
	1. There will be a position id which is an incremental id which starts at 0.
	2. next(long positionId) returns the first message bigger than the position id, so next(0) will not return a message at position 0, but 1 or higher.
	3. If you keep calling next with the same position id and nothing is being offered, it should keep returning the same message. 
	4. The consumer should call the next(-1 or lower) for the first next call ever. Afterwards, it will call next with the position id returned by MessagePosition class below.
	5. If you offer two messages with the same key, you deduplicate the older one. 
	6. You should not keep more than 100 messages. For example, if you offer 101 unique key messages and then call next(-1), it should get the message at position 1 not 0.
	7. For purpose of this problem, we will NOT send more than Long.MAX_VALUE amount of messages so do not worry about overflow or if you need to reset the position id. 
	8. Assume this will be used by a singled threaded environment. 
```java	
	// Implement this class.
	public interface MessageStreamer<K, V> {

		// Adds the message 
		void offer(Message<K, V> message);
	 
		// Gets the first message larger than the positionId or return null if it does not exist. 
		MessagePosition<K, V> next(long positionId);
	}
```
```java
	public class Message<K, V> {

		// Assume this K class has correct hashcode and equals methods implemented
		private K key;
		private V value;
	}
```
```java	
	public class MessagePosition<K, V> {
		private Message<K, V> message;
		private long positionId;
	}
```
