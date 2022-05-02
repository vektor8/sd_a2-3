package sd.utcn.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NewRestaurantDto {
    private String name;
    private String location;
    private Long adminId;
}
