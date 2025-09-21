public class Service {
    private String serviceId;
    private String serviceName;
    private double price;
    private String description;

    // Constructor
    public Service(String serviceId, String serviceName, double price, String description) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.description = description;
    }

    // Getters and setters
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Optional: Override toString for easy display
    @Override
    public String toString() {
        return "Service ID: " + serviceId + "\n" +
               "Service Name: " + serviceName + "\n" +
               "Price: " + price + "\n" +
               "Description: " + description;
    }
}
