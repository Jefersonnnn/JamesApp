package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void testCreateCustomer() {
        Optional<UserModel> userOwner = userService.findById(UUID.fromString("77f4f611-fd7d-4c41-967b-e04fa50d81b5"));
        CustomerModel customer = new CustomerModel();

        customer.setOwner(userOwner.get());
        customer.setName("James Test");
        customer.setCpfCnpj("055.155.190-94");

        CustomerModel createdCustomer = customerService.save(customer);

        assert Objects.nonNull(createdCustomer.getId());
        assert Objects.equals(customer.getName(), createdCustomer.getName());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/customer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void testFindAllCustomers(){
        Pageable paginacao = PageRequest.of(0, 10);
        Page<CustomerModel> customerModelList = customerService.findAllByUser(paginacao, new UserModel());

        assertNotNull(customerModelList);
        assertEquals(customerModelList.getTotalElements(), 5);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/customer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void testFindCustomerById() {
        var uuid = UUID.fromString("c251d6ea-8e4b-4cc6-a52c-6a0a5251c0d2");
        Optional<CustomerModel> customer = customerService.findById(uuid);

        assert customer.isPresent();
        assert uuid == customer.get().getId();

    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/customer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void testDeleteCustomer(){
        var uuid = UUID.fromString("3096d1ec-d6f5-4e13-8a35-1c9b8203c3a3");
        Optional<CustomerModel> customer = customerService.findById(uuid);
        assertNotNull(customer);

        assert customer.isPresent();
        customerService.delete(customer.get());
        Optional<CustomerModel> deletedUser = customerService.findById(uuid);
        assert(deletedUser.isEmpty());
    }
}
