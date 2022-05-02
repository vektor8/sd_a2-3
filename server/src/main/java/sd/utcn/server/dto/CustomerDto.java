package sd.utcn.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Setter
@Getter
public class CustomerDto extends UserDto {
    private List<OrderDto> orders;
    private boolean isAdmin = false;
    @Builder
    public CustomerDto(Long id, String email, List<OrderDto> orders){
        super(id, email);
        this.orders = orders;
    }
    public CustomerDto(){
        super();
    }
}
