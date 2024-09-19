package org.wolf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.wolf.model.Employee;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Employee> empList = prepareEmployeeList();

        // 1. Group the Employees by city.
        System.out.println("Group the Employees by city");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()))
                .forEach((key, value) -> System.out.println(key + ":" + value));

        // 2. Group the Employees by age.
        System.out.println("Group the Employees by age");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()))
                .forEach((key, value) -> System.out.println(key + ": " + value));

        // 3. Find the count of male and female employees present in the organization.
        System.out.println("Find the count of male and female employees present in the organization");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()))
                .forEach((key, value) -> System.out.println(key + ": " + value));

        // 4. Print the names of all departments in the organization.
        System.out.println("Print the names of all departments in the organization");
        empList.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);

        // 5. Print employee details whose age is greater than 28.
        System.out.println("Print employee details whose age is greater than 28");
        empList.stream().filter(employee -> employee.getAge() > 28).forEach(System.out::println);

        // 6. Find maximum age of employee.
        System.out.println("Find maximum age of employee");
        OptionalInt optionalMaxAge = empList.stream().mapToInt(Employee::getAge).max();
        if (optionalMaxAge.isPresent())
            System.out.println("Max age: " + optionalMaxAge.getAsInt());

        // 7. Print Average age of Male and Female Employees.
        System.out.println("Print Average age of Male and Female Employees");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)))
                .forEach((key, value) -> System.out.println(key + ": " + value));

        // 8. Print the number of employees in each department.
        System.out.println("Print the number of employees in each department");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .forEach((key, value) -> System.out.println(key + ": " + value));

        // 9. Find oldest employee.
        System.out.println("Find oldest employee");
        Optional<Employee> optionalOldestEmployee = empList.stream()
                .max(Comparator.comparingInt(Employee::getAge));
        optionalOldestEmployee.ifPresent(System.out::println);

        // 10. Find the youngest female employee.
        System.out.println("Find the youngest female employee");
        Optional<Employee> optionalYoungestFemaleEmp = empList.stream()
                .filter(emp -> emp.getGender().equalsIgnoreCase("F"))
                .min(Comparator.comparingInt(Employee::getAge));
        System.out.println(optionalYoungestFemaleEmp);

        // 11. Find employees whose age is greater than 30 and less than 28.
        System.out.println("Find employees whose age is greater than 30 and less than 30");
        empList.stream()
                .collect(Collectors.partitioningBy(emp -> emp.getAge() > 28))
                .forEach((key, list) -> list.forEach(emp -> System.out.println(key + ":" + emp)));

        // 12. Find the department name which has the highest number of employees.
        System.out.println("Find the department name which has the highest number of employees");
        Map.Entry<String, Long> department = empList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).get();
        System.out.println(department.getKey() + ": " + department.getValue());

        // 13. Find if there are any employees from HR Department.
        System.out.println("Find if there are any employees from HR Department");
        Optional<Employee> hrDeptEmployee = empList.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("HR"))
                .findAny();
        hrDeptEmployee.ifPresent(System.out::println);

        // 14. Find the department names that these employees work for, where the number of employees in the department is over 1.
        System.out.println("Find the department names that these employees work for, where the number of employees in the department is over 1");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1).toList()
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));


        // 15. Find distinct department names that employees work for.
        System.out.println("Find distinct department names that employees work for");
        empList.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);

        // 16. Find all employees who live in ‘Bangalore’ city, sort them by their name and print the names of employees.
        System.out.println("Find all employees who live in ‘Bangalore’ city, sort them by their name and print the names of employees");
        empList.stream()
                .filter(emp -> emp.getCity().equalsIgnoreCase("Bangalore"))
                .sorted(Comparator.comparing(Employee::getName))
                .map(Employee::getName)
                .forEach(System.out::println);

        // 17. No of employees in the organisation.
        System.out.println("No of employees in the organisation");
        System.out.println(empList.stream().count());

        // 18. Find employee count in every department
        System.out.println("Find employee count in every department");
        empList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .forEach((key, value) -> System.out.println(key + ": " + value));

        // 19. Find the department which has the highest number of employees.
        System.out.println("Find the department which has the highest number of employees");
        Optional<String> dept = empList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
        dept.ifPresent(System.out::println);

        // 20. Sorting a Stream by age and name fields.
        System.out.println("Sorting a Stream by age and name fields");
        empList.stream()
                .sorted(Comparator.comparing(Employee::getAge).thenComparing(Employee::getName))
                .forEach(System.out::println);

        // 21. Highest experienced employees in the organization.
        System.out.println("Highest experienced employees in the organization");
        Optional<Employee> employee = empList.stream().min(Comparator.comparing(Employee::getYearOfJoining));
        employee.ifPresent(System.out::println);


        // 22. Print average and total salary of the organization.
        System.out.println("Print average and total salary of the organization");
        OptionalDouble averageSalary = empList.stream().mapToDouble(Employee::getSalary).average();
        averageSalary.ifPresent(System.out::println);
        Double totalSalary = empList.stream().mapToDouble(Employee::getSalary).sum();
        System.out.println(totalSalary);

        // 23. Print Average salary of each department.
        System.out.println("Print Average salary of each department");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .forEach((key, value) -> System.out.println(key + ": " + value));

        // 24. Find Highest salary in the organization.
        System.out.println("Find Highest salary in the organization");
        OptionalDouble highestSalary = empList.stream().mapToDouble(Employee::getSalary).max();
        highestSalary.ifPresent(System.out::println);

        // 25. Find Second-Highest salary in the organization.
        System.out.println("Find Second-Highest salary in the organization");
        Optional<Employee> shEmployee = empList.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .skip(1).findFirst();
        shEmployee.ifPresent(System.out::println);

        // 26. Nth Highest salary.
        System.out.println("Nth (3rd) Highest salary");
        Optional<Employee> thirdHSalEmp = empList.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .skip(2).findFirst();
        thirdHSalEmp.ifPresent(System.out::println);

        // 27. Find highest paid salary in the organization based on gender.
        System.out.println("Find highest paid salary in the organization based on gender");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.maxBy((emp1, emp2) -> (int) (emp1.getSalary() - emp2.getSalary()))))
                .forEach((key, value) -> System.out.println(key + ": " + value.get().getSalary()));

        // 28. Find lowest paid salary in the organization based in the organization.
        System.out.println("Find lowest paid salary in the organization based in the organization");
        OptionalDouble lowestSalary = empList.stream()
                .mapToDouble(Employee::getSalary)
                .min();
        lowestSalary.ifPresent(System.out::println);


        // 29. Sort the employees salary in the organization in ascending order
        System.out.println("Sort the employees salary in the organization in ascending order");
        empList.stream()
                .mapToDouble(Employee::getSalary).sorted()
                .forEach(System.out::println);

        // 30. Sort the employees salary in the organization in descending order.
        System.out.println("Sort the employees salary in the organization in descending order");
        empList.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .mapToDouble(Employee::getSalary)
                .forEach(System.out::println);

        // 31. Highest salary based on department.
        System.out.println("Highest salary based on department");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.minBy((emp1, emp2) -> (int) (emp1.getSalary() - emp2.getSalary()))))
                .forEach((key, emp) -> System.out.println(key + ": " + emp.get().getSalary()));

        // 32. Print list of employee’s second-highest record based on department
        System.out.println("Print list of employee’s second-highest record based on department");
        empList.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list -> list.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                                        .skip(1).findFirst())))
                .forEach((key, optEmp) -> System.out.println(key + ": " + (optEmp.isPresent() ? optEmp.get().getSalary() : "")));


        // 33. Sort the employees salary in each department in ascending order
        System.out.println("Sort the employees salary in each department in ascending order");
        empList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(Collectors.toList(),
                        list -> list.stream().sorted(Comparator.comparingDouble(Employee::getSalary)))))
                .forEach((key, streamEmployee) -> {
                    System.out.println(key + ": ");
                    System.out.println(streamEmployee.collect(Collectors.toList()));
                });
        // 34. Sort the employees salary in each department in descending order
        System.out.println("Sort the employees salary in each department in descending order");
        empList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(Collectors.toList(),
                        list -> list.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()))))
                .forEach((key, streamEmp) -> {
                    System.out.println(key + ": ");
                    System.out.println(streamEmp.collect(Collectors.toList()));
                });
    }

    private static List<Employee> prepareEmployeeList() {
        File employeeDataFile = new File("src/main/resources/employee-data.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(employeeDataFile, new TypeReference<List<Employee>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}