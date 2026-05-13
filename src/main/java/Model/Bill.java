package Model;

import java.time.LocalDateTime;

public record Bill(
        int id,
        int clientId,
        String productName,
        int quantity,
        LocalDateTime createdAt
) {
}
