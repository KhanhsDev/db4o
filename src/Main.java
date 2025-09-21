import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo cơ sở dữ liệu
        DatabaseManager.init();  // Đảm bảo gọi phương thức này trước khi thao tác với cơ sở dữ liệu

        // Hiển thị menu chức năng
        String[] options = { 
            "Quản lý Khách Hàng", 
            "Quản lý Dịch Vụ", 
            "Quản lý Gói Khuyến Mãi", 
            "Quản lý Cuộc Hẹn",  // Thêm mục quản lý cuộc hẹn vào menu
            "Quản lý Chuyên Gia", // Thêm mục quản lý chuyên gia vào menu
            "Thoát" 
        };
        
        int choice = JOptionPane.showOptionDialog(
            null,
            "Chọn chức năng bạn muốn sử dụng:",
            "Menu",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        // Xử lý theo lựa chọn của người dùng
        switch (choice) {
            case 0:
                // Mở giao diện quản lý khách hàng
                CustomerUI customerUI = new CustomerUI();
                customerUI.setVisible(true);
                break;
            case 1:
                // Mở giao diện quản lý dịch vụ
                ServiceUI serviceUI = new ServiceUI();  // Tạo giao diện quản lý dịch vụ nếu có
                serviceUI.setVisible(true);
                break;
            case 2:
                // Mở giao diện quản lý gói khuyến mãi
                PromotionPackageUI promotionUI = new PromotionPackageUI();  // Tạo giao diện quản lý gói khuyến mãi nếu có
                promotionUI.setVisible(true);
                break;
            case 3:
                // Mở giao diện quản lý cuộc hẹn
                AppointmentUI appointmentUI = new AppointmentUI();  // Tạo giao diện quản lý cuộc hẹn nếu có
                appointmentUI.setVisible(true);
                break;
            case 4:
                // Mở giao diện quản lý chuyên gia
                SpecialistUI specialistUI = new SpecialistUI();  // Tạo giao diện quản lý chuyên gia nếu có
                specialistUI.setVisible(true);
                break;
            case 5:
                // Thoát ứng dụng
                System.exit(0);
                break;
            default:
                // Nếu không có lựa chọn, thoát
                System.exit(0);
        }
    }
}
