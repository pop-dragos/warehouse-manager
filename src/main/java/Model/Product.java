package Model;

/**
 * Represents a product entity in the system.
 * This class maps to the "product" table in the database and manages
 * inventory details such as name and available stock quantity.
 */
public class Product {
    private int id;
    private String name;
    private int quantity;

    /**
     * Default constructor required for reflection-based instantiation.
     */
    public Product() {}

    /**
     * Constructs a new Product with the specified details.
     * @param name The name of the product.
     * @param quantity The initial stock quantity available.
     */
    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", quantity=" + quantity + "]";
    }
}