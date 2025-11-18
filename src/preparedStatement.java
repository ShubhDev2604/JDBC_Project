package src;
import java.sql.*;
import java.util.Scanner;

public class preparedStatement {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/JavaDB";
        String uname = "ShubhMySQL";
        String pass = "Shubh@162630";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        scanner.close();

        String query = "insert into firstjdbctable(name, dob, email, phone_no) values(?, ?, ?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Driver not found: " + e.getMessage());
            return;
        }
        try {
            Connection con = DriverManager.getConnection(url, uname, pass);
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, name);
            st.setString(2, dob);
            st.setString(3, email);
            st.setString(4, phone);
            int count = st.executeUpdate();
            if(count == 0){
                System.out.println("No records inserted");
            } else {
                System.out.println(count + " records inserted");
            }
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
