package com.employeeaccess;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAccessServiceTest {

    EmployeeAccessService service = new EmployeeAccessService();

    @Test
    void testNormalEligibleEmployee() throws Exception {

        Employee employee = new Employee(
                "EMP001",
                "Arun",
                25,
                "IT",
                "Full-Time",
                3,
                true,
                true
        );

        EligibilityStatus result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.ELIGIBLE,
                result);
    }

    @Test
    void testBoundaryAge21() throws Exception {

        Employee employee = new Employee(
                "EMP002",
                "Kumar",
                21,
                "HR",
                "Full-Time",
                1,
                true,
                true
        );

        EligibilityStatus result =
                service.checkEligibility(
                        employee,
                        AccessLevel.INTERNAL);

        assertEquals(
                EligibilityStatus.ELIGIBLE,
                result);
    }

    @Test
    void testAgeBelow21() throws Exception {

        Employee employee = new Employee(
                "EMP003",
                "Ravi",
                20,
                "IT",
                "Full-Time",
                3,
                true,
                true
        );

        EligibilityStatus result =
                service.checkEligibility(
                        employee,
                        AccessLevel.INTERNAL);

        assertEquals(
                EligibilityStatus.NOT_ELIGIBLE,
                result);
    }

    @Test
    void testUnauthorizedDepartment() throws Exception {

        Employee employee = new Employee(
                "EMP004",
                "Suresh",
                30,
                "Marketing",
                "Full-Time",
                3,
                true,
                true
        );

        EligibilityStatus result =
                service.checkEligibility(
                        employee,
                        AccessLevel.INTERNAL);

        assertEquals(
                EligibilityStatus.NOT_ELIGIBLE,
                result);
    }

    @Test
    void testInactiveEmployee() throws Exception {

        Employee employee = new Employee(
                "EMP005",
                "Raj",
                30,
                "Finance",
                "Full-Time",
                3,
                true,
                false
        );

        EligibilityStatus result =
                service.checkEligibility(
                        employee,
                        AccessLevel.INTERNAL);

        assertEquals(
                EligibilityStatus.NOT_ELIGIBLE,
                result);
    }

    @Test
    void testInvalidEmployeeId() throws Exception {

        Employee employee = new Employee(
                "EMP006",
                "Vijay",
                30,
                "IT",
                "Full-Time",
                3,
                false,
                true
        );

        EligibilityStatus result =
                service.checkEligibility(
                        employee,
                        AccessLevel.INTERNAL);

        assertEquals(
                EligibilityStatus.NOT_ELIGIBLE,
                result);
    }

    @Test
    void testConditionalEligibility() throws Exception {

        Employee employee = new Employee(
                "EMP007",
                "Manoj",
                30,
                "IT",
                "Full-Time",
                1,
                true,
                true
        );

        EligibilityStatus result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.CONDITIONALLY_ELIGIBLE,
                result);
    }

    @Test
    void testMultipleFailures() throws Exception {

        Employee employee = new Employee(
                "EMP008",
                "Karthik",
                18,
                "Marketing",
                "Part-Time",
                1,
                false,
                false
        );

        List<String> reasons =
                service.getRejectionReasons(employee);

        assertEquals(4, reasons.size());

        assertTrue(reasons.get(0).contains("21"));
        assertTrue(reasons.get(1).contains("unauthorized"));
        assertTrue(reasons.get(2).contains("active"));
        assertTrue(reasons.get(3).contains("invalid"));
    }

    @Test
    void testInvalidAgeInput() {

        Employee employee = new Employee(
                "EMP009",
                "Test",
                0,
                "IT",
                "Full-Time",
                1,
                true,
                true
        );

        assertThrows(
                InvalidEmployeeException.class,
                () -> service.checkEligibility(
                        employee,
                        AccessLevel.INTERNAL)
        );
    }

    @Test
    void testInvalidClearance() {

        Employee employee = new Employee(
                "EMP010",
                "Test",
                25,
                "IT",
                "Full-Time",
                5,
                true,
                true
        );

        assertThrows(
                InvalidEmployeeException.class,
                () -> service.checkEligibility(
                        employee,
                        AccessLevel.INTERNAL)
        );
    }
}
