package project2.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver {

    static final String URL = "jdbc:mysql://localhost:3306/employees";
    static final String USR = "root";
    static final String PSW = "password";
    public static void main(String[] args) throws SQLException {
        
        
        String query = "SELECT d.dept_name, COUNT(CASE e.gender WHEN 'F' THEN 1 ELSE NULL END) / COUNT(CASE e.gender WHEN 'M' THEN 1 ELSE NULL END) AS female_male_ratio FROM departments d JOIN dept_emp de ON d.dept_no = de.dept_no JOIN employees e ON e.emp_no = de.emp_no GROUP BY d.dept_name ORDER BY female_male_ratio DESC";
        String query2 = "SELECT e.first_name, e. last_name, e. emp_no, dm. from_date, dm. to_date, if( dm. to_date = '9999-01-01', DATEDIFF(from_date, CURDATE()) * -1/7 , DATEDIFF(from_date, to_date) * -1/7) AS worked FROM dept_manager dm JOIN employees e ON e.emp_no = dm.emp_no ORDER BY worked DESC";
        Driver.runQuery(query2);

        
    } //main

    private static void runQuery(String query) {
        try {
            Connection c = DriverManager.getConnection(URL, USR, PSW);
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(query);
            int count = 0;
            while (result.next() && count < 100) {
                String data = "";
                for (int i = 1; i <= 2; i++) {
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

