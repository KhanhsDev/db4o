public class Customer {
    private String customerId;
    private String fullName;
    private String phoneNumber;
    private String birthDate;
    private String gender;
    private int points;
    private String membershipLevel;

    // Constructor
    public Customer(String customerId, String fullName, String phoneNumber, String birthDate, String gender, int points, String membershipLevel) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.points = points;
        this.membershipLevel = membershipLevel;
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return fullName; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getBirthDate() { return birthDate; }
    public String getGender() { return gender; }
    public int getPoints() { return points; }
    public String getMembershipLevel() { return membershipLevel; }

    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPoints(int points) { this.points = points; }
    public void setMembershipLevel(String membershipLevel) { this.membershipLevel = membershipLevel; }
}

// file này là nơi khai báo các thuộc tính ( attribute ) và phuoqng thức ( method ) của đối tượng Khách Hàng ( Customer )