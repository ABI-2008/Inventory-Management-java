package inventorymanagement.service;

import inventorymanagement.app.Searchable;
import inventorymanagement.model.Item;
import java.util.HashMap;

public class ItemManager implements Searchable {
    private HashMap<String, Item> items = new HashMap<>();

    public boolean add(Item item) { return items.putIfAbsent(item.getItemId(), item) == null; }
    public Item get(String id) { return items.get(id); }
    public boolean update(String id, String name, String category, double price, int quantity, String description) {
        Item item = items.get(id);
        if (item == null) return false;
        item.setName(name); item.setCategory(category); item.setPrice(price);
        item.setQuantity(quantity); item.setDescription(description);
        return true;
    }
    public Item delete(String id) { return items.remove(id); }
    public void displayAll() {
        if (items.isEmpty()) System.out.println("No items found.");
        for (Item item : items.values()) System.out.println(item);
    }
    public HashMap<String, Item> getItems() { return items; }
    public void setItems(HashMap<String, Item> items) { this.items = items == null ? new HashMap<>() : items; }
    @Override
    public String search(String keyword) {
        StringBuilder result = new StringBuilder();
        String text = keyword.toLowerCase();
        for (Item item : items.values()) {
            if (item.toString().toLowerCase().contains(text)) result.append(item).append(System.lineSeparator());
        }
        return result.length() == 0 ? "No matching items found." : result.toString();
    }
}
