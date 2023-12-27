package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveGroupBillDto;
import com.jm.jamesapp.models.dto.UpdateGroupBillDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface IGroupBillService{

    @Transactional
    GroupBillModel save(SaveGroupBillDto saveGroupBillDto, UserModel userModel);

    @Transactional
    GroupBillModel update(GroupBillModel groupBill, UpdateGroupBillDto updateGroupBillDto, UserModel userModel);

    Page<GroupBillModel> findAll(Pageable pageable);

    @Nullable
    GroupBillModel findById(UUID id);

    @Transactional
    void delete(GroupBillModel objModel);

    Page<GroupBillModel> findAllByUser(Pageable pageable, UserModel userModel);

    @Nullable
    GroupBillModel findByIdAndUser(UUID id, UserModel userModel);

    @Transactional
    void addCustomer(GroupBillModel groupBill, CustomerModel customer);
}
