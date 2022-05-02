package sd.utcn.server.mapper;

import sd.utcn.server.dto.UserDto;
import sd.utcn.server.model.Admin;
import sd.utcn.server.model.Customer;
import sd.utcn.server.model.User;

public class UserMapper {
    public static UserDto ToDto(User user){
        if(user instanceof Customer) return CustomerMapper.ToDto((Customer) user);
        return AdminMapper.ToDto((Admin) user);
    }
}
