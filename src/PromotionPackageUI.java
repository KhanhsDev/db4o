import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PromotionPackageUI extends JFrame {
    private JTextField tfPackageId, tfPackageName, tfDescription, tfDiscountedPrice;
    private JTextArea taPackageInfo;
    private JButton btnAdd, btnUpdate, btnDelete, btnView;

    private PromotionPackageManager promotionPackageManager;
    private JList<String> promotionPackageList;
    private DefaultListModel<String> listModel;

    public PromotionPackageUI() {
        promotionPackageManager = new PromotionPackageManager();  // Khởi tạo PromotionPackageManager

        // JFrame configuration
        setTitle("Quản lý Gói Khuyến Mãi - Spa");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for promotion package list at the top
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        // DefaultListModel to hold promotion package names
        listModel = new DefaultListModel<>();  // Initialize the listModel here
        promotionPackageList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(promotionPackageList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for promotion package information form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));  // Adjusted spacing for readability

        // Promotion package information fields
        formPanel.add(new JLabel("Mã Gói:"));
        tfPackageId = new JTextField();
        formPanel.add(tfPackageId);

        formPanel.add(new JLabel("Tên Gói:"));
        tfPackageName = new JTextField();
        formPanel.add(tfPackageName);

        formPanel.add(new JLabel("Mô Tả:"));
        tfDescription = new JTextField();
        formPanel.add(tfDescription);

        formPanel.add(new JLabel("Giá Khuyến Mãi:"));
        tfDiscountedPrice = new JTextField();
        formPanel.add(tfDiscountedPrice);

        // Panel for action buttons (Add, Update, Delete) in a single row below the list
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        btnAdd = new JButton("Thêm Gói Khuyến Mãi");
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Cập Nhật Gói Khuyến Mãi");
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Xóa Gói Khuyến Mãi");
        buttonPanel.add(btnDelete);

        btnView = new JButton("Xem Gói Khuyến Mãi");
        buttonPanel.add(btnView);

        // TextArea to show promotion package details (for feedback)
        taPackageInfo = new JTextArea(5, 40);
        taPackageInfo.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(taPackageInfo);

        // Add components to JFrame
        add(listPanel, BorderLayout.NORTH);   // Promotion package list at the top
        add(formPanel, BorderLayout.CENTER);  // Form in the center
        add(infoScrollPane, BorderLayout.EAST);  // Info/feedback area on the right
        add(buttonPanel, BorderLayout.SOUTH);  // Buttons at the bottom

        // Load promotion packages into list
        loadPromotionPackageList();  // This should now be safe

        // Listen for button clicks
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPromotionPackage();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePromotionPackage();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePromotionPackage();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPromotionPackage();
            }
        });
    }

    // Method to load promotion package list
    private void loadPromotionPackageList() {
        // Load all promotion packages into the list
        listModel.clear();
        System.out.println("Loading promotion packages...");
        List<PromotionPackage> promotionPackages = promotionPackageManager.getAllPromotionPackages();
        for (PromotionPackage promotionPackage : promotionPackages) {
            // Display promotion package information
            String promotionPackageInfo = String.format(
                "Mã Gói: %s - Tên Gói: %s - Giá: %.2f",
                promotionPackage.getPackageId(),
                promotionPackage.getPackageName(),
                promotionPackage.getDiscount()
            );
            listModel.addElement(promotionPackageInfo);  // Add info to the list
        }
    }

    // Add promotion package
    private void addPromotionPackage() {
        String packageId = tfPackageId.getText();
        String packageName = tfPackageName.getText();
        String description = tfDescription.getText();
        double discountedPrice = Double.parseDouble(tfDiscountedPrice.getText());

        PromotionPackage promotionPackage = new PromotionPackage(packageId, packageName, description, discountedPrice);
        promotionPackageManager.addPromotionPackage(promotionPackage);
        taPackageInfo.setText("Gói khuyến mãi đã được thêm!");
        loadPromotionPackageList();  // Reload the list after adding a new promotion package
    }

    // Update promotion package
    private void updatePromotionPackage() {
        String packageId = tfPackageId.getText();
        String packageName = tfPackageName.getText();
        String description = tfDescription.getText();
        double discountedPrice = Double.parseDouble(tfDiscountedPrice.getText());

        // Create PromotionPackage object
        PromotionPackage promotionPackage = new PromotionPackage(packageId, packageName, description, discountedPrice);
        promotionPackageManager.updatePromotionPackage(promotionPackage);  // Use updatePromotionPackage with PromotionPackage object
        taPackageInfo.setText("Gói khuyến mãi đã được cập nhật!");
        loadPromotionPackageList();  // Reload the list after updating the promotion package
    }

    // Delete promotion package
    private void deletePromotionPackage() {
        String packageId = tfPackageId.getText();
        promotionPackageManager.deletePromotionPackage(packageId);
        taPackageInfo.setText("Gói khuyến mãi đã bị xóa!");
        loadPromotionPackageList();  // Reload the list after deleting a promotion package
    }

    // View promotion package details
    private void viewPromotionPackage() {
        String packageId = tfPackageId.getText();
        PromotionPackage promotionPackage = promotionPackageManager.getPromotionPackageById(packageId);
        if (promotionPackage != null) {
            taPackageInfo.setText("Thông tin gói khuyến mãi:\n" +
                    "Mã gói: " + promotionPackage.getPackageId() + "\n" +
                    "Tên gói: " + promotionPackage.getPackageName() + "\n" +
                    "Mô tả: " + promotionPackage.getDescription() + "\n" +
                    "Giá khuyến mãi: " + promotionPackage.getDiscount() + "\n");
        } else {
            taPackageInfo.setText("Gói khuyến mãi không tồn tại!");
        }
    }

    public static void main(String[] args) {
        PromotionPackageUI ui = new PromotionPackageUI();
        ui.setVisible(true);
    }
}
