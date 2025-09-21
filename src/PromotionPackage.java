public class PromotionPackage {
    private String packageId;
    private String packageName;
    private String description;
    private double discount;  // Discount percentage

    // Constructor
    public PromotionPackage(String packageId, String packageName, String description, double discount) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.description = description;
        this.discount = discount;
    }

    // Getters and setters
    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    // Optional: Override toString for easy display
    @Override
    public String toString() {
        return "Package ID: " + packageId + "\n" +
               "Package Name: " + packageName + "\n" +
               "Description: " + description + "\n" +
               "Discount: " + discount + "%";
    }
}
