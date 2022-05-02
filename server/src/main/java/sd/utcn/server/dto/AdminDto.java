package sd.utcn.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AdminDto extends UserDto {
    List<AdminRestaurantDto> restaurants;
    private boolean isAdmin = true;
}
