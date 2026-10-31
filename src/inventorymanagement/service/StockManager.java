package inventorymanagement.service;

import inventorymanagement.model.Item;
import inventorymanagement.model.Stock;
import java.util.HashMap;

public class StockManager {
    public static final int LOW_STOCK_THRESHOLD = 10;
    private HashMap<String, Stock> stocks = new HashMap<>();

    public void sync(Item item) { stocks.put(item.getItemId(), new Stock(item)); }
    public void remove(String itemId) { stocks.remove(itemId); }
    public void update(Item item, int quantity) { item.setQuantity(quantity); sync(item); }
    public Stock get(String itemId) { return stocks.get(itemId); }
    public void displayStock() {
        if (stocks.isEmpty()) System.out.println("No stock found.");
        for (Stock stock : stocks.values()) System.out.println(stock.getItemId() + " | " + stock.getName() + " | Quantity: " + stock.getQuantity());
    }
    public void displayLowStock() {
        boolean found = false;
        for (Stock stock : stocks.values()) if (stock.getQuantity() <= LOW_STOCK_THRESHOLD) {
            if (!found) System.out.println("LOW STOCK ALERT");
            System.out.println(stock.getItemId() + " | " + stock.getName() + " | Quantity: " + stock.getQuantity()); found = true;
        }
        if (!found) System.out.println("No low stock items.");
    }
    public HashMap<String, Stock> getStocks() { return stocks; }
    public void setStocks(HashMap<String, Stock> stocks) { this.stocks = stocks == null ? new HashMap<>() : stocks; }
}
