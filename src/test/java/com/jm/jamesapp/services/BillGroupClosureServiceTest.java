package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.billgroup.BillGroupClosureModel;
import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.billgroup.enums.BillingFrequency;
import com.jm.jamesapp.models.dto.SaveTransactionDto;
import com.jm.jamesapp.models.transaction.TransactionModel;
import com.jm.jamesapp.models.transaction.enums.TransactionOrigin;
import com.jm.jamesapp.models.transaction.enums.TransactionType;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.user.enums.UserRole;
import com.jm.jamesapp.repositories.BillGroupClosureRepository;
import com.jm.jamesapp.repositories.BillGroupRepository;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillGroupClosureServiceTest {

    @Mock
    private BillGroupClosureRepository billGroupClosureRepository;

    @Mock
    private ITransactionService transactionService;

    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private BillGroupClosureService billGroupClosureService;

    private UserModel user;
    private BillGroupModel billGroup;
    private BillGroupClosureModel billGroupClosure;
    private CustomerModel customer1;
    private CustomerModel customer2;
    private CustomerModel customer3;
    private CustomerModel customer4;

    @BeforeEach
    void setUp() {
        user = new UserModel("Jeferson", "jefersonm", "jeferson@jamesapp.com.br", "12345678", UserRole.USER);
        customer1 = new CustomerModel(user, "Jose", "44086941007");
        customer2 = new CustomerModel(user, "Felipe", "21291150048");
        customer3 = new CustomerModel(user, "Douglas", "37760362064");
        customer4 = new CustomerModel(user, "Mari", "99767831916");

        billGroup = new BillGroupModel();
        billGroup.setName("Grupo Apostas Esportivas");
        billGroup.setUser(user);
        billGroup.getCustomers().addAll(
                Arrays.asList(customer1,customer2,customer3,customer4)
        );
        billGroup.setBillingFrequency(BillingFrequency.MONTHLY);
        billGroup.setDescription("Grupo de apostas esportivas");
        billGroup.setValue(BigDecimal.valueOf(520));

    }

    @Test
    void closeAndSave() {
        billGroupClosure = new BillGroupClosureModel(billGroup, Instant.parse("2021-12-03T01:50:45Z"), BigDecimal.valueOf(520), Set.of(customer2, customer4));

        when(customerService.calculateBalance(customer1)).thenReturn(BigDecimal.valueOf(25));
        when(customerService.calculateBalance(customer2)).thenReturn(BigDecimal.valueOf(260));
        when(customerService.calculateBalance(customer3)).thenReturn(BigDecimal.valueOf(25));
        when(customerService.calculateBalance(customer4)).thenReturn(BigDecimal.valueOf(260));

//        when(transactionService.register(Mockito.any(CustomerModel.class), Mockito.any(SaveTransactionDto.class))).thenReturn(new TransactionModel());
        when(billGroupClosureRepository.save(Mockito.any(BillGroupClosureModel.class))).thenReturn(billGroupClosure);

        BillGroupClosureModel billGroupClosure = billGroupClosureService.closeAndSave(billGroup);

        Assertions.assertThat(billGroupClosure).isNotNull();
        Assertions.assertThat(billGroupClosure.getCustomers().size()).isEqualTo(2);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }

    @Test
    void addCustomer() {
    }
}