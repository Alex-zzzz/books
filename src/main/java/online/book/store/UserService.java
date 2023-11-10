package online.book.store;

import online.book.store.dto.UserRegistrationRequestDto;
import online.book.store.dto.UserResponseDto;
import online.book.store.exception.RegistrationException;
import online.book.store.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    User getAuthenticatedUser();
}
