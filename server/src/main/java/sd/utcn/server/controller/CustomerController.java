package sd.utcn.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.utcn.server.dto.CustomerDto;
import sd.utcn.server.dto.NewCustomerDto;
import sd.utcn.server.service.CustomerService;

@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService _service;

    @Autowired
    public CustomerController(CustomerService _service) {
        this._service = _service;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> AddCustomer(@RequestBody NewCustomerDto newCustomer) {
        try {
            log.info("Trying to add new customer with email {}", newCustomer.getEmail());
            CustomerDto dto = _service.addCustomer(newCustomer);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
