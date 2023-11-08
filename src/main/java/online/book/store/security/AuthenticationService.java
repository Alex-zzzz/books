package online.book.store.security;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.UserLoginRequestDto;
import online.book.store.dto.UserLoginResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto
                        .getPassword())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        System.out.println(token);
        return new UserLoginResponseDto(token);
    }
}
