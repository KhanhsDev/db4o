import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class CustomerUI extends JFrame {
    private JTextField tfCustomerId, tfFullName, tfPhoneNumber, tfBirthDate, tfGender, tfPoints, tfMembershipLevel;
    private JTextArea taCustomerInfo;
    private JButton btnAdd, btnUpdate, btnDelete, btnView;

    private CustomerManager customerManager;
    private JList<String> customerList;
    private DefaultListModel<String> listModel;

    public CustomerUI() {
        customerManager = new CustomerManager();  // Initialize CustomerManager

        // JFrame configuration
        setTitle("Quản lý Khách Hàng - Spa");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for customer list at the top
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        // DefaultListModel to hold customer names
        listModel = new DefaultListModel<>();  // Initialize the listModel here
        customerList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(customerList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for customer information form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 2, 10, 10));  // Adjusted spacing for readability

        // Customer information fields
        formPanel.add(new JLabel("Mã Khách Hàng:"));
        tfCustomerId = new JTextField();
        formPanel.add(tfCustomerId);

        formPanel.add(new JLabel("Họ Tên:"));
        tfFullName = new JTextField();
        formPanel.add(tfFullName);

        formPanel.add(new JLabel("Số Điện Thoại:"));
        tfPhoneNumber = new JTextField();
        formPanel.add(tfPhoneNumber);

        formPanel.add(new JLabel("Ngày Sinh:"));
        tfBirthDate = new JTextField();
        formPanel.add(tfBirthDate);

        formPanel.add(new JLabel("Giới Tính:"));
        tfGender = new JTextField();
        formPanel.add(tfGender);

        formPanel.add(new JLabel("Tổng Điểm:"));
        tfPoints = new JTextField();
        formPanel.add(tfPoints);

        formPanel.add(new JLabel("Hạng Thành Viên:"));
        tfMembershipLevel = new JTextField();
        formPanel.add(tfMembershipLevel);

        // TextArea to show customer details (for feedback)
        taCustomerInfo = new JTextArea(5, 40);
        taCustomerInfo.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(taCustomerInfo);

        // Add components to JFrame
        add(listPanel, BorderLayout.NORTH);   // Customer list at the top
        add(formPanel, BorderLayout.CENTER);  // Form in the center
        add(infoScrollPane, BorderLayout.EAST);  // Info/feedback area on the right

        // Panel for action buttons (Add, Update, Delete) in a single row below the list
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        btnAdd = new JButton("Thêm Khách Hàng");
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Cập Nhật");
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Xóa");
        buttonPanel.add(btnDelete);

        btnView = new JButton("Xem Khách Hàng");
        buttonPanel.add(btnView);

        add(buttonPanel, BorderLayout.SOUTH);  // Buttons at the bottom

        // Load customers into list
        loadCustomerList();  // This should now be safe

        // Listen for button clicks
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // viewCustomer();
                viewCustomerByName();
            }
        });

        // Add ListSelectionListener to handle row selection
        customerList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Check if the selection is done
                    String selectedValue = customerList.getSelectedValue();
                    if (selectedValue != null) {
                        // Extract customerId from selected value (assuming the format "Mã KH: <customerId>")
                        String customerId = selectedValue.split(" - ")[0].split(": ")[1];
                        fillCustomerDetails(customerId);
                    }
                }
            }
        });
    }

    // Method to load customer list
    private void loadCustomerList() {
        // Load all customers into the list
        listModel.clear();
        List<Customer> customers = customerManager.getAllCustomers();
        for (Customer customer : customers) {
            // Display customer information
            String customerInfo = String.format(
                "Mã KH: %s - Họ Tên: %s - SĐT: %s - Ngày Sinh: %s - Giới Tính: %s - Điểm: %d - Hạng: %s",
                customer.getCustomerId(),
                customer.getFullName(),
                customer.getPhoneNumber(),
                customer.getBirthDate(),
                customer.getGender(),
                customer.getPoints(),
                customer.getMembershipLevel()
            );
            listModel.addElement(customerInfo); // Add info to the list
        }
    }

    // Method to fill customer details into the form fields
    private void fillCustomerDetails(String customerId) {
        Customer customer = customerManager.getCustomerById(customerId);
        if (customer != null) {
            tfCustomerId.setText(customer.getCustomerId());
            tfFullName.setText(customer.getFullName());
            tfPhoneNumber.setText(customer.getPhoneNumber());
            tfBirthDate.setText(customer.getBirthDate());
            tfGender.setText(customer.getGender());
            tfPoints.setText(String.valueOf(customer.getPoints()));
            tfMembershipLevel.setText(customer.getMembershipLevel());
        } else {
            taCustomerInfo.setText("Khách hàng không tồn tại!");
        }
    }

    // Add customer
    private void addCustomer() {
        String customerId = tfCustomerId.getText();
    
    // Kiểm tra xem ID khách hàng đã tồn tại chưa
    if (customerManager.getCustomerById(customerId) != null) {
        taCustomerInfo.setText("Mã khách hàng đã tồn tại. Vui lòng nhập mã khách hàng khác!");
        return;  // Dừng lại nếu ID đã tồn tại
    }
        String fullName = tfFullName.getText();
        String phoneNumber = tfPhoneNumber.getText();
        String birthDate = tfBirthDate.getText();
        String gender = tfGender.getText();
        int points = Integer.parseInt(tfPoints.getText());
        String membershipLevel = tfMembershipLevel.getText();

        Customer customer = new Customer(customerId, fullName, phoneNumber, birthDate, gender, points, membershipLevel);
        customerManager.addCustomer(customer);
        taCustomerInfo.setText("Khách hàng đã được thêm!");
        loadCustomerList();  // Reload the list after adding a new customer
    }

    // Update customer
    private void updateCustomer() {
        String customerId = tfCustomerId.getText();
        String fullName = tfFullName.getText();
        String phoneNumber = tfPhoneNumber.getText();
        String birthDate = tfBirthDate.getText();
        String gender = tfGender.getText();
        int points = Integer.parseInt(tfPoints.getText());
        String membershipLevel = tfMembershipLevel.getText();

        Customer customer = new Customer(customerId, fullName, phoneNumber, birthDate, gender, points, membershipLevel);
        customerManager.updateCustomer(customer);
        taCustomerInfo.setText("Khách hàng đã được cập nhật!");
        loadCustomerList();  // Reload the list after updating a customer
    }

    // Delete customer
    private void deleteCustomer() {
        String customerId = tfCustomerId.getText();
        customerManager.deleteCustomer(customerId);
        taCustomerInfo.setText("Khách hàng đã bị xóa!");
        loadCustomerList();  // Reload the list after deleting a customer
    }

    // View customer details
    private void viewCustomer() {
        String customerId = tfCustomerId.getText();
        Customer customer = customerManager.getCustomerById(customerId);
        if (customer != null) {
            taCustomerInfo.setText("Thông tin khách hàng:\n" +
                    "Mã khách hàng: " + customer.getCustomerId() + "\n" +
                    "Họ tên: " + customer.getFullName() + "\n" +
                    "Số điện thoại: " + customer.getPhoneNumber() + "\n" +
                    "Ngày sinh: " + customer.getBirthDate() + "\n" +
                    "Giới tính: " + customer.getGender() + "\n" +
                    "Tổng điểm: " + customer.getPoints() + "\n" +
                    "Hạng thành viên: " + customer.getMembershipLevel());
        } else {
            taCustomerInfo.setText("Khách hàng không tồn tại!");
        }
    }
    private void viewCustomerByName() {
        String customerName = tfFullName.getText();
        Customer customer = customerManager.getCustomerByName(customerName);
        if (customer != null) {
            taCustomerInfo.setText("Thông tin khách hàng:\n" +
                    "Mã khách hàng: " + customer.getCustomerId() + "\n" +
                    "Họ tên: " + customer.getFullName() + "\n" +
                    "Số điện thoại: " + customer.getPhoneNumber() + "\n" +
                    "Ngày sinh: " + customer.getBirthDate() + "\n" +
                    "Giới tính: " + customer.getGender() + "\n" +
                    "Tổng điểm: " + customer.getPoints() + "\n" +
                    "Hạng thành viên: " + customer.getMembershipLevel());
        } else {
            taCustomerInfo.setText("Khách hàng không tồn tại!");
        }
    }

    public static void main(String[] args) {
        CustomerUI ui = new CustomerUI();
        ui.setVisible(true);
    }
}
