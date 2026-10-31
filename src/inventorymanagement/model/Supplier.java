package inventorymanagement.model;

import java.io.Serializable;

public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private String supplierId;
    private String name;
    private String contact;
    private String email;
    private String address;

    public Supplier(String supplierId, String name, String contact, String email, String address) {
        this.supplierId = supplierId;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.address = address;
    }

    public String getSupplierId() { return supplierId; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return supplierId + " | " + name + " | Contact: " + contact + " | Email: " + email + " | " + address;
    }
}
