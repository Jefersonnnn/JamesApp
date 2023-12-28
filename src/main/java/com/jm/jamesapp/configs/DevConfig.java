package com.jm.jamesapp.configs;

import com.jm.jamesapp.dtos.requests.ApiCustomerRequestDto;
import com.jm.jamesapp.dtos.requests.ApiGroupBillRequestDto;
import com.jm.jamesapp.dtos.requests.ApiTransactionRequestDto;
import com.jm.jamesapp.dtos.requests.ApiUserRequestDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.*;
import com.jm.jamesapp.repositories.CustomerRepository;
import com.jm.jamesapp.repositories.GroupBillRepository;
import com.jm.jamesapp.repositories.TransactionRepository;
import com.jm.jamesapp.repositories.UserRepository;
import com.jm.jamesapp.services.CustomerService;
import com.jm.jamesapp.services.GroupBillService;
import com.jm.jamesapp.services.TransactionService;
import com.jm.jamesapp.services.UserService;
import com.jm.jamesapp.services.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GroupBillRepository groupBillRepository;

    @Autowired
    private GroupBillService groupBillService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public void run(String... args) throws Exception {

//        try {
//
//
//            transactionRepository.deleteAll();
//            groupBillRepository.deleteAll();
//            customerRepository.deleteAll();
//            userRepository.deleteAll();
//
//            ApiUserRequestDto u1 = new ApiUserRequestDto("Jeferson Machado", "jefersonm", "jeferson.machado@jamesapp.com.br", "12345678", UserModel.UserRole.ADMIN);
//            ApiUserRequestDto u2 = new ApiUserRequestDto("Douglas Giovanellas", "giova nellas", "doug.giova.nellas@jamesapp.com.br", "12345678", UserModel.UserRole.USER);
//            ApiUserRequestDto u3 = new ApiUserRequestDto("Felipe Prestes", "Felipe Prestes", "felipe.prestes@jamesapp.com.br", "12345678", UserModel.UserRole.USER);
//            ApiUserRequestDto u4 = new ApiUserRequestDto("José Raul", "rei delas", "jose.raqul.quadross@jamesapp.com.br", "12345678", UserModel.UserRole.USER);
//
//            UserModel user1 = userService.save(new SaveUserDto(u1));
//            UserModel user2 = userService.save(new SaveUserDto(u2));
//            UserModel user3 = userService.save(new SaveUserDto(u3));
//            userService.save(new SaveUserDto(u4));
//
//            ApiCustomerRequestDto customer1 = new ApiCustomerRequestDto("Jeferson Machado", "09256144913");
//            ApiCustomerRequestDto customer2 = new ApiCustomerRequestDto("Felipe Prestes", "10739970062");
//            ApiCustomerRequestDto customer3 = new ApiCustomerRequestDto("Jeferson Machado", "09256144913");
//            ApiCustomerRequestDto customer4 = new ApiCustomerRequestDto("Jeferson Machado", "09256144913");
//
//            customerService.save(new SaveCustomerDto(customer1), user1);
//            customerService.save(new SaveCustomerDto(customer2), user1);
//            customerService.save(new SaveCustomerDto(customer3), user2);
//            customerService.save(new SaveCustomerDto(customer4), user3);
//
//            ApiGroupBillRequestDto group1 = new ApiGroupBillRequestDto("Suno Research", BigDecimal.valueOf(130.0), GroupBillModel.BillingFrequency.MONTHLY, "Grupo suno de apostas esportivas");
//            groupBillService.save(new SaveGroupBillDto(group1), user2);
//
//            ApiTransactionRequestDto tran1 = new ApiTransactionRequestDto(Date.from(Instant.now()), "Pagamento aleatório", "09256144913", BigDecimal.valueOf(25));
//            ApiTransactionRequestDto tran2 = new ApiTransactionRequestDto(Date.from(Instant.now()), "Pagamento aleatório", "09256144913", BigDecimal.valueOf(100));
//            ApiTransactionRequestDto tran3 = new ApiTransactionRequestDto(Date.from(Instant.now()), "Pagamento aleatório", "09256144913", BigDecimal.valueOf(33));
//
//            transactionService.register(new SaveTransactionDto(tran1), user2);
//            transactionService.register(new SaveTransactionDto(tran2), user2);
//            transactionService.register(new SaveTransactionDto(tran3), user2);
//
//            SaveTransactionDto debitGroup = new SaveTransactionDto();
//            debitGroup.setAmount(BigDecimal.valueOf(-25));
//            debitGroup.setDescription("Pagamento do grupo Suno bolado");
//            debitGroup.setDueDate(Date.from(Instant.now()));
//            debitGroup.setCustomerCpfCnpj("09256144913");
//
//            transactionService.register(debitGroup, user2);
//
//            SaveTransactionDto debitGroup2 = new SaveTransactionDto();
//            debitGroup2.setAmount(BigDecimal.valueOf(-133));
//            debitGroup2.setDescription("Pagamento do grupo Suno bolado");
//            debitGroup2.setDueDate(Date.from(Instant.now()));
//            debitGroup2.setCustomerCpfCnpj("09256144913");
//
//            transactionService.register(debitGroup2, user2);
//
//        } catch (BusinessException e ){
//            System.out.println(e.getMessage());
//        }
    }
}
