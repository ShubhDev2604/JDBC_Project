package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    // configure these values for your environment
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/JavaDB";
    private static final String USER = "ShubhMySQL";
    private static final String PASS = "Shubh@162630";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Should not happen if connector JAR is on classpath
            throw new ExceptionInInitializerError(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // -------------------- READ (all) --------------------
    public List<User> findAll() throws SQLException {
        String sql = "SELECT id, name, dob, email, phone_no, created_at, updated_at FROM firstjdbctable";
        List<User> result = new ArrayList<>();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User u = mapRowToUser(rs);
                result.add(u);
            }
        }
        return result;
    }

    // -------------------- READ (by id) --------------------
    public Optional<User> findById(int id) throws SQLException {
        String sql = "SELECT id, name, dob, email, phone_no, created_at, updated_at FROM firstjdbctable WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToUser(rs));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    // -------------------- CREATE --------------------
    // returns generated id or -1 on failure
    public int insert(User user) throws SQLException {
        String sql = "INSERT INTO firstjdbctable (name, dob, email, phone_no) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, user.getName());
            pst.setDate(2, user.getDob());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPhoneNo());

            int affected = pst.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet keys = pst.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    user.setId(id);
                    return id;
                } else {
                    return -1;
                }
            }
        }
    }

    // -------------------- UPDATE --------------------
    // returns true if something was updated
    public boolean update(User user) throws SQLException {
        if (user.getId() == 0) throw new IllegalArgumentException("User id is required for update");
        String sql = "UPDATE firstjdbctable SET name = ?, dob = ?, email = ?, phone_no = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, user.getName());
            pst.setDate(2, user.getDob());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPhoneNo());
            pst.setInt(5, user.getId());

            int r = pst.executeUpdate();
            return r > 0;
        }
    }

    // -------------------- DELETE --------------------
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM firstjdbctable WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            int r = pst.executeUpdate();
            return r > 0;
        }
    }

    // helper to map resultset -> User
    private User mapRowToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Date dob = rs.getDate("dob");
        String email = rs.getString("email");
        String phone = rs.getString("phone_no");
        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");
        return new User(id, name, dob, email, phone, created, updated);
    }
}
