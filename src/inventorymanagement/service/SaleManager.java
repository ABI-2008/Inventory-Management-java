package inventorymanagement.service;

import inventorymanagement.app.Searchable;
import inventorymanagement.model.Sale;
import java.util.HashMap;

public class SaleManager implements Searchable {
    private HashMap<String, Sale> sales = new HashMap<>();
    public boolean add(Sale sale) { return sales.putIfAbsent(sale.getSaleId(), sale) == null; }
    public Sale get(String id) { return sales.get(id); }
    public Sale delete(String id) { return sales.remove(id); }
    public void displayAll() {
        if (sales.isEmpty()) System.out.println("No sales found.");
        for (Sale sale : sales.values()) System.out.println(sale);
    }
    public HashMap<String, Sale> getSales() { return sales; }
    public void setSales(HashMap<String, Sale> sales) { this.sales = sales == null ? new HashMap<>() : sales; }
    @Override
    public String search(String keyword) {
        StringBuilder result = new StringBuilder();
        String text = keyword.toLowerCase();
        for (Sale sale : sales.values()) if (sale.toString().toLowerCase().contains(text)) result.append(sale).append(System.lineSeparator());
        return result.length() == 0 ? "No matching sales found." : result.toString();
    }
}
