package inventorymanagement.model;

import java.io.Serializable;

public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;
    private String saleId;
    private String date;
    private String customer;
    private String item;
    private int quantity;
    private double totalAmount;

    public Sale(String saleId, String date, String customer, String item, int quantity, double totalAmount) {
        this.saleId = saleId;
        this.date = date;
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public String getSaleId() { return saleId; }
    public String getDate() { return date; }
    public String getCustomer() { return customer; }
    public String getItem() { return item; }
    public int getQuantity() { return quantity; }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return saleId + " | " + date + " | Customer: " + customer + " | Item: " + item
                + " | Quantity: " + quantity + " | Total: " + totalAmount;
    }
}
