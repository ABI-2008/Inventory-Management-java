package inventorymanagement.service;

import inventorymanagement.app.Searchable;
import inventorymanagement.model.Purchase;
import java.util.HashMap;

public class PurchaseManager implements Searchable {
    private HashMap<String, Purchase> purchases = new HashMap<>();
    public boolean add(Purchase purchase) { return purchases.putIfAbsent(purchase.getPurchaseId(), purchase) == null; }
    public Purchase get(String id) { return purchases.get(id); }
    public Purchase delete(String id) { return purchases.remove(id); }
    public void displayAll() {
        if (purchases.isEmpty()) System.out.println("No purchases found.");
        for (Purchase purchase : purchases.values()) System.out.println(purchase);
    }
    public HashMap<String, Purchase> getPurchases() { return purchases; }
    public void setPurchases(HashMap<String, Purchase> purchases) { this.purchases = purchases == null ? new HashMap<>() : purchases; }
    @Override
    public String search(String keyword) {
        StringBuilder result = new StringBuilder();
        String text = keyword.toLowerCase();
        for (Purchase purchase : purchases.values()) if (purchase.toString().toLowerCase().contains(text)) result.append(purchase).append(System.lineSeparator());
        return result.length() == 0 ? "No matching purchases found." : result.toString();
    }
}
