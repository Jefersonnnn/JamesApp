package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.ApiTransactionRequestDto;
import com.jm.jamesapp.dtos.responses.TransactionResponseDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.SaveTransactionDto;
import com.jm.jamesapp.models.dto.UpdateTransactionDto;
import com.jm.jamesapp.security.IAuthenticationFacade;
import com.jm.jamesapp.security.exceptions.UnauthorizedException;
import com.jm.jamesapp.services.exceptions.ObjectNotFoundException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/transactions")
public class TransactionController {


    final IAuthenticationFacade authenticationFacade;
    final ICustomerService customerService;
    final ITransactionService transactionService;

    public TransactionController(IAuthenticationFacade authenticationFacade, ICustomerService customerService, ITransactionService transactionService) {
        this.authenticationFacade = authenticationFacade;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> register(@RequestBody @Valid ApiTransactionRequestDto transactionRequestDto) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        var customerSender = customerService.findByCpfCnpjAndUser(transactionRequestDto.customerCpfCnpj(), userModel);
        if (customerSender == null)
            throw new ObjectNotFoundException(transactionRequestDto.customerCpfCnpj(), "customer");

        var transaction = transactionService.register(new SaveTransactionDto(customerSender, transactionRequestDto));

        var transactionResponse = new TransactionResponseDto(transaction);

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @GetMapping()
    public ResponseEntity<Page<TransactionResponseDto>> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        Page<TransactionModel> transactionList = transactionService.findAllByUser(pageable, userModel);

        Page<TransactionResponseDto> responseList = transactionList.map(TransactionResponseDto::new);

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/customer/{idCustomer}")
    public ResponseEntity<Page<TransactionResponseDto>> getAllByCustomer(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @PathVariable(value = "idCustomer") UUID idCustomer) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel customer = customerService.findByIdAndUser(idCustomer, userModel);

        if (customer == null) throw new ObjectNotFoundException(idCustomer, "customer");

        Page<TransactionModel> transactionList = transactionService.findAllByCustomerAndUser(pageable, customer, userModel);

        Page<TransactionResponseDto> responseList = transactionList.map(TransactionResponseDto::new);

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> get(@PathVariable(value = "id") UUID id) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        TransactionModel transaction = transactionService.findByIdAndUser(id, userModel);

        if (transaction == null) throw new ObjectNotFoundException(id, "transaction");

        return ResponseEntity.status(HttpStatus.OK).body(new TransactionResponseDto(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> update(@PathVariable(value = "id") UUID id,
                                                         @RequestBody @Valid ApiTransactionRequestDto transactionRequestDto) {
        //Todo: Quando tenta atualziar uma transação de debito, a verificação está considerando o saldo atual
        // Mas deverá recalcular considerando o novo valor para ver se fica negativo.
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        TransactionModel transaction = transactionService.findByIdAndUser(id, userModel);
        if (transaction == null) throw new ObjectNotFoundException(id, "transaction");

        var customerSender = customerService.findByCpfCnpjAndUser(transactionRequestDto.customerCpfCnpj(), userModel);
        if (customerSender == null)
            throw new ObjectNotFoundException(transactionRequestDto.customerCpfCnpj(), "customer");

        transactionService.update(transaction, new UpdateTransactionDto(customerSender, transactionRequestDto), userModel);

        var transactionResponse = new TransactionResponseDto(transaction);

        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id) {
        // Todo: se deletar um transação de crédito o saldo do guerreiro pode ficar negativo.
        // precisar tratar.
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        TransactionModel transaction = transactionService.findByIdAndUser(id, userModel);

        if (transaction == null) throw new ObjectNotFoundException(id, "transaction");

        transactionService.delete(transaction);

        return ResponseEntity.noContent().build();
    }

}
