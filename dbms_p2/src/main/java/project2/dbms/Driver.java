package project2.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver {

    static final String URL = "jdbc:mysql://localhost:3306/employees";
    static final String USR = "root";
    static final String PSW = "password";
    public static void main(String[] args) throws SQLException {
        
        
        String query = "SELECT d.dept_name, COUNT(CASE e.gender WHEN 'F' THEN 1 ELSE NULL END) / COUNT(CASE e.gender WHEN 'M' THEN 1 ELSE NULL END) AS female_male_ratio FROM departments d JOIN dept_emp de ON d.dept_no = de.dept_no JOIN employees e ON e.emp_no = de.emp_no GROUP BY d.dept_name ORDER BY female_male_ratio DESC";
        String query2 = "SELECT e.first_name, e. last_name, e. emp_no, dm. from_date, dm. to_date, if( dm. to_date = '9999-01-01', DATEDIFF(from_date, CURDATE()) * -1/7 , DATEDIFF(from_date, to_date) * -1/7) AS worked FROM dept_manager dm JOIN employees e ON e.emp_no = dm.emp_no ORDER BY worked DESC";
        String query3 = "SELECT first_set.dept_name, first_set.born_in_50s, first_set.average_salary_50s, second_set.born_in_60s, second_set.average_salary_60s FROM (SELECT d.dept_name, COUNT(DISTINCT e.emp_no) AS born_in_50s, AVG(s.salary) AS average_salary_50s FROM departments d JOIN dept_emp de ON d.dept_no = de.dept_no JOIN employees e ON e.emp_no = de.emp_no JOIN salaries s ON s.emp_no = e.emp_no WHERE e.birth_date >= '1950-01-01' AND e.birth_date < '1960-01-01' GROUP BY d.dept_name) AS first_set INNER JOIN (SELECT d.dept_name, COUNT(DISTINCT e.emp_no) AS born_in_60s, AVG(s.salary) AS average_salary_60s FROM departments d JOIN dept_emp de ON d.dept_no = de.dept_no JOIN employees e ON e.emp_no = de.emp_no JOIN salaries s ON s.emp_no = e.emp_no WHERE e.birth_date >= '1960-01-01' AND e.birth_date < '1970-01-01' GROUP BY d.dept_name) AS second_set ON first_set.dept_name = second_set.dept_name";
        String query4 = "SELECT e.emp_no, e.first_name, e.last_name, e.gender, e.birth_date FROM dept_manager dm JOIN employees e ON dm.emp_no = e.emp_no JOIN salaries s ON s.emp_no = e.emp_no where salary > 80000 and gender = 'F'and birth_date < '1990-01-01' group by e.emp_no;";
        String query5 = "SELECT first_set.emp_no, first_set.dept_no, second_set.emp_no FROM (SELECT e.emp_no, d.dept_no, de.to_date, de.from_date FROM employees e JOIN dept_emp de ON e.emp_no = de.emp_no JOIN departments d ON d.dept_no = de.dept_no where e.emp_no = 10009) AS first_set INNER JOIN (SELECT e.emp_no, d.dept_no, de.from_date, de.to_date FROM employees e JOIN dept_emp de ON e.emp_no = de.emp_no JOIN departments d ON d.dept_no = de.dept_no where e.emp_no = 11545) AS second_set ON first_set.dept_no = second_set.dept_no and (first_set.from_date <= second_set.to_date) and (first_set.to_date >= second_set.from_date);";
        String query6 = "SELECT e1, d1, c2.emp_no AS e3, d2, e2 FROM (SELECT e1.emp_no AS e1, all_emp.dept_no AS d1, all_emp.emp_no, all_emp.from_date, all_emp.to_date FROM (SELECT e.emp_no, d.dept_no, de.from_date, de.to_date FROM employees e JOIN dept_emp de ON e.emp_no = de.emp_no JOIN departments d ON d.dept_no = de.dept_no where e.emp_no = 13141) AS e1 INNER JOIN (SELECT e.emp_no, d.dept_no, de.from_date, de.to_date FROM employees e JOIN dept_emp de ON e.emp_no = de.emp_no JOIN departments d ON d.dept_no = de.dept_no) AS all_emp ON e1.dept_no = all_emp.dept_no and (e1.from_date <= all_emp.to_date) and (e1.to_date >= all_emp.from_date)) AS c1 JOIN (SELECT e2.emp_no AS e2, all_emp.dept_no AS d2, all_emp.emp_no, all_emp.from_date, all_emp.to_date FROM (SELECT e.emp_no, d.dept_no, de.from_date, de.to_date FROM employees e JOIN dept_emp de ON e.emp_no = de.emp_no JOIN departments d ON d.dept_no = de.dept_no where e.emp_no = 13111) AS e2 INNER JOIN (SELECT e.emp_no, d.dept_no, de.from_date, de.to_date FROM employees e JOIN dept_emp de ON e.emp_no = de.emp_no JOIN departments d ON d.dept_no = de.dept_no) AS all_emp ON e2.dept_no = all_emp.dept_no and (e2.from_date <= all_emp.to_date) and (e2.to_date >= all_emp.from_date)) AS c2 ON c1.emp_no = c2.emp_no AND c1.d1 != c2.d2";
        Driver.runQuery(query6);

        
    } //main

    private static void runQuery(String query) {
        try {
            Connection c = DriverManager.getConnection(URL, USR, PSW);
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(query);
            //Retrieving the ResultSetMetaData object
            ResultSetMetaData rsmd = result.getMetaData();
            //getting the column type
            int column_count = rsmd.getColumnCount();
            int count = 0;
            while (result.next() && count < 100) {
                String data = "";
                for (int i = 1; i <= column_count; i++) {
                    data += result.getString(i) + ":";
                } //for
                System.out.println(data);
                count++;
            } //while
            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.out.println("no results");

        } //try
    } //runQuery
}

