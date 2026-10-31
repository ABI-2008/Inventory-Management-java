package inventorymanagement.service;

import inventorymanagement.app.Searchable;
import inventorymanagement.model.Supplier;
import java.util.HashMap;

public class SupplierManager implements Searchable {
    private HashMap<String, Supplier> suppliers = new HashMap<>();
    public boolean add(Supplier supplier) { return suppliers.putIfAbsent(supplier.getSupplierId(), supplier) == null; }
    public Supplier get(String id) { return suppliers.get(id); }
    public boolean update(String id, String name, String contact, String email, String address) {
        Supplier supplier = suppliers.get(id);
        if (supplier == null) return false;
        supplier.setName(name); supplier.setContact(contact); supplier.setEmail(email); supplier.setAddress(address); return true;
    }
    public Supplier delete(String id) { return suppliers.remove(id); }
    public void displayAll() {
        if (suppliers.isEmpty()) System.out.println("No suppliers found.");
        for (Supplier supplier : suppliers.values()) System.out.println(supplier);
    }
    public HashMap<String, Supplier> getSuppliers() { return suppliers; }
    public void setSuppliers(HashMap<String, Supplier> suppliers) { this.suppliers = suppliers == null ? new HashMap<>() : suppliers; }
    @Override
    public String search(String keyword) {
        StringBuilder result = new StringBuilder();
        String text = keyword.toLowerCase();
        for (Supplier supplier : suppliers.values()) if (supplier.toString().toLowerCase().contains(text)) result.append(supplier).append(System.lineSeparator());
        return result.length() == 0 ? "No matching suppliers found." : result.toString();
    }
}
