import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {
    
    // Add a customer to the database
    public void addCustomer(Customer customer) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO customers (customerId, fullName, phoneNumber, birthDate, gender, points, membershipLevel) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getCustomerId());
            stmt.setString(2, customer.getFullName());
            stmt.setString(3, customer.getPhoneNumber());
            stmt.setString(4, customer.getBirthDate());
            stmt.setString(5, customer.getGender());
            stmt.setInt(6, customer.getPoints());
            stmt.setString(7, customer.getMembershipLevel());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    // Retrieve a customer by their ID
    public Customer getCustomerById(String customerId) {
        Customer customer = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM customers WHERE customerId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customerId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                    rs.getString("customerId"),
                    rs.getString("fullName"),
                    rs.getString("phoneNumber"),
                    rs.getString("birthDate"),
                    rs.getString("gender"),
                    rs.getInt("points"),
                    rs.getString("membershipLevel")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving customer: " + e.getMessage());
        }
        return customer;
    }

    // Update an existing customer's information
    public void updateCustomer(Customer customer) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "UPDATE customers SET fullName = ?, phoneNumber = ?, birthDate = ?, gender = ?, points = ?, membershipLevel = ? WHERE customerId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getPhoneNumber());
            stmt.setString(3, customer.getBirthDate());
            stmt.setString(4, customer.getGender());
            stmt.setInt(5, customer.getPoints());
            stmt.setString(6, customer.getMembershipLevel());
            stmt.setString(7, customer.getCustomerId());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    // Delete a customer from the database by ID
    public void deleteCustomer(String customerId) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "DELETE FROM customers WHERE customerId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customerId);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    // Retrieve all customers from the database
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM CUSTOMERS";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println("Executing query: " + sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getString("customerId"),
                    rs.getString("fullName"),
                    rs.getString("phoneNumber"),
                    rs.getString("birthDate"),
                    rs.getString("gender"),
                    rs.getInt("points"),
                    rs.getString("membershipLevel")
                );
                customers.add(customer);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
        return customers;
    }
}
