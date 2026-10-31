package inventorymanagement.model;

import java.io.Serializable;

public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;
    private String purchaseId;
    private String date;
    private String supplier;
    private String item;
    private int quantity;
    private double totalAmount;

    public Purchase(String purchaseId, String date, String supplier, String item, int quantity, double totalAmount) {
        this.purchaseId = purchaseId;
        this.date = date;
        this.supplier = supplier;
        this.item = item;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public String getPurchaseId() { return purchaseId; }
    public String getDate() { return date; }
    public String getSupplier() { return supplier; }
    public String getItem() { return item; }
    public int getQuantity() { return quantity; }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return purchaseId + " | " + date + " | Supplier: " + supplier + " | Item: " + item
                + " | Quantity: " + quantity + " | Total: " + totalAmount;
    }
}
