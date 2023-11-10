package online.book.store.mapper;

import online.book.store.dto.UserRegistrationRequestDto;
import online.book.store.dto.UserResponseDto;
import online.book.store.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponseDto toDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setPassword(user.getPassword());
        userResponseDto.setLastName(userResponseDto.getLastName());
        userResponseDto.setFirstName(userResponseDto.getFirstName());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }

    @Override
    public User toModel(UserRegistrationRequestDto userRequestDto) {
        User user = new User();
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setFirstName(userRequestDto.getFirstName());
        return user;
    }
}
