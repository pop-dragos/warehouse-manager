package BusinessLogic.Validators;

import Model.Orders;

/**
 * Validator used to ensure that the quantity requested in an order is valid.
 */
public class OrderQuantityValidator implements Validator<Orders> {

    /**
     * Validates the quantity of the specified order.
     * @param t The order object to be validated.
     * @throws IllegalArgumentException if the order quantity is less than or equal to 0.
     */
    @Override
    public void validate(Orders t) {
        if(t.getQuantity() <= 0){
            throw new IllegalArgumentException("Order quantity cannot be less than 1");
        }
    }
}