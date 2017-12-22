package project.wgu.sutton.devin.groceryinventory.model;


public class Food {
    private int id;
    private String name;
    private double quantity;
    private double price;
    private String type;
    private String expiration;
    private int usageCode;
    private int location;
    private String description;

    public static final int GOOD = 1;
    public static final int USED = 2;
    public static final int TOSSED = 3;

    public Food(String name, double quantity, double price, String type, String expiration, int usageCode, String description) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.usageCode = usageCode;
        this.expiration = expiration;
        this.description = description;
    }

    public Food(){

    }

    public int getUsageCode() {
        return usageCode;
    }

    public void setUsageCode(int usageCode) {
        this.usageCode = usageCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

