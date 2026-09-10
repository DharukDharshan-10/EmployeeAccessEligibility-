package com.employeeaccess;

public class Employee {

    private String employeeId;
    private String name;
    private int age;
    private String department;
    private String employmentType;
    private int securityClearanceLevel;
    private boolean idValid;
    private boolean active;

    public Employee(String employeeId, String name, int age,
                    String department, String employmentType,
                    int securityClearanceLevel,
                    boolean idValid, boolean active) {

        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.department = department;
        this.employmentType = employmentType;
        this.securityClearanceLevel = securityClearanceLevel;
        this.idValid = idValid;
        this.active = active;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public int getSecurityClearanceLevel() {
        return securityClearanceLevel;
    }

    public boolean isIdValid() {
        return idValid;
    }

    public boolean isActive() {
        return active;
    }
}
