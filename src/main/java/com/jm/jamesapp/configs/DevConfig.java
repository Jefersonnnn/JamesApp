package com.jm.jamesapp.configs;

import com.jm.jamesapp.dtos.requests.ApiCustomerRequestDto;
import com.jm.jamesapp.dtos.requests.ApiGroupBillRequestDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveCustomerDto;
import com.jm.jamesapp.models.dto.SaveGroupBillDto;
import com.jm.jamesapp.models.dto.UpdateCustomerDto;
import com.jm.jamesapp.repositories.CustomerRepository;
import com.jm.jamesapp.repositories.GroupBillRepository;
import com.jm.jamesapp.repositories.UserRepository;
import com.jm.jamesapp.services.CustomerService;
import com.jm.jamesapp.services.GroupBillService;
import com.jm.jamesapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GroupBillRepository groupBillRepository;

    @Autowired
    private GroupBillService groupBillService;

    @Override
    public void run(String... args) throws Exception {

        groupBillRepository.deleteAll();
        customerRepository.deleteAll();
        userRepository.deleteAll();

        UserModel userAdm = new UserModel("Jeferson Machado", "jeferson.machado@jamesapp.com.br", "12345678", UserModel.UserRole.ADMIN);
        UserModel user1 = new UserModel("Douglas Giovanellas", "doug.giova.nellas@jamesapp.com.br", "12345678", UserModel.UserRole.USER);
        UserModel user2 = new UserModel("Felipe Prestes", "felipe.prestes@jamesapp.com.br", "12345678", UserModel.UserRole.USER);
        UserModel user3 = new UserModel("José Raul", "jose.raqul.quadross@jamesapp.com.br", "12345678", UserModel.UserRole.USER);

        userRepository.saveAll(Arrays.asList(userAdm, user1, user2, user3));

        ApiCustomerRequestDto customer1 = new ApiCustomerRequestDto("Jeferson Machado", "09256144913");
        ApiCustomerRequestDto customer2 = new ApiCustomerRequestDto("Felipe Prestes", "10739970062");
        ApiCustomerRequestDto customer3 = new ApiCustomerRequestDto("Jeferson Machado", "09256144913");
        ApiCustomerRequestDto customer4 = new ApiCustomerRequestDto("Jeferson Machado", "09256144913");

        customerService.save(new SaveCustomerDto(customer1), user1);
        customerService.save(new SaveCustomerDto(customer2), user1);
        customerService.save(new SaveCustomerDto(customer3), user2);
        customerService.save(new SaveCustomerDto(customer4), user3);

        ApiGroupBillRequestDto group1 = new ApiGroupBillRequestDto("Suno Research", BigDecimal.valueOf(130.0), GroupBillModel.BillingFrequency.MONTHLY, "Grupo suno de apostas esportivas");
        groupBillService.save(new SaveGroupBillDto(group1), user1);
    }
}
