import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ServiceUI extends JFrame {
    private JTextField tfServiceId, tfServiceName, tfPrice, tfDescription;
    private JTextArea taServiceInfo;
    private JButton btnAdd, btnUpdate, btnDelete, btnView;

    private ServiceManager serviceManager;
    private JList<String> serviceList;
    private DefaultListModel<String> listModel;

    public ServiceUI() {
        serviceManager = new ServiceManager();  // Khởi tạo ServiceManager

        // JFrame configuration
        setTitle("Quản lý Dịch Vụ - Spa");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel for service list at the top
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        // DefaultListModel to hold service names
        listModel = new DefaultListModel<>();  // Initialize the listModel here
        serviceList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(serviceList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for service information form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));  // Adjusted spacing for readability

        // Service information fields
        formPanel.add(new JLabel("Mã Dịch Vụ:"));
        tfServiceId = new JTextField();
        formPanel.add(tfServiceId);

        formPanel.add(new JLabel("Tên Dịch Vụ:"));
        tfServiceName = new JTextField();
        formPanel.add(tfServiceName);

        formPanel.add(new JLabel("Giá:"));
        tfPrice = new JTextField();
        formPanel.add(tfPrice);

        formPanel.add(new JLabel("Mô Tả:"));
        tfDescription = new JTextField();
        formPanel.add(tfDescription);

        // Panel for action buttons (Add, Update, Delete) in a single row below the list
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        btnAdd = new JButton("Thêm Dịch Vụ");
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Cập Nhật Dịch Vụ");
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Xóa Dịch Vụ");
        buttonPanel.add(btnDelete);

        btnView = new JButton("Xem Dịch Vụ");
        buttonPanel.add(btnView);

        // TextArea to show service details (for feedback)
        taServiceInfo = new JTextArea(5, 40);
        taServiceInfo.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(taServiceInfo);

        // Add components to JFrame
        add(listPanel, BorderLayout.NORTH);   // Service list at the top
        add(formPanel, BorderLayout.CENTER);  // Form in the center
        add(infoScrollPane, BorderLayout.EAST);  // Info/feedback area on the right
        add(buttonPanel, BorderLayout.SOUTH);  // Buttons at the bottom

        // Load services into list
        loadServiceList();  // This should now be safe

        // Listen for button clicks
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addService();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateService();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteService();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewService();
            }
        });

        // Add ListSelectionListener to handle row selection
        serviceList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Check if the selection is done
                    String selectedValue = serviceList.getSelectedValue();
                    if (selectedValue != null) {
                        // Extract serviceId from selected value (assuming the format "Mã Dịch Vụ: <serviceId>")
                        String serviceId = selectedValue.split(" - ")[0].split(": ")[1];
                        fillServiceDetails(serviceId);
                    }
                }
            }
        });
    }

    // Method to load service list
    private void loadServiceList() {
        // Load all services into the list
        listModel.clear();
        List<Service> services = serviceManager.getAllServices();
        for (Service service : services) {
            // Display service information
            String serviceInfo = String.format(
                "Mã Dịch Vụ: %s - Tên Dịch Vụ: %s - Giá: %.2f",
                service.getServiceId(),
                service.getServiceName(),
                service.getPrice()
            );
            listModel.addElement(serviceInfo);  // Add info to the list
        }
    }

    // Method to fill service details into the form fields
    private void fillServiceDetails(String serviceId) {
        Service service = serviceManager.getServiceById(serviceId);
        if (service != null) {
            tfServiceId.setText(service.getServiceId());
            tfServiceName.setText(service.getServiceName());
            tfPrice.setText(String.valueOf(service.getPrice()));
            tfDescription.setText(service.getDescription());
        } else {
            taServiceInfo.setText("Dịch vụ không tồn tại!");
        }
    }

    // Add service
    private void addService() {
        String serviceId = tfServiceId.getText();
        String serviceName = tfServiceName.getText();
        double price = Double.parseDouble(tfPrice.getText());
        String description = tfDescription.getText();

        Service service = new Service(serviceId, serviceName, price, description);
        serviceManager.addService(service);
        taServiceInfo.setText("Dịch vụ đã được thêm!");
        loadServiceList();  // Reload the list after adding a new service
    }

    // Update service
    private void updateService() {
        String serviceId = tfServiceId.getText();
        String serviceName = tfServiceName.getText();
        double price = Double.parseDouble(tfPrice.getText());
        String description = tfDescription.getText();

        // Create Service object
        Service service = new Service(serviceId, serviceName, price, description);
        serviceManager.updateService(service);  // Use updateService with Service object
        taServiceInfo.setText("Dịch vụ đã được cập nhật!");
        loadServiceList();  // Reload the list after updating the service
    }

    // Delete service
    private void deleteService() {
        String serviceId = tfServiceId.getText();
        serviceManager.deleteService(serviceId);
        taServiceInfo.setText("Dịch vụ đã bị xóa!");
        loadServiceList();  // Reload the list after deleting a service
    }

    // View service details
    private void viewService() {
        String serviceId = tfServiceId.getText();
        Service service = serviceManager.getServiceById(serviceId);
        if (service != null) {
            taServiceInfo.setText("Thông tin dịch vụ:\n" +
                    "Mã dịch vụ: " + service.getServiceId() + "\n" +
                    "Tên dịch vụ: " + service.getServiceName() + "\n" +
                    "Giá: " + service.getPrice() + "\n" +
                    "Mô tả: " + service.getDescription());
        } else {
            taServiceInfo.setText("Dịch vụ không tồn tại!");
        }
    }

    public static void main(String[] args) {
        ServiceUI ui = new ServiceUI();
        ui.setVisible(true);
    }
}
