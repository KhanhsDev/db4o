import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialistManager {

    // Thêm chuyên gia vào cơ sở dữ liệu
    public void addSpecialist(Specialist specialist) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO specialist (specialistId, fullName, gender, jobTitle, experienceYears) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, specialist.getSpecialistId());
            stmt.setString(2, specialist.getFullName());
            stmt.setString(3, specialist.getGender());
            stmt.setString(4, specialist.getJobTitle());
            stmt.setInt(5, specialist.getExperienceYears());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding specialist: " + e.getMessage());
        }
    }

    // Lấy chuyên gia theo ID
    public Specialist getSpecialistById(String specialistId) {
        Specialist specialist = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM specialist WHERE specialistId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, specialistId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                specialist = new Specialist(
                    rs.getString("specialistId"),
                    rs.getString("fullName"),
                    rs.getString("gender"),
                    rs.getString("jobTitle"),
                    rs.getInt("experienceYears")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving specialist: " + e.getMessage());
        }
        return specialist;
    }

    // Cập nhật thông tin chuyên gia
    public void updateSpecialist(Specialist specialist) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "UPDATE specialist SET fullName = ?, gender = ?, jobTitle = ?, experienceYears = ? WHERE specialistId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, specialist.getFullName());
            stmt.setString(2, specialist.getGender());
            stmt.setString(3, specialist.getJobTitle());
            stmt.setInt(4, specialist.getExperienceYears());
            stmt.setString(5, specialist.getSpecialistId());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating specialist: " + e.getMessage());
        }
    }

    // Xóa chuyên gia khỏi cơ sở dữ liệu
    public void deleteSpecialist(String specialistId) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "DELETE FROM specialist WHERE specialistId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, specialistId);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error deleting specialist: " + e.getMessage());
        }
    }

    // Lấy tất cả chuyên gia từ cơ sở dữ liệu
    public List<Specialist> getAllSpecialists() {
        List<Specialist> specialists = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM specialist";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Specialist specialist = new Specialist(
                    rs.getString("specialistId"),
                    rs.getString("fullName"),
                    rs.getString("gender"),
                    rs.getString("jobTitle"),
                    rs.getInt("experienceYears")
                );
                specialists.add(specialist);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving specialists: " + e.getMessage());
        }
        return specialists;
    }
}
