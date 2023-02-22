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
        Driver.runQuery(query);
        
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

