package inventorymanagement.model;

import java.io.Serializable;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private String categoryId;
    private String name;
    private String description;

    public Category(String categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public String getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return categoryId + " | " + name + " | " + description;
    }
}
