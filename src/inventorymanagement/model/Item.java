package inventorymanagement.model;

import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String itemId;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private String description;

    public Item(String itemId, String name, String category, double price, int quantity, String description) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getDescription() { return description; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return itemId + " | " + name + " | Category: " + category + " | Price: " + price
                + " | Quantity: " + quantity + " | " + description;
    }
}
