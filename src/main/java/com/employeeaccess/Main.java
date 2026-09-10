package com.employeeaccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        EmployeeAccessService service = new EmployeeAccessService();

        List<Employee> employees = new ArrayList<>();

        System.out.print("Enter number of employees: ");
        int numberOfEmployees = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numberOfEmployees; i++) {

            System.out.println("\nEmployee " + i);

            System.out.print("Employee ID: ");
            String id = scanner.nextLine();

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Department: ");
            String department = scanner.nextLine();

            System.out.print("Employment Type: ");
            String employmentType = scanner.nextLine();

            System.out.print("Security Clearance Level (0-3): ");
            int clearance = scanner.nextInt();

            System.out.print("Is Employee ID Valid? (true/false): ");
            boolean idValid = scanner.nextBoolean();

            System.out.print("Is Employment Active? (true/false): ");
            boolean active = scanner.nextBoolean();

            scanner.nextLine();

            Employee employee = new Employee(
                    id,
                    name,
                    age,
                    department,
                    employmentType,
                    clearance,
                    idValid,
                    active
            );

            employees.add(employee);
        }

        System.out.println("\n========== ACCESS EVALUATION ==========");

        for (Employee employee : employees) {

            System.out.println("\nEmployee: " + employee.getName());
            System.out.println("ID: " + employee.getEmployeeId());

            try {

                System.out.print(
                        "Requested Access (PUBLIC/INTERNAL/CONFIDENTIAL): ");

                String accessInput = scanner.nextLine().toUpperCase();

                AccessLevel accessLevel =
                        AccessLevel.valueOf(accessInput);

                EligibilityStatus status =
                        service.checkEligibility(employee, accessLevel);

                System.out.println("Status: " + status);

                if (status == EligibilityStatus.NOT_ELIGIBLE) {

                    List<String> reasons =
                            service.getRejectionReasons(employee);

                    System.out.println("Reasons for rejection:");

                    for (String reason : reasons) {
                        System.out.println("- " + reason);
                    }
                }

                if (status ==
                        EligibilityStatus.CONDITIONALLY_ELIGIBLE) {

                    System.out.println(
                            "Condition: Security clearance is insufficient "
                            + "for the requested access level.");
                }

            } catch (InvalidEmployeeException e) {

                System.out.println(
                        "Invalid employee: " + e.getMessage());

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid access level entered.");
            }
        }

        scanner.close();
    }
}
