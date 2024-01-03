package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.billgroup.BillGroupClosureModel;
import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.dto.SaveTransactionDto;
import com.jm.jamesapp.models.dto.UpdateBillGroupDto;
import com.jm.jamesapp.models.transaction.enums.TransactionType;
import com.jm.jamesapp.repositories.BillGroupClosureRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.IGroupBillClosureService;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import com.jm.jamesapp.utils.BigDecimalUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class BillGroupClosureService implements IGroupBillClosureService {

    final BillGroupClosureRepository billGroupClosureRepository;

    final ICustomerService customerService;

    final ITransactionService transactionService;

    public BillGroupClosureService(BillGroupClosureRepository billGroupClosureRepository, ICustomerService customerService, ITransactionService transactionService) {
        this.billGroupClosureRepository = billGroupClosureRepository;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @Override
    @Transactional
    public BillGroupClosureModel closeAndSave(BillGroupModel billGroup) {
        if (!billGroup.shouldCloseGroup()){
            throw new BusinessException("Aguarde o fechamento do grupo");
        }
        Set<CustomerModel> customers = getAvailableCustomers(billGroup.getCustomers(), billGroup.getValue());

        if(customers.isEmpty()){
            throw new BusinessException("Sem clientes com saldo para fechar o grupo");
        }

        var valuePerCustomer = billGroup.getValue().divide(BigDecimal.valueOf(customers.size()));

        for (CustomerModel customer : customers){
            transactionService.register(customer, new SaveTransactionDto(Instant.now(), String.format("Pagamento do grupo %s.", billGroup.getName()), valuePerCustomer, TransactionType.BILL_GROUP_PAID));
        }

        BillGroupClosureModel billGroupClosure = new BillGroupClosureModel(billGroup, Instant.now(), billGroup.getValue(), customers);

        return billGroupClosureRepository.save(billGroupClosure);
    }

    @Override
    public BillGroupClosureModel update(BillGroupClosureModel groupBill, UpdateBillGroupDto updateBillGroupDto) {
        return null;
    }

    @Override
    public void delete(BillGroupClosureModel objModel) {

    }

    @Override
    public BillGroupClosureModel findById(UUID id) {
        return null;
    }

    @Override
    public void addCustomer(BillGroupClosureModel groupBill, CustomerModel customer) {

    }

    private Set<CustomerModel> getAvailableCustomers(Set<CustomerModel> customers, BigDecimal totalToPay){
        BigDecimal valuePerShare = BigDecimalUtils.divideValueMonetary(totalToPay, BigDecimal.valueOf(customers.size()));

        Set<CustomerModel> customersWithBalanceAvailable = new HashSet<>();
        for(CustomerModel customer : customers){
            var userBalance = customerService.calculateBalance(customer);
            if (userBalance.compareTo(valuePerShare) >= 0){
                customersWithBalanceAvailable.add(customer);
            }
        }
        if (customersWithBalanceAvailable.containsAll(customers) || customersWithBalanceAvailable.isEmpty()){
            return customersWithBalanceAvailable;
        }
        return getAvailableCustomers(customersWithBalanceAvailable, totalToPay);
    }
}
