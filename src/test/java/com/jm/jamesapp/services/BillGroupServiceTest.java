package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.billgroup.enums.BillingFrequency;
import com.jm.jamesapp.models.dto.SaveBillGroupDto;
import com.jm.jamesapp.models.dto.UpdateBillGroupDto;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.user.enums.UserRole;
import com.jm.jamesapp.repositories.BillGroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillGroupServiceTest {

    @Mock
    private BillGroupRepository billGroupRepository;

    @InjectMocks
    private BillGroupService billGroupService;

    private UserModel user;
    private CustomerModel customer1;
    private CustomerModel customer2;
    private BillGroupModel billGroup;
    private SaveBillGroupDto saveBillGroupDto;
    private UpdateBillGroupDto updateBillGroupDto;

    @BeforeEach
    public void init() {
        user = new UserModel("Felipe Adriano", "lipezin", "felipe.adriano@jamesapp.com.br", "lipe@54321", UserRole.ADMIN);
        customer1 = new CustomerModel(user, "James Customer", "05515519094");
        customer2 = new CustomerModel(user, "Jeferson", "51285246047");

        billGroup = new BillGroupModel();
        billGroup.setName("Test group");
        billGroup.setDescription("Test Group desc");
        billGroup.setValue(BigDecimal.valueOf(120));
        billGroup.setBillingFrequency(BillingFrequency.MONTHLY);
        billGroup.setUser(user);

        saveBillGroupDto = new SaveBillGroupDto();
        saveBillGroupDto.setName("Test group save");
        saveBillGroupDto.setDescription("Test Group desc save");
        saveBillGroupDto.setTotalPayment(BigDecimal.valueOf(125));
        saveBillGroupDto.setBillingFrequency(BillingFrequency.ANNUALLY);

        updateBillGroupDto = new UpdateBillGroupDto();
        updateBillGroupDto.setName("Test group save");
        updateBillGroupDto.setDescription("Test Group desc save");
        updateBillGroupDto.setTotalPayment(BigDecimal.valueOf(125));
        updateBillGroupDto.setBillingFrequency(BillingFrequency.ANNUALLY);
    }

    @Test
    void save() {
        when(billGroupRepository.save(Mockito.any(BillGroupModel.class))).thenReturn(billGroup);

        BillGroupModel createdBillGroup = billGroupService.save(saveBillGroupDto, user);

        Assertions.assertThat(createdBillGroup).isNotNull();
    }

    @Test
    void update() {

    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findLastByUser() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAllByUser() {
    }

    @Test
    void findByIdAndUser() {
    }

    @Test
    void addCustomer() {
        when(billGroupRepository.save(Mockito.any(BillGroupModel.class))).thenReturn(billGroup);
        billGroupService.addCustomer(billGroup, customer2);
        Mockito.verify(billGroupRepository).save(billGroup);
        Assertions.assertThat(billGroup.getCustomers().size()).isEqualTo(1);
    }
}