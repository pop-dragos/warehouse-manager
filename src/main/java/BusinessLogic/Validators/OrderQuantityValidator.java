package BusinessLogic.Validators;

import Model.Orders;

public class OrderQuantityValidator implements Validator<Orders> {
    @Override
    public void validate(Orders t) {
        if(t.getQuantity() <= 0){
            throw new IllegalArgumentException("Order quantity cannot be less than 1");
        }
    }
}
