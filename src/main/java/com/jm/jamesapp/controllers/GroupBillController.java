package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.GroupBillRequestRecordDto;
import com.jm.jamesapp.dtos.responses.GroupBillResponseRecordDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.IGroupBillService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<GroupBillResponseRecordDto> saveGroupBill(@RequestBody @Valid GroupBillRequestRecordDto groupBillRequestRecordDto, Authentication authentication) {
        var ownerUser = (UserModel) authentication.getPrincipal();

        if(ownerUser == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var groupBillModel = new GroupBillModel();
        BeanUtils.copyProperties(groupBillRequestRecordDto, groupBillModel);

        groupBillModel.setUser(ownerUser);

        groupBillService.save(groupBillModel);

        var groupBillResponse = new GroupBillResponseRecordDto(groupBillModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(groupBillResponse);
    }

    @GetMapping
    public ResponseEntity<Page<GroupBillResponseRecordDto>> getAllGroupBills(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication){

        var ownerUser = (UserModel) authentication.getPrincipal();

        var groupBillsList = groupBillService.findAllByOwner(ownerUser);

//        if(groupBillsList.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        var responseList = new ArrayList<GroupBillResponseRecordDto>();

        for (var groupBill:groupBillsList) {
            responseList.add(new GroupBillResponseRecordDto(groupBill));
        }

        Page<GroupBillResponseRecordDto> pageResponse = new PageImpl<>(responseList, pageable, responseList.size());

        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupBillResponseRecordDto> getOneGroupBill(@PathVariable(value="id") UUID id){
        GroupBillModel groupBillO = groupBillService.findById(id);
        if(groupBillO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var groupBillResponse = new GroupBillResponseRecordDto(groupBillO.get());

        return ResponseEntity.status(HttpStatus.OK).body(groupBillResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupBillResponseRecordDto> updateGroupBill(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid GroupBillRequestRecordDto groupBillRequestRecordDto,
                                                                      Authentication authentication) {
        var ownerUser = (UserModel) authentication.getPrincipal();

        GroupBillModel groupBillO = groupBillService.findById(id);
        if(groupBillO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var groupBill = groupBillO.get();
        BeanUtils.copyProperties(groupBillRequestRecordDto, groupBill);
        groupBill.setUpdatedBy(ownerUser.getId());

        groupBillService.save(groupBill);

        var groupBillResponse = new GroupBillResponseRecordDto(groupBill);

        return ResponseEntity.status(HttpStatus.OK).body(groupBillResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroupBill(@PathVariable(value="id") UUID id){
        GroupBillModel groupBillO = groupBillService.findById(id);
        if(groupBillO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupBillService.delete(groupBillO.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
