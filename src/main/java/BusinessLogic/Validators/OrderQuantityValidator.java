package BusinessLogic.Validators;

import Model.Order;

public class OrderQuantityValidator implements Validator<Order> {
    @Override
    public void validate(Order t) {
        if(t.getQuantity() <= 0){
            throw new IllegalArgumentException("Order quantity cannot be less than 1");
        }
    }
}
