import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionPackageManager {

    // Thêm gói khuyến mãi vào cơ sở dữ liệu
    public void addPromotionPackage(PromotionPackage promotionPackage) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO PromotionPackage (packageId, packageName, description, discount) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, promotionPackage.getPackageId());
            stmt.setString(2, promotionPackage.getPackageName());
            stmt.setString(3, promotionPackage.getDescription());
            stmt.setDouble(4, promotionPackage.getDiscount());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding promotion package: " + e.getMessage());
        }
    }

    // Lấy gói khuyến mãi theo ID
    public PromotionPackage getPromotionPackageById(String packageId) {
        PromotionPackage promotionPackage = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM PromotionPackage WHERE packageId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, packageId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                promotionPackage = new PromotionPackage(
                    rs.getString("packageId"),
                    rs.getString("packageName"),
                    rs.getString("description"),
                    rs.getDouble("discount")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving promotion package: " + e.getMessage());
        }
        return promotionPackage;
    }

    // Cập nhật thông tin gói khuyến mãi
    public void updatePromotionPackage(PromotionPackage promotionPackage) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "UPDATE PromotionPackage SET packageName = ?, description = ?, discount = ? WHERE packageId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, promotionPackage.getPackageName());
            stmt.setString(2, promotionPackage.getDescription());
            stmt.setDouble(3, promotionPackage.getDiscount());
            stmt.setString(4, promotionPackage.getPackageId());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating promotion package: " + e.getMessage());
        }
    }

    // Xóa gói khuyến mãi khỏi cơ sở dữ liệu
    public void deletePromotionPackage(String packageId) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "DELETE FROM PromotionPackage WHERE packageId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, packageId);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error deleting promotion package: " + e.getMessage());
        }
    }

    // Lấy tất cả gói khuyến mãi từ cơ sở dữ liệu
    public List<PromotionPackage> getAllPromotionPackages() {
        List<PromotionPackage> promotionPackages = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT * FROM PromotionPackage";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println("Executing query: " + sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PromotionPackage promotionPackage = new PromotionPackage(
                    rs.getString("packageId"),
                    rs.getString("packageName"),
                    rs.getString("description"),
                    rs.getDouble("discount")
                );
                promotionPackages.add(promotionPackage);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving promotion packages: " + e.getMessage());
        }
        return promotionPackages;
    }
}
