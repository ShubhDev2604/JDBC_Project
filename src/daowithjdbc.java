package src;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class daowithjdbc {
    public static void main(String[] args) {
        UserDao dao = new UserDao();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nChoose: 1=list  2=insert  3=update  4=delete  5=findById  0=exit");
                System.out.print("> ");
                String cmd = scanner.nextLine().trim();
                switch (cmd) {
                    case "1":
                        listAll(dao);
                        break;
                    case "2":
                        insertInteractive(dao, scanner);
                        break;
                    case "3":
                        updateInteractive(dao, scanner);
                        break;
                    case "4":
                        deleteInteractive(dao, scanner);
                        break;
                    case "5":
                        findByIdInteractive(dao, scanner);
                        break;
                    case "0":
                        System.out.println("bye");
                        return;
                    default:
                        System.out.println("unknown command");
                }
            }
        }
    }

    private static void listAll(UserDao dao) {
        try {
            List<User> users = dao.findAll();
            if (users.isEmpty()) {
                System.out.println("no rows");
            } else {
                users.forEach(System.out::println);
            }
        } catch (SQLException e) {
            printSqlError(e);
        }
    }

    private static void insertInteractive(UserDao dao, Scanner scanner) {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter dob (YYYY-MM-DD): ");
            String dobStr = scanner.nextLine();
            Date dob = Date.valueOf(dobStr);

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();

            User u = new User(name, dob, email, phone);
            int id = dao.insert(u);
            if (id > 0) System.out.println("Inserted with id = " + id);
            else System.out.println("Insert failed");
        } catch (IllegalArgumentException ex) {
            System.out.println("Bad input: " + ex.getMessage());
        } catch (SQLException ex) {
            printSqlError(ex);
        }
    }

    private static void updateInteractive(UserDao dao, Scanner scanner) {
        try {
            System.out.print("Enter id to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Optional<User> found = dao.findById(id);
            if (!found.isPresent()) {
                System.out.println("No user with id " + id);
                return;
            }
            User existing = found.get();
            System.out.println("Existing: " + existing);

            System.out.print("Enter new name (empty to keep): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) existing.setName(name);

            System.out.print("Enter new dob (YYYY-MM-DD) (empty to keep): ");
            String dobStr = scanner.nextLine();
            if (!dobStr.isEmpty()) existing.setDob(Date.valueOf(dobStr));

            System.out.print("Enter new email (empty to keep): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) existing.setEmail(email);

            System.out.print("Enter new phone (empty to keep): ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) existing.setPhoneNo(phone);

            boolean ok = dao.update(existing);
            System.out.println(ok ? "Updated" : "No change");
        } catch (SQLException ex) {
            printSqlError(ex);
        }
    }

    private static void deleteInteractive(UserDao dao, Scanner scanner) {
        try {
            System.out.print("Enter id to delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean ok = dao.delete(id);
            System.out.println(ok ? "Deleted" : "No row with id " + id);
        } catch (SQLException ex) {
            printSqlError(ex);
        }
    }

    private static void findByIdInteractive(UserDao dao, Scanner scanner) {
        try {
            System.out.print("Enter id: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Optional<User> u = dao.findById(id);
            System.out.println(u.map(User::toString).orElse("Not found"));
        } catch (SQLException ex) {
            printSqlError(ex);
        }
    }

    private static void printSqlError(SQLException e) {
        System.err.println("SQLException: " + e.getMessage());
        System.err.println("SQLState: " + e.getSQLState());
        System.err.println("ErrorCode: " + e.getErrorCode());
    }
}
