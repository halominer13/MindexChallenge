package com.mindex.challenge.data;

import java.util.ArrayList;
import java.util.List;

import com.mindex.challenge.dao.EmployeeRepository;

/**
 * Object type that contains an employee object and an integer number of all reporting individuals
 * 
 * ASSUMPTION: Anyone who is considered to be "under" the original employee is included in this value, with "under"
 * meaning the reports of the employee's direct reports, and so on and so forth
 */
public class ReportingStructure {

    private Employee employee;
    private int numberOfReports;

    /**
     * Internal variable used to keep track of any employees down the chain of command that have been added to the report list via ID.
     * 
     * Generally there won't be any cyclical power structures or dynamics in a real environment, but it's beneficial to be insured against the off-hand chance
     */
    private List<String> recursionCheck = new ArrayList<String>();

    public ReportingStructure(){}

    /**
     * Constructor
     * 
     * @param employee: The employee who a report is being run on
     */
    public ReportingStructure(Employee employee, EmployeeRepository employeeRepository){
        this.employee = employee;
        this.numberOfReports = findAllReports(employee.getEmployeeId(), 0, employeeRepository);
    }

    public Employee getEmployee(){
        return this.employee;
    }

    public int getNumberOfReports(){
        return this.numberOfReports;
    }

    /**
     * Recursive function to find all employees that report to a given employee
     * 
     * @param employee: Employee to check direct reports of
     * @param accumulation: Total value that persists within the recursive function
     * @return: Integer value of total number of people who report to the employee in question
     */
    private int findAllReports(String id, int accumulation, EmployeeRepository employeeRepository){
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            //The employee object was null / the employee does not exist
            throw new RuntimeException("Invalid employeeId marked in database under existing employee: " + id);
        }
        final List<Employee> directReports = employee.getDirectReports();

        //The employee we're checking doesn't have anyone reporting to them
        if(directReports == null){
            return accumulation;
        }

        //For each of the direct reports of the employee in question
        for(Employee report : directReports){
            //Check to make sure that we have not analyzed this person before (only applicable in 
            //cyclical power structures and cases where someone directly reports to multiple people)
            if(!this.recursionCheck.contains(report.getEmployeeId())){
                //Add the employee to the recursionCheck list and tally their direct reports
                this.recursionCheck.add(report.getEmployeeId());
                accumulation+=1;
                accumulation = findAllReports(report.getEmployeeId(), accumulation, employeeRepository);
            }
        }
        return accumulation;
    }

    /**
     * ToString method that neatly prints out the employee ID and the number of people that report to that employee
     */
    public String toString(){
        return "{ employee ID: " + this.employee.getEmployeeId() + ", number of reports: " + this.numberOfReports;
    }
    
}
