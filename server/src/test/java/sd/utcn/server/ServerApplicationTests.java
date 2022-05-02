package sd.utcn.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import sd.utcn.server.service.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomerServiceTests.class,
        UserServiceTests.class,
        FoodServiceTests.class,
        OrderServiceTests.class,
        RestaurantServiceTests.class
})
@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
