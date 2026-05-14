package Model;

import java.time.LocalDateTime;

/**
 * An immutable data representation of an invoice (bill).
 * This record stores the final details of a successful order.
 ** @param id          The unique identifier of the bill.
 * @param clientId    The ID of the client who placed the order.
 * @param productName The name of the purchased product at the time of the transaction.
 * @param quantity    The number of items purchased.
 * @param createdAt   The exact date and time when the bill was generated.
 */
public record Bill(
        int id,
        int clientId,
        String productName,
        int quantity,
        LocalDateTime createdAt
) {
}