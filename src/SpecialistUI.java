import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SpecialistUI extends JFrame {
    private JTextField tfSpecialistId, tfFullName, tfGender, tfJobTitle, tfExperienceYears;
    private JTextArea taSpecialistInfo;
    private JButton btnAdd, btnUpdate, btnDelete, btnView;

    private SpecialistManager specialistManager;
    private JList<String> specialistList;
    private DefaultListModel<String> listModel;

    public SpecialistUI() {
        specialistManager = new SpecialistManager();  // Khởi tạo SpecialistManager

        // JFrame configuration
        setTitle("Quản lý Chuyên Gia - Spa");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for specialist list at the top
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        // DefaultListModel to hold specialist names
        listModel = new DefaultListModel<>();  // Initialize the listModel here
        specialistList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(specialistList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for specialist information form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));  // Adjusted spacing for readability

        // Specialist information fields
        formPanel.add(new JLabel("Mã Chuyên Gia:"));
        tfSpecialistId = new JTextField();
        formPanel.add(tfSpecialistId);

        formPanel.add(new JLabel("Họ Tên:"));
        tfFullName = new JTextField();
        formPanel.add(tfFullName);

        formPanel.add(new JLabel("Giới Tính:"));
        tfGender = new JTextField();
        formPanel.add(tfGender);

        formPanel.add(new JLabel("Chức Vụ:"));
        tfJobTitle = new JTextField();
        formPanel.add(tfJobTitle);

        formPanel.add(new JLabel("Kinh Nghiệm (Năm):"));
        tfExperienceYears = new JTextField();
        formPanel.add(tfExperienceYears);

        // Panel for action buttons (Add, Update, Delete) in a single row below the list
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        btnAdd = new JButton("Thêm Chuyên Gia");
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Cập Nhật Chuyên Gia");
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Xóa Chuyên Gia");
        buttonPanel.add(btnDelete);

        btnView = new JButton("Xem Chuyên Gia");
        buttonPanel.add(btnView);

        // TextArea to show specialist details (for feedback)
        taSpecialistInfo = new JTextArea(5, 40);
        taSpecialistInfo.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(taSpecialistInfo);

        // Add components to JFrame
        add(listPanel, BorderLayout.NORTH);   // Specialist list at the top
        add(formPanel, BorderLayout.CENTER);  // Form in the center
        add(infoScrollPane, BorderLayout.EAST);  // Info/feedback area on the right
        add(buttonPanel, BorderLayout.SOUTH);  // Buttons at the bottom

        // Load specialists into list
        loadSpecialistList();  // This should now be safe

        // Listen for button clicks
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSpecialist();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSpecialist();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSpecialist();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSpecialist();
            }
        });

        // Add ListSelectionListener to handle row selection
        specialistList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Check if the selection is done
                    String selectedValue = specialistList.getSelectedValue();
                    if (selectedValue != null) {
                        // Extract specialistId from selected value (assuming the format "Mã Chuyên Gia: <specialistId>")
                        String specialistId = selectedValue.split(" - ")[0].split(": ")[1];
                        fillSpecialistDetails(specialistId);
                    }
                }
            }
        });
    }

    // Method to load specialist list
    private void loadSpecialistList() {
        // Load all specialists into the list
        listModel.clear();
        List<Specialist> specialists = specialistManager.getAllSpecialists();
        for (Specialist specialist : specialists) {
            // Display specialist information
            String specialistInfo = String.format(
                "Mã Chuyên Gia: %s - Họ Tên: %s - Kinh Nghiệm: %d năm",
                specialist.getSpecialistId(),
                specialist.getFullName(),
                specialist.getExperienceYears()
            );
            listModel.addElement(specialistInfo);  // Add info to the list
        }
    }

    // Method to fill specialist details into the form fields
    private void fillSpecialistDetails(String specialistId) {
        Specialist specialist = specialistManager.getSpecialistById(specialistId);
        if (specialist != null) {
            tfSpecialistId.setText(specialist.getSpecialistId());
            tfFullName.setText(specialist.getFullName());
            tfGender.setText(specialist.getGender());
            tfJobTitle.setText(specialist.getJobTitle());
            tfExperienceYears.setText(String.valueOf(specialist.getExperienceYears()));
        } else {
            taSpecialistInfo.setText("Chuyên gia không tồn tại!");
        }
    }

    // Add specialist
    private void addSpecialist() {
        String specialistId = tfSpecialistId.getText();
        String fullName = tfFullName.getText();
        String gender = tfGender.getText();
        String jobTitle = tfJobTitle.getText();
        int experienceYears = Integer.parseInt(tfExperienceYears.getText());

        Specialist specialist = new Specialist(specialistId, fullName, gender, jobTitle, experienceYears);
        specialistManager.addSpecialist(specialist);
        taSpecialistInfo.setText("Chuyên gia đã được thêm!");
        loadSpecialistList();  // Reload the list after adding a new specialist
    }

    // Update specialist
    private void updateSpecialist() {
        String specialistId = tfSpecialistId.getText();
        String fullName = tfFullName.getText();
        String gender = tfGender.getText();
        String jobTitle = tfJobTitle.getText();
        int experienceYears = Integer.parseInt(tfExperienceYears.getText());

        // Create Specialist object
        Specialist specialist = new Specialist(specialistId, fullName, gender, jobTitle, experienceYears);
        specialistManager.updateSpecialist(specialist);  // Use updateSpecialist with Specialist object
        taSpecialistInfo.setText("Chuyên gia đã được cập nhật!");
        loadSpecialistList();  // Reload the list after updating the specialist
    }

    // Delete specialist
    private void deleteSpecialist() {
        String specialistId = tfSpecialistId.getText();
        specialistManager.deleteSpecialist(specialistId);
        taSpecialistInfo.setText("Chuyên gia đã bị xóa!");
        loadSpecialistList();  // Reload the list after deleting a specialist
    }

    // View specialist details
    private void viewSpecialist() {
        String specialistId = tfSpecialistId.getText();
        Specialist specialist = specialistManager.getSpecialistById(specialistId);
        if (specialist != null) {
            taSpecialistInfo.setText("Thông tin chuyên gia:\n" +
                    "Mã chuyên gia: " + specialist.getSpecialistId() + "\n" +
                    "Họ tên: " + specialist.getFullName() + "\n" +
                    "Giới tính: " + specialist.getGender() + "\n" +
                    "Chức vụ: " + specialist.getJobTitle() + "\n" +
                    "Kinh nghiệm: " + specialist.getExperienceYears() + " năm");
        } else {
            taSpecialistInfo.setText("Chuyên gia không tồn tại!");
        }
    }

    public static void main(String[] args) {
        SpecialistUI ui = new SpecialistUI();
        ui.setVisible(true);
    }
}
