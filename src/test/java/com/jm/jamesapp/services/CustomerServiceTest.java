package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.dto.SaveCustomerDto;
import com.jm.jamesapp.models.dto.UpdateCustomerDto;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.user.enums.UserRole;
import com.jm.jamesapp.repositories.CustomerRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;
    private CustomerModel customer;
    private CustomerModel customerUpdt;
    private SaveCustomerDto saveCustomerDto;
    private UpdateCustomerDto updateCustomerDto;
    private UserModel user;

    @BeforeEach
    public void init() {
        user = new UserModel("Felipe Adriano", "lipezin", "felipe.adriano@jamesapp.com.br", "lipe@54321", UserRole.ADMIN);
        customer = new CustomerModel(null, "James Customer", "05515519094");
        customerUpdt = new CustomerModel(null, "James Customer Updated", "05515519094");

        saveCustomerDto = new SaveCustomerDto();
        saveCustomerDto.setName("James Customer");
        saveCustomerDto.setCpfCnpj("055.155.190-94");

        updateCustomerDto = new UpdateCustomerDto();
        updateCustomerDto.setName("James Customer Updated");
        updateCustomerDto.setCpfCnpj("055.155.190-94");
    }

    @Test
    public void testCreateCustomer() {
        when(customerRepository.save(Mockito.any(CustomerModel.class))).thenReturn(customer);

        CustomerModel createdCustomer = customerService.save(saveCustomerDto, null);

        Assertions.assertThat(createdCustomer).isNotNull();
    }


    @Test
    public void testFindAllByCustomers() {
        Page<CustomerModel> customers = Mockito.mock(Page.class);
        when(customerRepository.findAllByUser(Mockito.any(Pageable.class), Mockito.any(UserModel.class))).thenReturn(customers);

        Pageable paginacao = PageRequest.of(0, 10);
        Page<CustomerModel> customerModelList = customerService.findAllByUser(paginacao, user);

        Assertions.assertThat(customerModelList).isNotNull();
    }

    @Test
    public void testFindCustomerById() {
        var uuid = UUID.fromString("c251d6ea-8e4b-4cc6-a52c-6a0a5251c0d2");
        when(customerRepository.findById(uuid)).thenReturn(Optional.ofNullable(customer));

        CustomerModel customer = customerService.findById(uuid);

        Assertions.assertThat(customer).isNotNull();

    }

    @Test
    public void testFindCustomerByIdAndUser() {
        var uuid = UUID.fromString("c251d6ea-8e4b-4cc6-a52c-6a0a5251c0d2");
        when(customerRepository.findByIdAndUser(uuid, user)).thenReturn(Optional.ofNullable(customer));

        CustomerModel customer = customerService.findByIdAndUser(uuid, user);

        Assertions.assertThat(customer).isNotNull();

    }

    @Test
    public void testUpdateCustomer() {
        customer.setUser(user);
        customerUpdt.setUser(user);

        when(customerRepository.save(Mockito.any(CustomerModel.class))).thenReturn(customerUpdt);

        CustomerModel savedUser = customerService.update(customer, updateCustomerDto, user);

        Assertions.assertThat(savedUser).isNotNull();

    }

    @Test
    public void testDeleteCustomer() {
        BigDecimal balanceZero = BigDecimal.ZERO;
        when(customerRepository.sumTransactionsByCustomer(customer)).thenReturn(balanceZero);
        customerService.delete(customer);

        Mockito.verify(customerRepository).delete(customer);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    @DisplayName("Should remove a customer even with a positive balance")
    public void testDeleteCustomerWithPendingBalance() {
        customerService.deleteWithPendingBalance(customer);
        Mockito.verify(customerRepository).delete(customer);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    @DisplayName("When trying to remove a customer with a balance, an error should return")
    public void testDeleteCustomerWithBalancePositive() {
        BigDecimal balance = BigDecimal.valueOf(1);
        when(customerRepository.sumTransactionsByCustomer(customer)).thenReturn(balance);

        BusinessException exception = Assertions.catchThrowableOfType(() -> {
            customerService.delete(customer);
        }, BusinessException.class);

        Assertions.assertThat(exception.getMessage()).isEqualTo("Cliente possui saldo pendente");
        verify(customerRepository).sumTransactionsByCustomer(customer);
        verifyNoMoreInteractions(customerRepository);
    }
    }
