package online.book.store.dto;

import java.util.Set;
import lombok.Data;
import online.book.store.model.Role;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private boolean isDeleted;
    private Set<Role> roles;

}
