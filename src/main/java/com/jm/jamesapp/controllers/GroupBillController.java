package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.ApiGroupBillRequestDto;
import com.jm.jamesapp.dtos.responses.GroupBillResponseDto;
import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveGroupBillDto;
import com.jm.jamesapp.models.dto.UpdateGroupBillDto;
import com.jm.jamesapp.security.exceptions.UnauthorizedException;
import com.jm.jamesapp.services.exceptions.ObjectNotFoundException;
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
    public ResponseEntity<GroupBillResponseDto> saveGroupBill(@RequestBody @Valid ApiGroupBillRequestDto apiGroupBillRequestDto, Authentication authentication) {
        UserModel userModel = (UserModel) authentication.getPrincipal();

        if(userModel == null) throw new UnauthorizedException();

        var groupBill = groupBillService.save(new SaveGroupBillDto(apiGroupBillRequestDto), userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(new GroupBillResponseDto(groupBill));
    }

    @GetMapping
    public ResponseEntity<Page<GroupBillResponseDto>> getAllGroupBills(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        Page<GroupBillModel> groupBillsList = groupBillService.findAllByUser(pageable, userModel);

        Page<GroupBillResponseDto> pageResponse = groupBillsList.map(GroupBillResponseDto::new);
//        Page<GroupBillResponseRecordDto> pageResponse = new PageImpl<>(responseList, pageable, responseList.size());

        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupBillResponseDto> getOneGroupBill(@PathVariable(value="id") UUID id, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        GroupBillModel groupBillO = groupBillService.findById(id);
        if(groupBillO == null) throw new ObjectNotFoundException(id, "group_bill");

        return ResponseEntity.status(HttpStatus.OK).body(new GroupBillResponseDto(groupBillO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupBillResponseDto> updateGroupBill(@PathVariable(value="id") UUID id,
                                                                @RequestBody @Valid ApiGroupBillRequestDto apiGroupBillRequestDto,
                                                                Authentication authentication) {
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        GroupBillModel groupBillO = groupBillService.findById(id);
        if(groupBillO== null) throw new ObjectNotFoundException(id, "group_bill");

        GroupBillModel groupBillUpdated = groupBillService.update(groupBillO, new UpdateGroupBillDto(apiGroupBillRequestDto, id), userModel);

        return ResponseEntity.status(HttpStatus.OK).body(new GroupBillResponseDto(groupBillUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroupBill(@PathVariable(value="id") UUID id, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        GroupBillModel groupBillO = groupBillService.findByIdAndUser(id, userModel);
        if(groupBillO == null) throw new ObjectNotFoundException(id, "group_bill");

        groupBillService.delete(groupBillO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
