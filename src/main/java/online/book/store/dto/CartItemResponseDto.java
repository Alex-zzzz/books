package online.book.store.dto;

public record CartItemResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        int quantity
) {
}
