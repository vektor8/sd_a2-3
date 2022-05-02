package sd.utcn.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class NewCustomerDto {
    private String email;
    private String passwordHash;
}
