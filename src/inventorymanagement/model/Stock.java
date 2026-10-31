package inventorymanagement.model;

public class Stock extends Item {
    private static final long serialVersionUID = 1L;

    public Stock(Item item) {
        super(item.getItemId(), item.getName(), item.getCategory(), item.getPrice(), item.getQuantity(), item.getDescription());
    }

    public Stock(String itemId, String name, String category, double price, int quantity, String description) {
        super(itemId, name, category, price, quantity, description);
    }
}
