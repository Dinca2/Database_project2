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
        Driver.runQuery(query3);

        
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
        } //try
    } //runQuery
}

