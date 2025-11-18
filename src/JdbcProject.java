package src;
import java.sql.*;

public class JdbcProject {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/JavaDB";
        String uname = "ShubhMySQL";
        String pass = "Shubh@162630";
        String query = "select * from firstjdbctable where name like 'a%'";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Driver not found: " + e.getMessage());
            return;
        }
        try {
            Connection con = DriverManager.getConnection(url, uname, pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
                System.out.println(rs.getString("name"));
            st.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
            return;
        }
    }
}
