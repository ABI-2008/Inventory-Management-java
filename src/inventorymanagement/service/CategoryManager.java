package inventorymanagement.service;

import inventorymanagement.app.Searchable;
import inventorymanagement.model.Category;
import java.util.HashMap;

public class CategoryManager implements Searchable {
    private HashMap<String, Category> categories = new HashMap<>();
    public boolean add(Category category) { return categories.putIfAbsent(category.getCategoryId(), category) == null; }
    public Category get(String id) { return categories.get(id); }
    public boolean update(String id, String name, String description) {
        Category category = categories.get(id);
        if (category == null) return false;
        category.setName(name); category.setDescription(description); return true;
    }
    public Category delete(String id) { return categories.remove(id); }
    public void displayAll() {
        if (categories.isEmpty()) System.out.println("No categories found.");
        for (Category category : categories.values()) System.out.println(category);
    }
    public HashMap<String, Category> getCategories() { return categories; }
    public void setCategories(HashMap<String, Category> categories) { this.categories = categories == null ? new HashMap<>() : categories; }
    @Override
    public String search(String keyword) {
        StringBuilder result = new StringBuilder();
        String text = keyword.toLowerCase();
        for (Category category : categories.values()) if (category.toString().toLowerCase().contains(text)) result.append(category).append(System.lineSeparator());
        return result.length() == 0 ? "No matching categories found." : result.toString();
    }
}
