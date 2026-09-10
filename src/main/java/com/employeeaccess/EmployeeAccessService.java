package com.employeeaccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeAccessService {

    private static final List<String> AUTHORIZED_DEPARTMENTS =
            Arrays.asList("IT", "HR", "Finance", "Administration");

    public EligibilityStatus checkEligibility(
            Employee employee,
            AccessLevel requestedAccess) throws InvalidEmployeeException {

        validateEmployee(employee);

        List<String> reasons = getRejectionReasons(employee);

        if (!reasons.isEmpty()) {
            return EligibilityStatus.NOT_ELIGIBLE;
        }

        int requiredClearance = getRequiredClearance(requestedAccess);

        if (employee.getSecurityClearanceLevel() < requiredClearance) {
            return EligibilityStatus.CONDITIONALLY_ELIGIBLE;
        }

        return EligibilityStatus.ELIGIBLE;
    }

    public List<String> getRejectionReasons(Employee employee)
            throws InvalidEmployeeException {

        validateEmployee(employee);

        List<String> reasons = new ArrayList<>();

        if (employee.getAge() < 21) {
            reasons.add("Employee must be at least 21 years old.");
        }

        if (!AUTHORIZED_DEPARTMENTS.contains(employee.getDepartment())) {
            reasons.add("Employee belongs to an unauthorized department.");
        }

        if (!employee.isActive()) {
            reasons.add("Employee employment status is not active.");
        }

        if (!employee.isIdValid()) {
            reasons.add("Employee ID is invalid.");
        }

        return reasons;
    }

    private int getRequiredClearance(AccessLevel accessLevel) {

        switch (accessLevel) {

            case PUBLIC:
                return 0;

            case INTERNAL:
                return 1;

            case CONFIDENTIAL:
                return 3;

            default:
                return 0;
        }
    }

    private void validateEmployee(Employee employee)
            throws InvalidEmployeeException {

        if (employee == null) {
            throw new InvalidEmployeeException(
                    "Employee details cannot be null.");
        }

        if (employee.getEmployeeId() == null ||
                employee.getEmployeeId().trim().isEmpty()) {

            throw new InvalidEmployeeException(
                    "Employee ID cannot be empty.");
        }

        if (employee.getName() == null ||
                employee.getName().trim().isEmpty()) {

            throw new InvalidEmployeeException(
                    "Employee name cannot be empty.");
        }

        if (employee.getAge() <= 0) {
            throw new InvalidEmployeeException(
                    "Age must be greater than zero.");
        }

        if (employee.getSecurityClearanceLevel() < 0 ||
                employee.getSecurityClearanceLevel() > 3) {

            throw new InvalidEmployeeException(
                    "Security clearance level must be between 0 and 3.");
        }
    }
}
