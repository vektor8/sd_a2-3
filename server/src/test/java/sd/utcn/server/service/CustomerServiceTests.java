package sd.utcn.server.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sd.utcn.server.dto.CustomerDto;
import sd.utcn.server.dto.NewCustomerDto;
import sd.utcn.server.model.Customer;
import sd.utcn.server.repository.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerServiceTests {

    @MockBean
    private final CustomerRepository customerRepository;

    private final CustomerService customerService;

    @Autowired
    public CustomerServiceTests(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @Test
    public void addCustomerTest() throws Exception {
        var dto = NewCustomerDto.builder()
                .email("abcde@gmail.co")
                .passwordHash("1234")
                .build();
        var entity = new Customer(7L,
                "abcde@gmail.co",
                "$2a$12$SXQriQcIHu4qKgLl5WZH0.gzJ3Dty2RXwX1FWiE0VfSLn6RXynkJ2",
                null);
        Mockito.when(customerRepository.findCustomerByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(entity);
        var res = customerService.addCustomer(dto);
        var expected = CustomerDto.builder()
                .email("abcde@gmail.co")
                .id(7L)
                .orders(null)
                .build();

        assert (res.getEmail().equals(expected.getEmail()));
        assert (res.getOrders() == null);
        assert (!res.isAdmin());
    }

    @Test
    public void addCustomerTestBadEmail() throws Exception {
        var dto = NewCustomerDto.builder()
                .email("abcde")
                .passwordHash("1234")
                .build();

        Exception exception = assertThrows(Exception.class, () -> customerService.addCustomer(dto));
        assert(exception.getMessage().equals("Invalid user data"));
    }

}
