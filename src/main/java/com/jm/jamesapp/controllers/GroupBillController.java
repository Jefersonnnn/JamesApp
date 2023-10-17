package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.GroupBillRequestRecordDto;
import com.jm.jamesapp.dtos.responses.GroupBillResponseRecordDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.IGroupBillService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/groupbills")
public class GroupBillController {

    final IUserService userService;
    final ICustomerService customerService;
    final IGroupBillService groupBillService;

    public GroupBillController(IUserService userService, ICustomerService customerService, IGroupBillService groupBillService) {
        this.userService = userService;
        this.customerService = customerService;
        this.groupBillService = groupBillService;
    }


    @PostMapping
    public ResponseEntity<Object> saveGroupBill(@RequestBody @Valid GroupBillRequestRecordDto groupBillRequestRecordDto) {
        var ownerUser = userService.findById(UUID.fromString(groupBillRequestRecordDto.ownerId()));

        if(ownerUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner not found.");
        }

        var groupBillModel = new GroupBillModel();
        BeanUtils.copyProperties(groupBillRequestRecordDto, groupBillModel);

        groupBillModel.setOwner(ownerUser.get());

        if(groupBillRequestRecordDto.customersIds() != null){
            List<CustomerModel> customerModelList = new ArrayList<>();
            for (var customerId: groupBillRequestRecordDto.customersIds()) {
                var customer = customerService.findById(customerId);
                customer.ifPresent(customerModelList::add);
            }
            groupBillModel.setCustomers(customerModelList);
        }

        groupBillService.save(groupBillModel);

        var groupBillResponse = new GroupBillResponseRecordDto(
                groupBillModel.getId(),
                groupBillModel.getOwner().getId(),
                groupBillModel.getName(),
                groupBillModel.getTotalPayment(),
                groupBillModel.getDueDateDay(),
                groupBillModel.getDueDateHour(),
                groupBillModel.getDescription());

        return ResponseEntity.status(HttpStatus.CREATED).body(groupBillResponse);
    }

    @GetMapping
    public ResponseEntity<Page<GroupBillModel>> getAllGroupBills(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        var groupBillsList = groupBillService.findAll(pageable);
        if(groupBillsList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        var responseList = new ArrayList<GroupBillResponseRecordDto>();
//
//        for (var groupBill:groupBillsList) {
//            var cResponse = new GroupBillResponseRecordDto(
//                    groupBill.getId(),
//                    groupBill.getOwner().getId(),
//                    groupBill.getName(),
//                    groupBill.getTotalPayment(),
//                    groupBill.getDueDateDay(),
//                    groupBill.getDueDateHour(),
//                    groupBill.getDescription()
//            );
//            responseList.add(cResponse);
//        }

        return ResponseEntity.status(HttpStatus.OK).body(groupBillsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneGroupBill(@PathVariable(value="id") UUID id){
        Optional<GroupBillModel> groupBillO = groupBillService.findById(id);
        if(groupBillO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GroupBill not found.");
        }

        var groupBill = groupBillO.get();
        var groupBillResponse = new GroupBillResponseRecordDto(
                groupBill.getId(),
                groupBill.getOwner().getId(),
                groupBill.getName(),
                groupBill.getTotalPayment(),
                groupBill.getDueDateDay(),
                groupBill.getDueDateHour(),
                groupBill.getDescription()
        );

        return ResponseEntity.status(HttpStatus.OK).body(groupBillResponse);
    }

    @GetMapping("/{id}/customers")
    public ResponseEntity<Object> getAllCostumersInGroupBill(@PathVariable(value="id") UUID id){
        Optional<GroupBillModel> groupBillO = groupBillService.findById(id);
        if(groupBillO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GroupBill not found.");
        }

        var groupBill = groupBillO.get();

        return ResponseEntity.status(HttpStatus.OK).body(groupBill.getCustomers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGroupBill(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid GroupBillRequestRecordDto groupBillRequestRecordDto) {
        Optional<GroupBillModel> groupBillO = groupBillService.findById(id);
        if(groupBillO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GroupBill not found.");
        }
        var groupBill = groupBillO.get();
        BeanUtils.copyProperties(groupBillRequestRecordDto, groupBill);
        groupBillService.save(groupBill);

        var groupBillResponse = new GroupBillResponseRecordDto(
                groupBill.getId(),
                groupBill.getOwner().getId(),
                groupBill.getName(),
                groupBill.getTotalPayment(),
                groupBill.getDueDateDay(),
                groupBill.getDueDateHour(),
                groupBill.getDescription()
        );

        return ResponseEntity.status(HttpStatus.OK).body(groupBillResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroupBill(@PathVariable(value="id") UUID id){
        Optional<GroupBillModel> groupBillO = groupBillService.findById(id);
        if(groupBillO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GroupBill not found.");
        }
        groupBillService.delete(groupBillO.get());
        return ResponseEntity.status(HttpStatus.OK).body("GroupBill deleted successfully.");
    }

}
