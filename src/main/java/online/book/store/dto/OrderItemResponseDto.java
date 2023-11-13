package online.book.store.dto;

public record OrderItemResponseDto(
        Long id,
        Long bookId,
        int quantity
) {
}
