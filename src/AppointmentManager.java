import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;


public class AppointmentManager {
    
    // Thêm cuộc hẹn vào cơ sở dữ liệu
    public void addAppointment(Appointment appointment) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO APPOINTMENT (appointmentId, dateTime, status, customerNote) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, appointment.getAppointmentId());
            stmt.setString(2, appointment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));  // Đảm bảo đúng định dạng
            stmt.setString(3, appointment.getStatus());
            stmt.setString(4, appointment.getCustomerNote());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding appointment: " + e.getMessage());
        }
    }

    // Lấy cuộc hẹn theo ID
    public Appointment getAppointmentById(String appointmentId) {
        Appointment appointment = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM APPOINTMENT WHERE appointmentId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, appointmentId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                appointment = new Appointment(
                    rs.getString("appointmentId"),
                    rs.getString("dateTime"),
                    rs.getString("status"),
                    rs.getString("customerNote")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving appointment: " + e.getMessage());
        }
        return appointment;
    }

    // Cập nhật thông tin cuộc hẹn
    public void updateAppointment(Appointment appointment) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "UPDATE APPOINTMENT SET dateTime = ?, status = ?, customerNote = ? WHERE appointmentId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, appointment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));  // Đảm bảo đúng định dạng
            stmt.setString(2, appointment.getStatus());
            stmt.setString(3, appointment.getCustomerNote());
            stmt.setString(4, appointment.getAppointmentId());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating appointment: " + e.getMessage());
        }
    }

    // Xóa cuộc hẹn khỏi cơ sở dữ liệu
    public void deleteAppointment(String appointmentId) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "DELETE FROM APPOINTMENT WHERE appointmentId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, appointmentId);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
        }
    }

    // Lấy tất cả cuộc hẹn từ cơ sở dữ liệu
    public List<Appointment> getAllAppointments() {
        List<Appointment> APPOINTMENT = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM APPOINTMENT";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println("Executing query: " + sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment(
                    rs.getString("appointmentId"),
                    rs.getString("dateTime"),
                    rs.getString("status"),
                    rs.getString("customerNote")
                );
                APPOINTMENT.add(appointment);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving APPOINTMENT: " + e.getMessage());
        }
        return APPOINTMENT;
    }
}
