
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    static List<Employee> employeeList = new ArrayList<>();
    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        EmployeeFactory employeeFactory = new EmployeeFactory();
        employeeList = employeeFactory.getAllEmployee();
        
        List<Project> projectsList = new ArrayList<>();
        for (Employee employee : employeeList) {
        	projectsList.addAll(employee.getProjects());
        }

        System.out.println("List of Employees :");
        for(Employee employee: employeeList) {
        	System.out.println(employee);
        }
        
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("1. List of distinct Projects are: ");
        listDistinctProjectsNonAscending(projectsList).forEach(System.out::println);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("2. Print full name of any employee whose firstName starts with ‘A’. ");
        employeeNameStartingWithA(employeeList);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("3. List of all employee who joined in year 2023 (year to be extracted from employee id i.e., 1st 4 characters) ");
		employeejoinedIn2023(employeeList);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("4. Sort employees based on firstName, for same firstName sort by salary.");
		sortedEmployeeOnTheBasisOfFirstName(employeeList);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("5. Print names of all employee with 3rd highest salary.");
		employeesWithThirdHighestSalary(employeeList);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("6. Print Minimum Salary.");
		int minSalary = minimumSalary(employeeList);
		System.out.println(minSalary);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("7. Print list of all employee with min salary.");
		employeeWithMinimumSalary(employeeList);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("8. List of people working on more than 2 projects.");
		employeeWithMoreThan2Project(employeeList);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("9. Count of total laptops assigned to the employees.");
		long countOfLaptopAssigned = laptopAssigned(employeeList);
		System.out.println(countOfLaptopAssigned);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("10. Count of all projects with Robert Downey Jr as PM.");	
		long countOfProjectsUnderRobertDowneyJr = countOfProjectsUnderRobertDowneyJr(projectsList);
		System.out.println(countOfProjectsUnderRobertDowneyJr);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("11. List of all projects with Robert Downey Jr as PM.");
		projectsUnderRobertDowneyJr(projectsList);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("12. List of all people working with Robert Downey Jr.");
		employeesUnderRobert(employeeList);
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("13. Create a map based on this data, the key should be the year of joining, and value should be list of all the employees who joined the particular year.");
		employeeMappedByJoiningYear(employeeList).forEach((year, employees) -> {
            System.out.println(year);
            employees.forEach(System.out::println);
        });
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("14. Create a map based on this data, the key should be year of joining and value should be the count of people joined in that particular year.");
		employeeMappedByCounyAndJoiningYear(employeeList).forEach((year, count)-> {
			System.out.println(year + " : "+ count);
		});
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//    1. List all distinct project in non-ascending order. 
    public static List<String> listDistinctProjectsNonAscending(List<Project> projectsList) {
        return projectsList.stream()
                .map(Project::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//    2. Print full name of any employee whose firstName starts with ‘A’.
    public static void employeeNameStartingWithA (List<Employee> employeelist){
    	 employeeList.stream()
    			.filter(employee -> employee.getFirstName().startsWith("A"))
    			.forEach(System.out::println);;
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//    3. List of all employee who joined in year 2023 (year to be extracted from employee id i.e., 1st 4 characters)
    public static void employeejoinedIn2023 (List<Employee> employeeList) {
    	employeeList.stream()
    	.filter(employee ->employee.getId().startsWith("2023"))
    	.forEach(System.out::println);
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//    4. Sort employees based on firstName, for same firstName sort by salary.
    public static void sortedEmployeeOnTheBasisOfFirstName (List<Employee> employeeList) {
    	employeeList.stream()
    	.sorted(Comparator.comparing(Employee::getFirstName))
    	.forEach(System.out::println);
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------- 
//    5. Print names of all employee with 3rd highest salary.
    public static void employeesWithThirdHighestSalary(List<Employee> employeeList) {
        List<Employee> employeesWithThirdHighestSalary = employeeList.stream()
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .distinct()
                .skip(2)
                .limit(1)
                .flatMap(employee -> employeeList.stream()
                        .filter(e -> e.getSalary() == employee.getSalary()))
                .collect(Collectors.toList());

        employeesWithThirdHighestSalary.forEach(System.out::println);
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------- 
//  6. Print min salary.
    public static int minimumSalary (List<Employee> employeeList) {
    	return employeeList.stream()
    			.mapToInt(Employee::getSalary)
    			.min()
    			.orElse(Integer.MAX_VALUE);
}
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//  7. Print list of all employees with min salary.
    public static void employeeWithMinimumSalary (List<Employee> employeeList) {
    	employeeList.stream()
      .filter(employee -> employee.getSalary() == minimumSalary(employeeList))
      .forEach(System.out::println);
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//  8. List of people working on more than 2 projects.
    public static void employeeWithMoreThan2Project (List<Employee> employeeList) {
    	employeeList.stream()
        .filter(employee -> employee.getProjects().size() > 2)
        .forEach(System.out::println);
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//  9. Count of total laptops assigned to the employees.
    public static long laptopAssigned (List<Employee> employeeList) {
    	return employeeList.stream()
    			.mapToInt(Employee::getTotalLaptopsAssigned)
    			.sum();
    	}
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//  10. Count of all projects with Robert Downey Jr as PM.
    public static long countOfProjectsUnderRobertDowneyJr (List<Project> projectsList) {
    	return projectsList.stream()
      		   .filter(project -> project.getProjectManager().equals("Robert Downey Jr"))
      		   .distinct()
      		   .count();
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//  11. List of all projects with Robert Downey Jr as PM.
    public static void projectsUnderRobertDowneyJr (List<Project> projectsList) {
    	projectsList.stream()
    	.filter(project -> project.getProjectManager().equals("Robert Downey Jr"))
    	.map(Project::getName)
    	.distinct()
    	.sorted()
    	.collect(Collectors.toList())
    	.forEach(System.out::println);
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//  12. List of all people working with Robert Downey Jr
    public static List<Employee> employeesUnderRobert(List<Employee> employeeList) {
        return employeeList.stream()
                .filter(employee -> employee.getProjects().stream()
                        .anyMatch(project -> project.getProjectManager().equals("Robert Downey Jr")))
                .distinct()
                .collect(Collectors.toList());
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//  13. Create a map based on this data, the key should be the year of joining, and value should be list of all the employees who joined the particular year.
    public static Map<Object, List<Employee>> employeeMappedByJoiningYear(List<Employee> employeeList) {
        return employeeList.stream()
                .collect(Collectors.groupingBy(employee -> Integer.parseInt(employee.getId().substring(0, 4))));
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  
//  14. Create a map based on this data, the key should be year of joining and value should be the count of people joined in that particular year.
    public static Map<Object, Long> employeeMappedByCounyAndJoiningYear(List<Employee> employeeList) {
        return employeeList.stream()
                .collect(Collectors.groupingBy(employee -> employee.getId().substring(0, 4), Collectors.counting()));
        
        
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------  

}     


