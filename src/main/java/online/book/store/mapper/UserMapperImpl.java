package online.book.store.mapper;

import online.book.store.dto.UserRegistrationRequestDto;
import online.book.store.dto.UserResponseDto;
import online.book.store.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponseDto toDto(User user) {
        return null;
    }

    @Override
    public User toModel(UserRegistrationRequestDto userRequestDto) {
        return null;
    }
}
