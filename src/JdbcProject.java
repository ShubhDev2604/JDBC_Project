package src;
import java.sql.*;

public class JdbcProject {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/JavaDB";
        String uname = "ShubhMySQL";
        String pass = "Shubh@162630";
        String query = "select * from firstjdbctable";
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
            while (rs.next()) {
                String user = "id: " + rs.getInt("id") + ", " +
                        "name: " + rs.getString("name") + ", " +
                        "dob: " + rs.getDate("dob") + ", " +
                        "email: " + rs.getString("email") + ", " +
                        "phone: " + rs.getString("phone_no") + ", " +
                        "created_at: " + rs.getTimestamp("created_at") + ", " +
                        "updated_at: " + rs.getTimestamp("updated_at");
                System.out.println(user);
            }
            st.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
            return;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}
