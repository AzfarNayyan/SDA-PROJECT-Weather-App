package dbconnection;

import java.sql.*;

public class DBConnection {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/SDAProject", "root", "12345678");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locations");
            while (rs.next()) {
                int id = rs.getInt("id");
                String cityName = rs.getString("city");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                System.out.println("Location ID: " + id + ", City Name: " + cityName + ", Latitude: " + latitude + ", Longitude: " + longitude);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
