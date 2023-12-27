package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.TransactionRequestRecordDto;
import com.jm.jamesapp.dtos.responses.TransactionResponseRecordDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/transactions")
public class TransactionController {

    final ICustomerService customerService;
    final IUserService userService;

    final ITransactionService transactionService;

    public TransactionController(ICustomerService customerService, IUserService userService, ITransactionService transactionService) {
        this.customerService = customerService;
        this.userService = userService;
        this.transactionService = transactionService;
    }


    @PostMapping
    public ResponseEntity<Object> registerTransaction(@RequestBody @Valid TransactionRequestRecordDto transactionRequestDto,
                                                      Authentication authentication) {

        var ownerUser = (UserModel) authentication.getPrincipal();

        if(ownerUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var customerSender = customerService.findByCpfCnpjAndOwner(transactionRequestDto.customerCpfCnpj(), ownerUser);

        if(customerSender.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var newTransaction = getTransactionModel(transactionRequestDto, ownerUser, customerSender.get());

        transactionService.save(newTransaction);

        var transactionResponse = new TransactionResponseRecordDto(newTransaction);

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @GetMapping()
    public ResponseEntity<Page<TransactionResponseRecordDto>> getAllTransactions(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication){

        var ownerUser = (UserModel) authentication.getPrincipal();

        var transactionList = transactionService.findAllByOwner(ownerUser);

//        if(transactionList.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        var responseList = new ArrayList<TransactionResponseRecordDto>();

        for (var transaction:transactionList) {
            var tResponse = new TransactionResponseRecordDto(transaction);
            responseList.add(tResponse);
        }

        Page<TransactionResponseRecordDto> pageResponse = new PageImpl<>(responseList, pageable, responseList.size());
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseRecordDto> getOneTransaction(@PathVariable(value="id") UUID id){
        Optional<TransactionModel> transactionO = transactionService.findById(id);

        if(transactionO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var transactionResponse = new TransactionResponseRecordDto(transactionO.get());

        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTransaction(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid TransactionRequestRecordDto transactionRequestRecordDto) {
        Optional<TransactionModel> transactionO = transactionService.findById(id);

        if(transactionO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var transaction = transactionO.get();

        BeanUtils.copyProperties(transactionRequestRecordDto, transaction);

        transactionService.update(transaction);

        var transactionResponse = new TransactionResponseRecordDto(transaction);

        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable(value="id") UUID id){
        Optional<TransactionModel> transactionO = transactionService.findById(id);

        if(transactionO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        transactionService.delete(transactionO.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static TransactionModel getTransactionModel(TransactionRequestRecordDto transactionRequestDto, UserModel ownerUser, CustomerModel customerSender) {
        var newTransaction = new TransactionModel();

        newTransaction.setDueDate(transactionRequestDto.dueDate());
        newTransaction.setDescription(transactionRequestDto.description());

        newTransaction.setStatus(TransactionModel.StatusTransaction.COMPLETED);
        newTransaction.setTypeTransaction(TransactionModel.TypeTransaction.PAYMENT_RECEIVED);
        newTransaction.setOwner(ownerUser);
        newTransaction.setCustomer(customerSender);

        newTransaction.setAmount(transactionRequestDto.amount());
        return newTransaction;
    }

}
