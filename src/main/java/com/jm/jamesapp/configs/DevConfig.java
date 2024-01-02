package com.jm.jamesapp.configs;

import com.jm.jamesapp.repositories.BillGroupRepository;
import com.jm.jamesapp.repositories.CustomerRepository;
import com.jm.jamesapp.repositories.TransactionRepository;
import com.jm.jamesapp.repositories.UserRepository;
import com.jm.jamesapp.services.BillGroupService;
import com.jm.jamesapp.services.CustomerService;
import com.jm.jamesapp.services.TransactionService;
import com.jm.jamesapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
    private BillGroupRepository groupBillRepository;

    @Autowired
    private BillGroupService groupBillService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public void run(String... args) throws Exception {
//        try {
////            transactionRepository.deleteAll();
////            groupBillRepository.deleteAll();
////            customerRepository.deleteAll();
////            userRepository.deleteAll();
//
////            ApiUserRequestDto u1 = new ApiUserRequestDto("Jeferson Machado", "jefersonm", "jeferson.machado@jamesapp.com.br", "12345678", UserRole.ADMIN);
////            UserModel user1 = userService.save(new SaveUserDto(u1), null);
//            UserModel userAdmin = userService.findByUsername("jefersonm");
//
//            try {
//                ApiUserRequestDto u2 = new ApiUserRequestDto("Douglas Giovanellas", "giova nellas", "doug.giova.nellas@jamesapp.com.br", "12345678", UserRole.ADMIN);
//                UserModel user2 = userService.save(new SaveUserDto(u2), userAdmin);
//            } catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//
//            try {
//                ApiUserRequestDto u3 = new ApiUserRequestDto("Felipe Prestes", "Felipe Prestes", "felipe.prestes@jamesapp.com.br", "12345678", UserRole.USER);
//                UserModel user3 = userService.save(new SaveUserDto(u3), userAdmin);
//            } catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//
//            CustomerModel c1;
//            try {
//                ApiCustomerRequestDto customer1 = new ApiCustomerRequestDto("Jeferson Machado", "09256144913");
//                c1 = customerService.save(new SaveCustomerDto(customer1), userAdmin);
//            } catch (Exception e){
//                c1 = customerService.findByCpfCnpjAndUser("09256144913", userAdmin);
//                System.out.println(e.getMessage());
//            }
//
//            CustomerModel c2;
//            try {
//                ApiCustomerRequestDto customer2 = new ApiCustomerRequestDto("Felipe Prestes", "10739970062");
//                c2 = customerService.save(new SaveCustomerDto(customer2), userAdmin);
//            } catch (Exception e){
//                c2 = customerService.findByCpfCnpjAndUser("10739970062", userAdmin);
//                System.out.println(e.getMessage());
//            }
//
//            BillGroupModel g1;
//            try {
//                ApiGroupBillRequestDto group1 = new ApiGroupBillRequestDto("Suno Research", BigDecimal.valueOf(130.0), BillingFrequency.MONTHLY, "Grupo suno de apostas esportivas");
//                g1 = groupBillService.save(new SaveGroupBillDto(group1), userAdmin);
//            } catch (Exception e){
//                g1 = groupBillService.findLastByUser(userAdmin);
//            }
//
//            try {
//                groupBillService.addCustomer(g1, c1);
//                groupBillService.addCustomer(g1, c2);
//            } catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//
//
//
//            ApiTransactionRequestDto tran1 = new ApiTransactionRequestDto(c1.getId(), Instant.now(), "Pagamento aleatório", BigDecimal.valueOf(25), TransactionType.PAYMENT_RECEIVED);
//            ApiTransactionRequestDto tran2 = new ApiTransactionRequestDto(c1.getId(), Instant.now(), "Pagamento aleatório", BigDecimal.valueOf(100), TransactionType.PAYMENT_RECEIVED);
//            ApiTransactionRequestDto tran3 = new ApiTransactionRequestDto(c1.getId(), Instant.now(), "Pagamento aleatório", BigDecimal.valueOf(33), TransactionType.PAYMENT_RECEIVED);
//
//            transactionService.register(c1, new SaveTransactionDto(tran1));
//            transactionService.register(c1, new SaveTransactionDto(tran2));
//            transactionService.register(c1, new SaveTransactionDto(tran3));
//
//        } catch (BusinessException e ){
//            System.out.println(e.getMessage());
//        }
    }
}
