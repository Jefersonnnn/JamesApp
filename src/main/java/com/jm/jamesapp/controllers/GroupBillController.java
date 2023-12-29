package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.ApiGroupBillRequestDto;
import com.jm.jamesapp.dtos.responses.CustomerResponseDto;
import com.jm.jamesapp.dtos.responses.GroupBillResponseDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveGroupBillDto;
import com.jm.jamesapp.models.dto.UpdateGroupBillDto;
import com.jm.jamesapp.security.IAuthenticationFacade;
import com.jm.jamesapp.security.exceptions.UnauthorizedException;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.exceptions.ObjectNotFoundException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.IGroupBillService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/groupbills")
public class GroupBillController {
    final IAuthenticationFacade authenticationFacade;
    final IUserService userService;
    final ICustomerService customerService;
    final IGroupBillService groupBillService;

    public GroupBillController(IAuthenticationFacade authenticationFacade, IUserService userService, ICustomerService customerService, IGroupBillService groupBillService) {
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
        this.customerService = customerService;
        this.groupBillService = groupBillService;
    }

    @PostMapping
    public ResponseEntity<GroupBillResponseDto> save(@RequestBody @Valid ApiGroupBillRequestDto apiGroupBillRequestDto) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();

        if (userModel == null) throw new UnauthorizedException();

        var groupBill = groupBillService.save(new SaveGroupBillDto(apiGroupBillRequestDto), userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(new GroupBillResponseDto(groupBill));
    }

    @GetMapping
    public ResponseEntity<Page<GroupBillResponseDto>> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        Page<GroupBillModel> groupBillsList = groupBillService.findAllByUser(pageable, userModel);

        Page<GroupBillResponseDto> pageResponse = groupBillsList.map(GroupBillResponseDto::new);

        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupBillResponseDto> getOne(@PathVariable(value = "id") UUID id) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        GroupBillModel groupBillO = groupBillService.findById(id);
        if (groupBillO == null) throw new ObjectNotFoundException(id, "group_bill");

        return ResponseEntity.status(HttpStatus.OK).body(new GroupBillResponseDto(groupBillO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupBillResponseDto> update(@PathVariable(value = "id") UUID id,
                                                       @RequestBody @Valid ApiGroupBillRequestDto apiGroupBillRequestDto) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        GroupBillModel groupBillO = groupBillService.findById(id);
        if (groupBillO == null) throw new ObjectNotFoundException(id, "group_bill");

        GroupBillModel groupBillUpdated = groupBillService.update(groupBillO, new UpdateGroupBillDto(apiGroupBillRequestDto, id), userModel);

        return ResponseEntity.status(HttpStatus.OK).body(new GroupBillResponseDto(groupBillUpdated));
    }

    @PostMapping("/{groupBillId}/customer/{customerId}")
    public ResponseEntity<Void> addCustomer(@PathVariable(value = "groupBillId") UUID groupBillId, @PathVariable(value = "customerId") UUID customerId) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        GroupBillModel groupBill = groupBillService.findByIdAndUser(groupBillId, userModel);
        if (groupBill == null) throw new ObjectNotFoundException(groupBillId, "group_bill");

        CustomerModel customer = customerService.findByIdAndUser(customerId, userModel);
        if (customer == null) throw new ObjectNotFoundException(customerId, "customer");

        if(groupBill.getCustomers().contains(customer)) throw new BusinessException("Cliente já está no grupo");

        groupBillService.addCustomer(groupBill, customer);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupBillId}/customer")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers(@PageableDefault(sort = "dueDate", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable(value = "groupBillId") UUID groupBillId) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        var groupBill = groupBillService.findById(groupBillId);
        if(groupBill == null) throw new ObjectNotFoundException(groupBillId, "group_bill");

        List<CustomerResponseDto> pageResponse = groupBill.getCustomers().stream().map(CustomerResponseDto::new).toList();

        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id, Authentication authentication) {
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        GroupBillModel groupBillO = groupBillService.findByIdAndUser(id, userModel);
        if (groupBillO == null) throw new ObjectNotFoundException(id, "group_bill");

        groupBillService.delete(groupBillO);
        return ResponseEntity.noContent().build();
    }

}
