package sd.utcn.server.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sd.utcn.server.model.Customer;
import sd.utcn.server.repository.UserRepository;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @MockBean
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserServiceTests(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Test
    public void loadUserByUsernameTest() {
        var customer = Customer.builder()
                .id(10L)
                .email("abc@gmail.com")
                .passwordHash("1234")
                .build();
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(customer));
        var res = userService.loadUserByUsername("abc@gmail.com");
        assert res.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("customer"));
    }
}
