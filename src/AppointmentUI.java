import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AppointmentUI extends JFrame {
    private JTextField tfAppointmentId, tfDateTime, tfStatus, tfCustomerNote;
    private JTextArea taAppointmentInfo;
    private JButton btnAdd, btnUpdate, btnDelete, btnView;

    private AppointmentManager appointmentManager;
    private JList<String> appointmentList;
    private DefaultListModel<String> listModel;

    public AppointmentUI() {
        appointmentManager = new AppointmentManager();  // Initialize AppointmentManager

        // JFrame configuration
        setTitle("Quản lý Cuộc Hẹn - Spa");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for appointment list at the top
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        // DefaultListModel to hold appointment information
        listModel = new DefaultListModel<>();  // Initialize the listModel here
        appointmentList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(appointmentList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for appointment information form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));  // Adjusted spacing for readability

        // Appointment information fields
        formPanel.add(new JLabel("Mã Cuộc Hẹn:"));
        tfAppointmentId = new JTextField();
        formPanel.add(tfAppointmentId);

        formPanel.add(new JLabel("Ngày Giờ:"));
        tfDateTime = new JTextField();
        formPanel.add(tfDateTime);

        formPanel.add(new JLabel("Trạng Thái:"));
        tfStatus = new JTextField();
        formPanel.add(tfStatus);

        formPanel.add(new JLabel("Ghi Chú Khách Hàng:"));
        tfCustomerNote = new JTextField();
        formPanel.add(tfCustomerNote);

        // TextArea to show appointment details (for feedback)
        taAppointmentInfo = new JTextArea(5, 40);
        taAppointmentInfo.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(taAppointmentInfo);

        // Add components to JFrame
        add(listPanel, BorderLayout.NORTH);   // Appointment list at the top
        add(formPanel, BorderLayout.CENTER);  // Form in the center
        add(infoScrollPane, BorderLayout.EAST);  // Info/feedback area on the right

        // Panel for action buttons (Add, Update, Delete) in a single row below the list
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        btnAdd = new JButton("Thêm Cuộc Hẹn");
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Cập Nhật");
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Xóa");
        buttonPanel.add(btnDelete);

        btnView = new JButton("Xem Cuộc Hẹn");
        buttonPanel.add(btnView);

        add(buttonPanel, BorderLayout.SOUTH);  // Buttons at the bottom

        // Load appointments into list
        loadAppointmentList();  // This should now be safe

        // Listen for button clicks
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAppointment();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAppointment();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAppointment();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAppointment();
            }
        });

        // Add ListSelectionListener to handle row selection
        appointmentList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Check if the selection is done
                    String selectedValue = appointmentList.getSelectedValue();
                    if (selectedValue != null) {
                        // Extract appointmentId from selected value (assuming the format "Mã Cuộc Hẹn: <appointmentId>")
                        String appointmentId = selectedValue.split(" - ")[0].split(": ")[1];
                        fillAppointmentDetails(appointmentId);
                    }
                }
            }
        });
    }

    // Method to load appointment list
    private void loadAppointmentList() {
        // Load all appointments into the list
        listModel.clear();
        List<Appointment> appointments = appointmentManager.getAllAppointments();
        for (Appointment appointment : appointments) {
            // Display appointment information
            String appointmentInfo = String.format(
                "Mã Cuộc Hẹn: %s - Ngày Giờ: %s - Trạng Thái: %s - Ghi Chú: %s",
                appointment.getAppointmentId(),
                appointment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                appointment.getStatus(),
                appointment.getCustomerNote()
            );
            listModel.addElement(appointmentInfo); // Add info to the list
        }
    }

    // Method to fill appointment details into the form fields
    private void fillAppointmentDetails(String appointmentId) {
        Appointment appointment = appointmentManager.getAppointmentById(appointmentId);
        if (appointment != null) {
            tfAppointmentId.setText(appointment.getAppointmentId());
            tfDateTime.setText(appointment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            tfStatus.setText(appointment.getStatus());
            tfCustomerNote.setText(appointment.getCustomerNote());
        } else {
            taAppointmentInfo.setText("Cuộc hẹn không tồn tại!");
        }
    }

    // Add appointment
    private void addAppointment() {
        String appointmentId = tfAppointmentId.getText();
        String dateTime = tfDateTime.getText();  // Get input from the user
        String status = tfStatus.getText();
        String customerNote = tfCustomerNote.getText();

        // Convert dateTime from String to LocalDateTime
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Appointment appointment = new Appointment(appointmentId, dateTime, status, customerNote);
        appointmentManager.addAppointment(appointment);
        taAppointmentInfo.setText("Cuộc hẹn đã được thêm!");
        loadAppointmentList();  // Reload the list after adding a new appointment
    }

    // Update appointment
    private void updateAppointment() {
        String appointmentId = tfAppointmentId.getText();
        String dateTime = tfDateTime.getText();
        String status = tfStatus.getText();
        String customerNote = tfCustomerNote.getText();

        // Convert dateTime from String to LocalDateTime
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Appointment appointment = new Appointment(appointmentId, dateTime, status, customerNote);
        appointmentManager.updateAppointment(appointment); // Use updateAppointment method
        taAppointmentInfo.setText("Cuộc hẹn đã được cập nhật!");
        loadAppointmentList();  // Reload the list after updating an appointment
    }

    // Delete appointment
    private void deleteAppointment() {
        String appointmentId = tfAppointmentId.getText();
        appointmentManager.deleteAppointment(appointmentId);
        taAppointmentInfo.setText("Cuộc hẹn đã bị xóa!");
        loadAppointmentList();  // Reload the list after deleting an appointment
    }

    // View appointment details
    private void viewAppointment() {
        String appointmentId = tfAppointmentId.getText();
        Appointment appointment = appointmentManager.getAppointmentById(appointmentId);
        if (appointment != null) {
            taAppointmentInfo.setText("Thông tin cuộc hẹn:\n" +
                    "Mã cuộc hẹn: " + appointment.getAppointmentId() + "\n" +
                    "Ngày giờ: " + appointment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" +
                    "Trạng thái: " + appointment.getStatus() + "\n" +
                    "Ghi chú khách hàng: " + appointment.getCustomerNote());
        } else {
            taAppointmentInfo.setText("Cuộc hẹn không tồn tại!");
        }
    }

    public static void main(String[] args) {
        AppointmentUI ui = new AppointmentUI();
        ui.setVisible(true);
    }
}
