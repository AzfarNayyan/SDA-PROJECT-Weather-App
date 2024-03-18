import java.sql.*;

public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/SDA", "root", "12345678");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Project");
            while (rs.next()) {
                String projectName = rs.getString("name");
                int projectId = rs.getInt("ID");
                System.out.println("Project ID: " + projectId + ", Project Name: " + projectName);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

