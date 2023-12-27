package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.ApiTransactionRequestDto;
import com.jm.jamesapp.dtos.responses.TransactionResponseDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.UpdateTransactionDto;
import com.jm.jamesapp.security.exceptions.UnauthorizedException;
import com.jm.jamesapp.services.exceptions.ObjectNotFoundException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<TransactionResponseDto> registerTransaction(@RequestBody @Valid ApiTransactionRequestDto transactionRequestDto,
                                                                      Authentication authentication) {

        UserModel userModel = (UserModel) authentication.getPrincipal();
        if(userModel == null) throw new UnauthorizedException();

        var customerSender = customerService.findByCpfCnpjAndUser(transactionRequestDto.customerCpfCnpj(), userModel);

        if(customerSender == null) {
            // Todo: Gerar uma execão personaliada?
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var newTransaction = getTransactionModel(transactionRequestDto, userModel, customerSender);

        transactionService.save(newTransaction);

        var transactionResponse = new TransactionResponseDto(newTransaction);

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @GetMapping()
    public ResponseEntity<Page<TransactionResponseDto>> getAllTransactions(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication){

        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        Page<TransactionModel> transactionList = transactionService.findAllByUser(pageable, userModel);

        Page<TransactionResponseDto> responseList = transactionList.map(TransactionResponseDto::new);

//        Page<TransactionResponseDto> pageResponse = new PageImpl<>(responseList, pageable, responseList.size());
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> get(@PathVariable(value="id") UUID id, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        TransactionModel transaction = transactionService.findByIdAndUser(id, userModel);

        if(transaction == null) throw new ObjectNotFoundException(id, "transaction");

        return ResponseEntity.status(HttpStatus.OK).body(new TransactionResponseDto(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> updateTransaction(@PathVariable(value="id") UUID id,
                                                                    @RequestBody @Valid ApiTransactionRequestDto transactionRequestDto, Authentication authentication) {
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        TransactionModel transaction = transactionService.findByIdAndUser(id, userModel);

        if(transaction == null) throw new ObjectNotFoundException(id, "transaction");

        transactionService.update(transaction, new UpdateTransactionDto(transactionRequestDto), userModel);

        var transactionResponse = new TransactionResponseDto(transaction);

        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable(value="id") UUID id, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        TransactionModel transaction = transactionService.findByIdAndUser(id, userModel);

        if(transaction == null) throw new ObjectNotFoundException(id, "transaction");

        transactionService.delete(transaction);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static TransactionModel getTransactionModel(ApiTransactionRequestDto transactionRequestDto, UserModel userModel, CustomerModel customerSender) {
        var newTransaction = new TransactionModel();

        newTransaction.setDueDate(transactionRequestDto.dueDate());
        newTransaction.setDescription(transactionRequestDto.description());

        newTransaction.setStatus(TransactionModel.StatusTransaction.COMPLETED);
        newTransaction.setTypeTransaction(TransactionModel.TypeTransaction.PAYMENT_RECEIVED);
        newTransaction.setUser(userModel);
        newTransaction.setCustomer(customerSender);

        newTransaction.setAmount(transactionRequestDto.amount());
        return newTransaction;
    }

}
