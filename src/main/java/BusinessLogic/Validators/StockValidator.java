package BusinessLogic.Validators;

import Model.Product;

/**
 * Validator used to verify that a product's stock level is valid.
 * It ensures that no product can be registered or updated with a negative quantity.
 */
public class StockValidator implements Validator<Product> {

    /**
     * Validates the stock quantity of the specified product.
     * @param t The product object to be validated.
     * @throws IllegalArgumentException if the product quantity is less than 0.
     */
    @Override
    public void validate(Product t) {
        if(t.getQuantity() < 0) {
            throw new IllegalArgumentException("The stock cannot be negative");
        }
    }
}