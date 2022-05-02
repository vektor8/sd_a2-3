package sd.utcn.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sd.utcn.server.dto.CustomerDto;
import sd.utcn.server.dto.NewCustomerDto;
import sd.utcn.server.mapper.CustomerMapper;
import sd.utcn.server.repository.CustomerRepository;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public CustomerService(CustomerRepository _repository) {
        this.customerRepository = _repository;
    }

    /**
     * checks if a given string represents a valid email
     *
     * @param str
     * @return
     */
    private boolean isValidEmail(String str) {
        return str.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
    }

    /**
     * Tries to add a new customer to the db, after checking if their data is valid
     *
     * @param newCustomer {  private String email;
     *                    private String passwordHash;}
     * @return
     * @throws Exception
     */
    public CustomerDto addCustomer(NewCustomerDto newCustomer) throws Exception {
        if (newCustomer.getEmail().isEmpty() ||
                newCustomer.getPasswordHash().isEmpty() ||
                !isValidEmail(newCustomer.getEmail())) {
            log.error("Invalid user data");
            throw new Exception("Invalid user data");
        }
        var b = customerRepository.findCustomerByEmail(newCustomer.getEmail());
        if (customerRepository.findCustomerByEmail(newCustomer.getEmail()).isPresent()) {
            log.error("Email taken");
            throw new Exception("Email taken");
        }
        var entity = CustomerMapper.ToEntity(newCustomer);
        entity.setPasswordHash(bCryptPasswordEncoder.encode(entity.getPasswordHash()));
        var a = customerRepository.save(entity);
        return CustomerMapper.ToDto(a);
    }
}
