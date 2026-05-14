package Model;

/**
 * Represents an order entity in the system.
 * This class maps to the "orders" table in the database and links
 * a client to a specific product with a requested quantity.
 */
public class Orders {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;

    /**
     * Default constructor required for reflection-based instantiation.
     */
    public Orders() {}

    /**
     * Constructs a new Order with the specified relations and quantity.
     * @param clientId The ID of the client placing the order.
     * @param productId The ID of the product being ordered.
     * @param quantity The number of product units.
     */
    public Orders(int clientId, int productId, int quantity) {
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", quantity=" + quantity + "]";
    }
}