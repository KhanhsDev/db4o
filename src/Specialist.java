public class Specialist {
    private String specialistId;
    private String fullName;
    private String gender;
    private String jobTitle;
    private int experienceYears;

    // Constructor
    public Specialist(String specialistId, String fullName, String gender, String jobTitle, int experienceYears) {
        this.specialistId = specialistId;
        this.fullName = fullName;
        this.gender = gender;
        this.jobTitle = jobTitle;
        this.experienceYears = experienceYears;
    }

    // Getters and setters
    public String getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(String specialistId) {
        this.specialistId = specialistId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    // Optional: Override toString for easy display
    @Override
    public String toString() {
        return "Specialist ID: " + specialistId + "\n" +
               "Full Name: " + fullName + "\n" +
               "Gender: " + gender + "\n" +
               "Job Title: " + jobTitle + "\n" +
               "Experience Years: " + experienceYears;
    }
}
