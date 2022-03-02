package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private Employee employee;
    private Double salary;
    private String effectiveDate;

    public Compensation(){}

    public Compensation(Employee employee, Double salary, String effectiveDate){
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public Employee getEmployee(){
        return employee;
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
    }

    public Double getSalary(){
        return salary;
    }

    public void setSalary(Double salary){
        this.salary = salary;
    }

    public String getEffectiveDate(){
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate){
        this.effectiveDate = effectiveDate;
    }

    public String toString(){
        return "{employee ID: " + this.employee.getEmployeeId() + ", salary: $" + this.salary + ", effective date: " + this.effectiveDate;
    }
}
