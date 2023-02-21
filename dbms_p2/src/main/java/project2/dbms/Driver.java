package project2.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/employees";
        String user_name = "root";
        String password = "password";
        String query = "SELECT * FROM Departments";
        
        try {
            Connection c = DriverManager.getConnection(url, user_name, password);
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String data = "";
                for (int i = 1; i <= 2; i++) {
                    data += result.getString(i) + ":";
                } //for
                System.out.println(data);
            } //while
            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } //try
    } //main
}