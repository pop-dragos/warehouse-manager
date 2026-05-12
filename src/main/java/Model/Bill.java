package Model;

import java.time.LocalDateTime;

public record Bill(
        int orderID,
        String clientName,
        String productName,
        int quantity,
        LocalDateTime createdAt
) {
}
