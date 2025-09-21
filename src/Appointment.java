import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private String appointmentId;
    private LocalDateTime dateTime;  // Changed to LocalDateTime
    private String status;  // Status of the appointment
    private String customerNote;  // Customer's notes

    // Constructor
    public Appointment(String appointmentId, String dateTime, String status, String customerNote) {
        this.appointmentId = appointmentId;
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = status;
        this.customerNote = customerNote;
    }

    // Getters
    public String getAppointmentId() { return appointmentId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    public String getCustomerNote() { return customerNote; }

    // Setters
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public void setDateTime(String dateTime) { 
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); 
    }
    public void setStatus(String status) { this.status = status; }
    public void setCustomerNote(String customerNote) { this.customerNote = customerNote; }

    // Optional: Override toString method to easily display Appointment details
    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId + "\n" +
               "Date and Time: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" +
               "Status: " + status + "\n" +
               "Customer Notes: " + customerNote;
    }
}
