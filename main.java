import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Custom exception for item code not found
class ItemCodeNotFoundException extends Exception {
    public ItemCodeNotFoundException(String message) {
        super(message);
    }
}

// GroceryItem class to store item details
class GroceryItem {
    private String itemCode;
    private String itemName;
    private double price;
    private double weight;
    private String manufacturer;
    private LocalDateTime manufacturingDate;
    private LocalDateTime expiryDate;
    private double discount;

    // Constructor
    public GroceryItem(String itemCode, String itemName, double price, double weight, String manufacturer,
                       LocalDateTime manufacturingDate, LocalDateTime expiryDate, double discount) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.price = price;
        this.weight = weight;
        this.manufacturer = manufacturer;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.discount = discount;
    }

    // Getters
    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public LocalDateTime getManufacturingDate() {
        return manufacturingDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public double getDiscount() {
        return discount;
    }
}

// POS class
class POS {
    private String cashierName;
    private String branch;
    private String customerName;
    private List<GroceryItem> itemList;
    private double totalDiscount;
    private double totalPrice;
    private LocalDateTime dateTime;

    // Constructor
    public POS(String cashierName, String branch, String customerName) {
        this.cashierName = cashierName;
        this.branch = branch;
        this.customerName = customerName;
        this.itemList = new ArrayList<>();
        this.totalDiscount = 0.0;
        this.totalPrice = 0.0;
        this.dateTime = LocalDateTime.now();
    }

    // Method to add an item to the bill
    public void addItem(GroceryItem item, int quantity) {
        itemList.add(item);
        double itemDiscount = item.getDiscount();
        double itemPrice = item.getPrice() * quantity;
        double discountAmount = itemPrice * itemDiscount / 100;
        totalPrice += itemPrice - discountAmount;
        totalDiscount += discountAmount;
    }

    // Method to handle pending bills
    public void handlePendingBill() {
        // Logic to handle pending bills
        // This can involve storing the current bill temporarily and resuming it later
        // For the sake of simplicity, this is not implemented in this example
    }

    // Method to get item details while handling ItemCodeNotFoundException
    public GroceryItem getItemDetails() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        GroceryItem item = null;
        boolean validItemCode = false;
        while (!validItemCode) {
            try {
                System.out.print("Enter item code: ");
                String itemCode = br.readLine();
                // Fetch item details from the database (For simplicity, hardcoding item details)
                // If item code is found, return the item details
                // Otherwise, throw ItemCodeNotFoundException
                if (itemCode.equals("123")) { // Sample item code, replace with actual database logic
                    item = new GroceryItem("123", "Sample Item", 10.0, 1.0, "Sample Manufacturer",
                            LocalDateTime.now(), LocalDateTime.now().plusDays(30), 5.0);
                    validItemCode = true;
                } else {
                    throw new ItemCodeNotFoundException("Item code not found. Please re-enter.");
                }
            } catch (IOException e) {
                System.out.println("Error reading input.");
            } catch (ItemCodeNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return item;
    }

    // Method to print the bill
    public void printBill() {
        System.out.println("Cashier: " + cashierName);
        System.out.println("Branch: " + branch);
        System.out.println("Customer: " + customerName);
        System.out.println("Date and Time: " + dateTime);
        System.out.println("Item List:");
        for (GroceryItem item : itemList) {
            System.out.println("Item Name: " + item.getItemName());
            System.out.println("Price: " + item.getPrice());
            System.out.println("Discount: " + item.getDiscount() + "%");
            // Add more details as needed
        }
        System.out.println("Total Discount: " + totalDiscount);
        System.out.println("Total Price: " + totalPrice);
    }
}

public class main {
    public static void main(String[] args) {
        // Sample usage of POS system
        POS pos = new POS("John Doe", "Branch A", "Alice");
        try {
            GroceryItem item = pos.getItemDetails();
            pos.addItem(item, 1); // Add item to the bill
            pos.printBill();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}