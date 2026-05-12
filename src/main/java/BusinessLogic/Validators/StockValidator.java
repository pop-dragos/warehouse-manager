package BusinessLogic.Validators;

import Model.Product;

public class StockValidator implements Validator<Product> {
    @Override
    public void validate(Product t) {
        if(t.getQuantity() < 0) {
            throw new IllegalArgumentException("The stock cannot be negative");
        }
    }
}
