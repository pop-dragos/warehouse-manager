package BusinessLogic;

import BusinessLogic.Validators.StockValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.ProductDAO;
import Model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Class for managing product-related operations.
 */
public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    /**
     * Initializes the ProductBLL with stock validators and the corresponding DAO.
     */
    public ProductBLL() {
        productDAO = new ProductDAO();
        validators = new ArrayList<>();
        validators.add(new StockValidator());
    }

    /**
     * Retrieves a product by its unique identifier.
     * @param id The ID of the searched product.
     * @return The found {@link Product} object.
     * @throws NoSuchElementException if no product is found with the given ID.
     */
    public Product findProductById(int id) {
        Product p = productDAO.findById(id);
        if (p == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return p;
    }

    /**
     * Fetches all product records from the database.
     * @return A list of all {@link Product} objects.
     */
    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Validates and inserts a new product into the system.
     * @param product The product object to be inserted.
     */
    public void insertProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        productDAO.insert(product);
    }

    /**
     * Validates and updates an existing product's information or stock level.
     * @param product The product object containing updated information.
     */
    public void updateProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        productDAO.update(product);
    }

    /**
     * Removes a product record from the system.
     * @param product The product object to be deleted.
     */
    public void deleteProduct(Product product) {
        productDAO.delete(product);
    }
}