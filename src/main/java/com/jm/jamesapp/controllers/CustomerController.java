package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.ApiCustomerRequestDto;
import com.jm.jamesapp.dtos.responses.CustomerResponseDto;
import com.jm.jamesapp.dtos.responses.TransactionResponseDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.transaction.TransactionModel;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.SaveCustomerDto;
import com.jm.jamesapp.models.dto.UpdateCustomerDto;
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

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/customers")
public class CustomerController {

    private final IAuthenticationFacade authenticationFacade;

    final ICustomerService customerService;

    final ITransactionService transactionService;

    public CustomerController(IAuthenticationFacade authenticationFacade, ICustomerService customerService, ITransactionService transactionService) {
        this.authenticationFacade = authenticationFacade;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> save(@RequestBody @Valid ApiCustomerRequestDto apiCustomerRequestDto) {
        var userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel savedCustomer = customerService.save(new SaveCustomerDto(apiCustomerRequestDto), userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerResponseDto(savedCustomer));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseDto>> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        Page<CustomerModel> customersList = customerService.findAllByUser(pageable, userModel);

        Page<CustomerResponseDto> pageResponse = customersList.map(model -> {
            BigDecimal balance = customerService.calculateBalance(model);
            return new CustomerResponseDto(model, balance);
        });

        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> get(@PathVariable(value="id") UUID id){
        var userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel customer = customerService.findByIdAndUser(id, userModel);
        if (customer == null) throw new ObjectNotFoundException(id, "customer");

        BigDecimal balance = customerService.calculateBalance(customer);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseDto(customer, balance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> update(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid ApiCustomerRequestDto apiCustomerRequestDto) {
        var userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel customer = customerService.findByIdAndUser(id, userModel);
        if (customer == null) throw new ObjectNotFoundException(id, "customer");

        CustomerModel updatedCustomer = customerService.update(customer, new UpdateCustomerDto(apiCustomerRequestDto), userModel);

        BigDecimal balance = customerService.calculateBalance(customer);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseDto(updatedCustomer, balance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value="id") UUID id) {
        var userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel customer = customerService.findByIdAndUser(id, userModel);
        if (customer == null) throw new ObjectNotFoundException(id, "customer");

        customerService.delete(customer);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{customerId}/transactions")
    public ResponseEntity<Page<TransactionResponseDto>> listTransactions(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @PathVariable(value = "customerId") UUID customerId) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel customer = customerService.findByIdAndUser(customerId, userModel);
        if (customer == null) throw new ObjectNotFoundException(customerId, "customer");

        Page<TransactionModel> transactionList = transactionService.findAllByCustomerAndUser(pageable, customer, userModel);

        Page<TransactionResponseDto> responseList = transactionList.map(TransactionResponseDto::new);

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }
}
