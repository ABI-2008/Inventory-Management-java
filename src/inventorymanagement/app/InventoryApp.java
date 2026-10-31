package inventorymanagement.app;

import inventorymanagement.model.Category;
import inventorymanagement.model.Item;
import inventorymanagement.model.Supplier;
import inventorymanagement.service.CategoryManager;
import inventorymanagement.service.ItemManager;
import inventorymanagement.service.StockManager;
import inventorymanagement.service.SupplierManager;
import java.util.Scanner;

public class InventoryApp {
    private final Scanner scanner = new Scanner(System.in);
    private final ItemManager itemManager = new ItemManager();
    private final CategoryManager categoryManager = new CategoryManager();
    private final SupplierManager supplierManager = new SupplierManager();
    private final StockManager stockManager = new StockManager();

    public static void main(String[] args) {
        new InventoryApp().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            switch (Validation.menuChoice(scanner)) {
                case 1 -> addItem();
                case 2 -> viewItems();
                case 3 -> addCategory();
                case 4 -> viewCategories();
                case 5 -> addSupplier();
                case 6 -> viewSuppliers();
                case 7 -> checkStock();
                case 8 -> stockManager.displayLowStock();
                case 9 -> reports();
                case 10 -> running = false;
                default -> System.out.println("Please choose a number from 1 to 10.");
            }
            System.out.println();
        }
        scanner.close();
        System.out.println("Thank you for using the Inventory Management System.");
    }

    private void printMainMenu() {
        System.out.println("========================================");
        System.out.println("       INVENTORY MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Item\n2. View Items\n3. Add Category\n4. View Categories");
        System.out.println("5. Add Supplier\n6. View Suppliers\n7. Check Stock\n8. Low Stock Alert");
        System.out.println("9. Reports\n10. Exit");
    }

    private void addItem() {
        heading("ADD ITEM");
        String id = Validation.required(scanner, "Enter Item ID: ");
        if (itemManager.get(id) != null) { System.out.println("Duplicate item ID."); return; }
        String name = Validation.required(scanner, "Enter Item Name: ");
        String category = Validation.required(scanner, "Enter Category: ");
        double price = Validation.nonNegativeDouble(scanner, "Enter Price: ");
        int quantity = Validation.nonNegativeInt(scanner, "Enter Quantity: ", true);
        String description = Validation.required(scanner, "Enter Description: ");
        Item item = new Item(id, name, category, price, quantity, description);
        itemManager.add(item); stockManager.sync(item);
        System.out.println("Item added successfully!");
    }

    private void viewItems() {
        heading("ITEMS"); itemManager.displayAll();
        String action = actionPrompt("item");
        if (action.equals("U")) updateItem();
        else if (action.equals("D")) deleteItem();
        else if (action.equals("S")) search(itemManager);
    }

    private void updateItem() {
        String id = Validation.required(scanner, "Enter Item ID: ");
        if (itemManager.get(id) == null) { System.out.println("Item not found."); return; }
        String name = Validation.required(scanner, "Enter Item Name: ");
        String category = Validation.required(scanner, "Enter Category: ");
        double price = Validation.nonNegativeDouble(scanner, "Enter Price: ");
        int quantity = Validation.nonNegativeInt(scanner, "Enter Quantity: ", true);
        String description = Validation.required(scanner, "Enter Description: ");
        itemManager.update(id, name, category, price, quantity, description);
        stockManager.sync(itemManager.get(id));
        System.out.println("Item updated successfully!");
    }

    private void deleteItem() {
        String id = Validation.required(scanner, "Enter Item ID: ");
        if (itemManager.delete(id) == null) System.out.println("Item not found.");
        else { stockManager.remove(id); System.out.println("Item deleted successfully!"); }
    }

    private void addCategory() {
        heading("ADD CATEGORY");
        String id = Validation.required(scanner, "Enter Category ID: ");
        if (categoryManager.get(id) != null) { System.out.println("Duplicate category ID."); return; }
        String name = Validation.required(scanner, "Enter Category Name: ");
        String description = Validation.required(scanner, "Enter Description: ");
        categoryManager.add(new Category(id, name, description));
        System.out.println("Category added successfully!");
    }

    private void viewCategories() {
        heading("CATEGORIES"); categoryManager.displayAll();
        String action = actionPrompt("category");
        if (action.equals("U")) updateCategory();
        else if (action.equals("D")) deleteCategory();
        else if (action.equals("S")) search(categoryManager);
    }

    private void updateCategory() {
        String id = Validation.required(scanner, "Enter Category ID: ");
        if (categoryManager.get(id) == null) { System.out.println("Category not found."); return; }
        categoryManager.update(id, Validation.required(scanner, "Enter Category Name: "), Validation.required(scanner, "Enter Description: "));
        System.out.println("Category updated successfully!");
    }

    private void deleteCategory() {
        String id = Validation.required(scanner, "Enter Category ID: ");
        System.out.println(categoryManager.delete(id) == null ? "Category not found." : "Category deleted successfully!");
    }

    private void addSupplier() {
        heading("ADD SUPPLIER");
        String id = Validation.required(scanner, "Enter Supplier ID: ");
        if (supplierManager.get(id) != null) { System.out.println("Duplicate supplier ID."); return; }
        Supplier supplier = new Supplier(id, Validation.required(scanner, "Enter Name: "), Validation.required(scanner, "Enter Contact: "), Validation.required(scanner, "Enter Email: "), Validation.required(scanner, "Enter Address: "));
        supplierManager.add(supplier); System.out.println("Supplier added successfully!");
    }

    private void viewSuppliers() {
        heading("SUPPLIERS"); supplierManager.displayAll();
        String action = actionPrompt("supplier");
        if (action.equals("U")) updateSupplier();
        else if (action.equals("D")) deleteSupplier();
        else if (action.equals("S")) search(supplierManager);
    }

    private void updateSupplier() {
        String id = Validation.required(scanner, "Enter Supplier ID: ");
        if (supplierManager.get(id) == null) { System.out.println("Supplier not found."); return; }
        supplierManager.update(id, Validation.required(scanner, "Enter Name: "), Validation.required(scanner, "Enter Contact: "), Validation.required(scanner, "Enter Email: "), Validation.required(scanner, "Enter Address: "));
        System.out.println("Supplier updated successfully!");
    }

    private void deleteSupplier() {
        String id = Validation.required(scanner, "Enter Supplier ID: ");
        System.out.println(supplierManager.delete(id) == null ? "Supplier not found." : "Supplier deleted successfully!");
    }

    private void checkStock() {
        heading("STOCK"); stockManager.displayStock();
        String itemId = Validation.required(scanner, "Enter Item ID to update, or press 0 to return: ");
        if (!itemId.equals("0")) {
            Item item = itemManager.get(itemId);
            if (item == null) System.out.println("Item not found.");
            else { int quantity = Validation.nonNegativeInt(scanner, "Enter New Quantity: ", true); stockManager.update(item, quantity); System.out.println("Stock updated successfully!"); }
        }
    }

    private void reports() {
        heading("REPORTS");
        System.out.println("1. Inventory Report\n2. Stock Report\n3. Low Stock Report");
        switch (Validation.menuChoice(scanner)) {
            case 1 -> itemManager.displayAll();
            case 2 -> stockManager.displayStock();
            case 3 -> stockManager.displayLowStock();
            default -> System.out.println("Invalid report choice.");
        }
    }

    private String actionPrompt(String type) {
        System.out.print("Enter U to update, D to delete, S to search " + type + ", or press Enter to return: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    private void search(Searchable searchable) {
        String keyword = Validation.required(scanner, "Enter search keyword: ");
        System.out.println(searchable.search(keyword));
    }

    private void heading(String title) {
        System.out.println("\n========================================");
        System.out.println("             " + title);
        System.out.println("========================================");
    }
}
