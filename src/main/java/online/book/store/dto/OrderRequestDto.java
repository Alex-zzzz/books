package online.book.store.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class OrderRequestDto {
    @NotEmpty
    private String shippingAddress;
}
