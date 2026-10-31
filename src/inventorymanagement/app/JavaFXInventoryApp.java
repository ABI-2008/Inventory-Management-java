package inventorymanagement.app;

import inventorymanagement.model.Category;
import inventorymanagement.model.Item;
import inventorymanagement.model.Stock;
import inventorymanagement.model.Supplier;
import inventorymanagement.service.CategoryManager;
import inventorymanagement.service.ItemManager;
import inventorymanagement.service.StockManager;
import inventorymanagement.service.SupplierManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXInventoryApp extends Application {
    private final ItemManager itemManager = new ItemManager();
    private final CategoryManager categoryManager = new CategoryManager();
    private final SupplierManager supplierManager = new SupplierManager();
    private final StockManager stockManager = new StockManager();

    private TableView<Item> itemTable;
    private TableView<Category> categoryTable;
    private TableView<Supplier> supplierTable;
    private TableView<Stock> stockTable;
    private TextArea reportArea;

    private TextField itemIdField;
    private TextField itemNameField;
    private TextField itemCategoryField;
    private TextField itemPriceField;
    private TextField itemQuantityField;
    private TextField itemDescriptionField;

    private TextField categoryIdField;
    private TextField categoryNameField;
    private TextField categoryDescriptionField;

    private TextField supplierIdField;
    private TextField supplierNameField;
    private TextField supplierContactField;
    private TextField supplierEmailField;
    private TextField supplierAddressField;

    private TextField stockUpdateItemIdField;
    private TextField stockUpdateQuantityField;

    @Override
    public void init() {
        loadSampleData();
    }

    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Inventory Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(createItemsTab(), createCategoriesTab(), createSuppliersTab(), createStockTab(), createReportsTab());

        VBox root = new VBox(12, title, tabPane);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 1100, 620);
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadSampleData() {
        itemManager.add(new Item("ITM-1001", "Laptop", "Electronics", 1200.00, 8, "Gaming workstation"));
        itemManager.add(new Item("ITM-1002", "Office Chair", "Furniture", 180.00, 15, "Ergonomic chair"));
        itemManager.add(new Item("ITM-1003", "Printer", "Office Supplies", 420.00, 5, "Laser printer"));
        itemManager.getItems().values().forEach(stockManager::sync);

        categoryManager.add(new Category("CAT-100", "Electronics", "Electronic devices and accessories"));
        categoryManager.add(new Category("CAT-200", "Furniture", "Office and home furniture"));
        categoryManager.add(new Category("CAT-300", "Office Supplies", "General office materials"));

        supplierManager.add(new Supplier("SUP-100", "Tech Source", "9876543210", "sales@techsource.com", "123 Market Road"));
        supplierManager.add(new Supplier("SUP-200", "Home Comfort", "9123456780", "orders@homecomfort.com", "456 Oak Avenue"));
    }

    private Tab createItemsTab() {
        itemTable = new TableView<>();
        itemTable.setItems(FXCollections.observableArrayList(itemManager.getItems().values()));
        itemTable.setPlaceholder(new Label("No items available."));

        TableColumn<Item, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<Item, Number> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Item, Number> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<Item, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        itemTable.getColumns().addAll(idColumn, nameColumn, categoryColumn, priceColumn, quantityColumn, descriptionColumn);

        itemIdField = new TextField();
        itemIdField.setPromptText("Item ID");
        itemNameField = new TextField();
        itemNameField.setPromptText("Item Name");
        itemCategoryField = new TextField();
        itemCategoryField.setPromptText("Category");
        itemPriceField = new TextField();
        itemPriceField.setPromptText("Price");
        itemQuantityField = new TextField();
        itemQuantityField.setPromptText("Quantity");
        itemDescriptionField = new TextField();
        itemDescriptionField.setPromptText("Description");

        Button addBtn = new Button("Add Item");
        addBtn.setOnAction(event -> addItem());
        Button updateBtn = new Button("Update Selected");
        updateBtn.setOnAction(event -> updateSelectedItem());
        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.setOnAction(event -> deleteSelectedItem());
        Button clearBtn = new Button("Clear");
        clearBtn.setOnAction(event -> clearItemForm());
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(event -> refreshItemTable());

        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(10); form.setPadding(new Insets(10));
        form.addRow(0, new Label("ID:"), itemIdField, new Label("Name:"), itemNameField);
        form.addRow(1, new Label("Category:"), itemCategoryField, new Label("Price:"), itemPriceField);
        form.addRow(2, new Label("Quantity:"), itemQuantityField, new Label("Description:"), itemDescriptionField);
        form.addRow(3, addBtn, updateBtn, deleteBtn, clearBtn);
        form.add(refreshBtn, 3, 4);

        HBox layout = new HBox(20, form, itemTable);
        layout.setPadding(new Insets(10));

        Tab tab = new Tab("Items");
        tab.setContent(layout);
        return tab;
    }

    private Tab createCategoriesTab() {
        categoryTable = new TableView<>();
        categoryTable.setItems(FXCollections.observableArrayList(categoryManager.getCategories().values()));
        categoryTable.setPlaceholder(new Label("No categories available."));

        TableColumn<Category, String> categoryIdCol = new TableColumn<>("ID");
        categoryIdCol.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        TableColumn<Category, String> categoryNameCol = new TableColumn<>("Name");
        categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Category, String> categoryDescriptionCol = new TableColumn<>("Description");
        categoryDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryTable.getColumns().addAll(categoryIdCol, categoryNameCol, categoryDescriptionCol);

        categoryIdField = new TextField();
        categoryIdField.setPromptText("Category ID");
        categoryNameField = new TextField();
        categoryNameField.setPromptText("Category Name");
        categoryDescriptionField = new TextField();
        categoryDescriptionField.setPromptText("Description");

        Button addBtn = new Button("Add Category");
        addBtn.setOnAction(event -> addCategory());
        Button updateBtn = new Button("Update Selected");
        updateBtn.setOnAction(event -> updateSelectedCategory());
        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.setOnAction(event -> deleteSelectedCategory());
        Button clearBtn = new Button("Clear");
        clearBtn.setOnAction(event -> clearCategoryForm());

        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(10); form.setPadding(new Insets(10));
        form.addRow(0, new Label("ID:"), categoryIdField, new Label("Name:"), categoryNameField);
        form.addRow(1, new Label("Description:"), categoryDescriptionField);
        form.addRow(2, addBtn, updateBtn, deleteBtn, clearBtn);

        HBox layout = new HBox(20, form, categoryTable);
        layout.setPadding(new Insets(10));

        Tab tab = new Tab("Categories");
        tab.setContent(layout);
        return tab;
    }

    private Tab createSuppliersTab() {
        supplierTable = new TableView<>();
        supplierTable.setItems(FXCollections.observableArrayList(supplierManager.getSuppliers().values()));
        supplierTable.setPlaceholder(new Label("No suppliers available."));

        TableColumn<Supplier, String> supplierIdCol = new TableColumn<>("ID");
        supplierIdCol.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        TableColumn<Supplier, String> supplierNameCol = new TableColumn<>("Name");
        supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Supplier, String> supplierContactCol = new TableColumn<>("Contact");
        supplierContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        TableColumn<Supplier, String> supplierEmailCol = new TableColumn<>("Email");
        supplierEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Supplier, String> supplierAddressCol = new TableColumn<>("Address");
        supplierAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        supplierTable.getColumns().addAll(supplierIdCol, supplierNameCol, supplierContactCol, supplierEmailCol, supplierAddressCol);

        supplierIdField = new TextField();
        supplierIdField.setPromptText("Supplier ID");
        supplierNameField = new TextField();
        supplierNameField.setPromptText("Supplier Name");
        supplierContactField = new TextField();
        supplierContactField.setPromptText("Contact");
        supplierEmailField = new TextField();
        supplierEmailField.setPromptText("Email");
        supplierAddressField = new TextField();
        supplierAddressField.setPromptText("Address");

        Button addBtn = new Button("Add Supplier");
        addBtn.setOnAction(event -> addSupplier());
        Button updateBtn = new Button("Update Selected");
        updateBtn.setOnAction(event -> updateSelectedSupplier());
        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.setOnAction(event -> deleteSelectedSupplier());
        Button clearBtn = new Button("Clear");
        clearBtn.setOnAction(event -> clearSupplierForm());

        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(10); form.setPadding(new Insets(10));
        form.addRow(0, new Label("ID:"), supplierIdField, new Label("Name:"), supplierNameField);
        form.addRow(1, new Label("Contact:"), supplierContactField, new Label("Email:"), supplierEmailField);
        form.addRow(2, new Label("Address:"), supplierAddressField);
        form.addRow(3, addBtn, updateBtn, deleteBtn, clearBtn);

        HBox layout = new HBox(20, form, supplierTable);
        layout.setPadding(new Insets(10));

        Tab tab = new Tab("Suppliers");
        tab.setContent(layout);
        return tab;
    }

    private Tab createStockTab() {
        stockTable = new TableView<>();
        stockTable.setItems(FXCollections.observableArrayList(stockManager.getStocks().values()));
        stockTable.setPlaceholder(new Label("No stock records."));

        TableColumn<Stock, String> stockIdCol = new TableColumn<>("Item ID");
        stockIdCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        TableColumn<Stock, String> stockNameCol = new TableColumn<>("Name");
        stockNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Stock, String> stockCategoryCol = new TableColumn<>("Category");
        stockCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<Stock, Number> stockQtyCol = new TableColumn<>("Quantity");
        stockQtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<Stock, Number> stockPriceCol = new TableColumn<>("Price");
        stockPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockTable.getColumns().addAll(stockIdCol, stockNameCol, stockCategoryCol, stockQtyCol, stockPriceCol);

        stockUpdateItemIdField = new TextField();
        stockUpdateItemIdField.setPromptText("Item ID");
        stockUpdateQuantityField = new TextField();
        stockUpdateQuantityField.setPromptText("New Quantity");

        Button applyStockBtn = new Button("Update Stock");
        applyStockBtn.setOnAction(event -> updateStockQuantity());
        Button lowStockBtn = new Button("Show Low Stock");
        lowStockBtn.setOnAction(event -> showLowStockAlert());

        VBox controls = new VBox(10,
                new Label("Update stock quantity"),
                new HBox(10, new Label("Item ID:"), stockUpdateItemIdField),
                new HBox(10, new Label("Quantity:"), stockUpdateQuantityField),
                applyStockBtn,
                lowStockBtn);
        controls.setPadding(new Insets(10));

        HBox layout = new HBox(20, controls, stockTable);
        layout.setPadding(new Insets(10));

        Tab tab = new Tab("Stock");
        tab.setContent(layout);
        return tab;
    }

    private Tab createReportsTab() {
        reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setWrapText(true);

        Button inventoryBtn = new Button("Inventory Report");
        inventoryBtn.setOnAction(event -> reportArea.setText(buildInventoryReport()));
        Button stockBtn = new Button("Stock Report");
        stockBtn.setOnAction(event -> reportArea.setText(buildStockReport()));
        Button lowStockBtn = new Button("Low Stock Report");
        lowStockBtn.setOnAction(event -> reportArea.setText(buildLowStockReport()));

        HBox buttons = new HBox(10, inventoryBtn, stockBtn, lowStockBtn);
        buttons.setPadding(new Insets(10, 0, 0, 0));

        VBox layout = new VBox(10, buttons, reportArea);
        layout.setPadding(new Insets(10));

        Tab tab = new Tab("Reports");
        tab.setContent(layout);
        return tab;
    }

    private void addItem() {
        String id = itemIdField.getText().trim();
        String name = itemNameField.getText().trim();
        String category = itemCategoryField.getText().trim();
        String priceText = itemPriceField.getText().trim();
        String quantityText = itemQuantityField.getText().trim();
        String description = itemDescriptionField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || category.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || description.isEmpty()) {
            showError("All item fields are required.");
            return;
        }

        if (itemManager.get(id) != null) {
            showError("Duplicate item ID.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);
            Item item = new Item(id, name, category, price, quantity, description);
            itemManager.add(item);
            stockManager.sync(item);
            refreshItemTable();
            refreshStockTable();
            clearItemForm();
        } catch (NumberFormatException ex) {
            showError("Price and quantity must be valid numbers.");
        }
    }

    private void updateSelectedItem() {
        Item selected = itemTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select an item to update.");
            return;
        }

        String id = selected.getItemId();
        String name = itemNameField.getText().trim();
        String category = itemCategoryField.getText().trim();
        String description = itemDescriptionField.getText().trim();

        if (name.isEmpty() || category.isEmpty() || itemPriceField.getText().trim().isEmpty() || itemQuantityField.getText().trim().isEmpty() || description.isEmpty()) {
            showError("Please fill in all item fields before updating.");
            return;
        }

        try {
            double price = Double.parseDouble(itemPriceField.getText().trim());
            int quantity = Integer.parseInt(itemQuantityField.getText().trim());
            itemManager.update(id, name, category, price, quantity, description);
            stockManager.sync(itemManager.get(id));
            refreshItemTable();
            refreshStockTable();
            clearItemForm();
        } catch (NumberFormatException ex) {
            showError("Price and quantity must be valid numbers.");
        }
    }

    private void deleteSelectedItem() {
        Item selected = itemTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select an item to delete.");
            return;
        }

        itemManager.delete(selected.getItemId());
        stockManager.remove(selected.getItemId());
        refreshItemTable();
        refreshStockTable();
        clearItemForm();
    }

    private void clearItemForm() {
        itemIdField.clear();
        itemNameField.clear();
        itemCategoryField.clear();
        itemPriceField.clear();
        itemQuantityField.clear();
        itemDescriptionField.clear();
        itemTable.getSelectionModel().clearSelection();
    }

    private void addCategory() {
        String id = categoryIdField.getText().trim();
        String name = categoryNameField.getText().trim();
        String description = categoryDescriptionField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || description.isEmpty()) {
            showError("All category fields are required.");
            return;
        }

        if (categoryManager.get(id) != null) {
            showError("Duplicate category ID.");
            return;
        }

        categoryManager.add(new Category(id, name, description));
        refreshCategoryTable();
        clearCategoryForm();
    }

    private void updateSelectedCategory() {
        Category selected = categoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a category to update.");
            return;
        }

        String id = selected.getCategoryId();
        String name = categoryNameField.getText().trim();
        String description = categoryDescriptionField.getText().trim();

        if (name.isEmpty() || description.isEmpty()) {
            showError("Category name and description are required.");
            return;
        }

        categoryManager.update(id, name, description);
        refreshCategoryTable();
        clearCategoryForm();
    }

    private void deleteSelectedCategory() {
        Category selected = categoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a category to delete.");
            return;
        }

        categoryManager.delete(selected.getCategoryId());
        refreshCategoryTable();
        clearCategoryForm();
    }

    private void clearCategoryForm() {
        categoryIdField.clear();
        categoryNameField.clear();
        categoryDescriptionField.clear();
        categoryTable.getSelectionModel().clearSelection();
    }

    private void addSupplier() {
        String id = supplierIdField.getText().trim();
        String name = supplierNameField.getText().trim();
        String contact = supplierContactField.getText().trim();
        String email = supplierEmailField.getText().trim();
        String address = supplierAddressField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || email.isEmpty() || address.isEmpty()) {
            showError("All supplier fields are required.");
            return;
        }

        if (supplierManager.get(id) != null) {
            showError("Duplicate supplier ID.");
            return;
        }

        supplierManager.add(new Supplier(id, name, contact, email, address));
        refreshSupplierTable();
        clearSupplierForm();
    }

    private void updateSelectedSupplier() {
        Supplier selected = supplierTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a supplier to update.");
            return;
        }

        String id = selected.getSupplierId();
        String name = supplierNameField.getText().trim();
        String contact = supplierContactField.getText().trim();
        String email = supplierEmailField.getText().trim();
        String address = supplierAddressField.getText().trim();

        if (name.isEmpty() || contact.isEmpty() || email.isEmpty() || address.isEmpty()) {
            showError("Supplier name, contact, email, and address are required.");
            return;
        }

        supplierManager.update(id, name, contact, email, address);
        refreshSupplierTable();
        clearSupplierForm();
    }

    private void deleteSelectedSupplier() {
        Supplier selected = supplierTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a supplier to delete.");
            return;
        }

        supplierManager.delete(selected.getSupplierId());
        refreshSupplierTable();
        clearSupplierForm();
    }

    private void clearSupplierForm() {
        supplierIdField.clear();
        supplierNameField.clear();
        supplierContactField.clear();
        supplierEmailField.clear();
        supplierAddressField.clear();
        supplierTable.getSelectionModel().clearSelection();
    }

    private void updateStockQuantity() {
        String itemId = stockUpdateItemIdField.getText().trim();
        String quantityText = stockUpdateQuantityField.getText().trim();

        if (itemId.isEmpty() || quantityText.isEmpty()) {
            showError("Item ID and quantity are required.");
            return;
        }

        Item item = itemManager.get(itemId);
        if (item == null) {
            showError("Item not found.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            stockManager.update(item, quantity);
            refreshStockTable();
            stockUpdateItemIdField.clear();
            stockUpdateQuantityField.clear();
        } catch (NumberFormatException ex) {
            showError("Quantity must be a valid number.");
        }
    }

    private void showLowStockAlert() {
        StringBuilder message = new StringBuilder();
        boolean found = false;
        for (Stock stock : stockManager.getStocks().values()) {
            if (stock.getQuantity() <= StockManager.LOW_STOCK_THRESHOLD) {
                if (!found) {
                    message.append("LOW STOCK ALERT\n");
                    found = true;
                }
                message.append(stock.getItemId()).append(" | ").append(stock.getName()).append(" | Quantity: ").append(stock.getQuantity()).append("\n");
            }
        }
        if (!found) {
            message.append("No low stock items.");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Low Stock");
        alert.setHeaderText(null);
        alert.setContentText(message.toString());
        alert.showAndWait();
    }

    private String buildInventoryReport() {
        StringBuilder report = new StringBuilder();
        report.append("INVENTORY REPORT\n");
        if (itemManager.getItems().isEmpty()) {
            report.append("No items found.");
        } else {
            itemManager.getItems().values().forEach(item -> report.append(item).append("\n"));
        }
        return report.toString();
    }

    private String buildStockReport() {
        StringBuilder report = new StringBuilder();
        report.append("STOCK REPORT\n");
        if (stockManager.getStocks().isEmpty()) {
            report.append("No stock found.");
        } else {
            stockManager.getStocks().values().forEach(stock -> report.append(stock.getItemId())
                    .append(" | ").append(stock.getName())
                    .append(" | Quantity: ").append(stock.getQuantity()).append("\n"));
        }
        return report.toString();
    }

    private String buildLowStockReport() {
        StringBuilder report = new StringBuilder();
        report.append("LOW STOCK REPORT\n");
        boolean found = false;
        for (Stock stock : stockManager.getStocks().values()) {
            if (stock.getQuantity() <= StockManager.LOW_STOCK_THRESHOLD) {
                report.append(stock.getItemId()).append(" | ").append(stock.getName())
                        .append(" | Quantity: ").append(stock.getQuantity()).append("\n");
                found = true;
            }
        }
        if (!found) {
            report.append("No low stock items.");
        }
        return report.toString();
    }

    private void refreshItemTable() {
        itemTable.setItems(FXCollections.observableArrayList(itemManager.getItems().values()));
    }

    private void refreshCategoryTable() {
        categoryTable.setItems(FXCollections.observableArrayList(categoryManager.getCategories().values()));
    }

    private void refreshSupplierTable() {
        supplierTable.setItems(FXCollections.observableArrayList(supplierManager.getSuppliers().values()));
    }

    private void refreshStockTable() {
        stockTable.setItems(FXCollections.observableArrayList(stockManager.getStocks().values()));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
