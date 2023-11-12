package online.book.store.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import online.book.store.model.Order;

@Data
public class OrderUpdateStatusDto {
    @NotEmpty
    private Order.Status status;
}
