import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:h2:file:D:/LEARN/CT2/baitap/spa_database";  // Đảm bảo đường dẫn đúng
    private static final String USER = "sa";  // Tên người dùng
    private static final String PASSWORD = ""; // Mật khẩu (nếu có)

    // Phương thức để khởi tạo kết nối cơ sở dữ liệu
    public static void init() {
        try (Connection conn = getConnection()) {
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }

    // Phương thức lấy kết nối
    public static Connection getConnection() throws SQLException {
        try {
            // Tải driver JDBC cho H2
            Class.forName("org.h2.Driver");

            // Mở kết nối
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Lỗi kết nối: " + e.getMessage());
        }
    }
}
