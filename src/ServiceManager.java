import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {

    // Thêm dịch vụ vào cơ sở dữ liệu
    public void addService(Service service) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO service (serviceId, serviceName, price, description) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, service.getServiceId());
            stmt.setString(2, service.getServiceName());
            stmt.setDouble(3, service.getPrice());
            stmt.setString(4, service.getDescription());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding service: " + e.getMessage());
        }
    }

    // Lấy dịch vụ theo ID
    public Service getServiceById(String serviceId) {
        Service service = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM service WHERE serviceId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, serviceId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                service = new Service(
                    rs.getString("serviceId"),
                    rs.getString("serviceName"),
                    rs.getDouble("price"),
                    rs.getString("description")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving service: " + e.getMessage());
        }
        return service;
    }

    // Cập nhật dịch vụ
    public void updateService(Service service) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "UPDATE service SET serviceName = ?, price = ?, description = ? WHERE serviceId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, service.getServiceName());
            stmt.setDouble(2, service.getPrice());
            stmt.setString(3, service.getDescription());
            stmt.setString(4, service.getServiceId());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating service: " + e.getMessage());
        }
    }

    // Xóa dịch vụ khỏi cơ sở dữ liệu
    public void deleteService(String serviceId) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "DELETE FROM service WHERE serviceId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, serviceId);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error deleting service: " + e.getMessage());
        }
    }

    // Lấy tất cả dịch vụ từ cơ sở dữ liệu
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM service";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Service service = new Service(
                    rs.getString("serviceId"),
                    rs.getString("serviceName"),
                    rs.getDouble("price"),
                    rs.getString("description")
                );
                services.add(service);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving services: " + e.getMessage());
        }
        return services;
    }
}
