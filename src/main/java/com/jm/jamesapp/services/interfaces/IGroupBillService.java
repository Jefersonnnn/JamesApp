package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.SaveBillGroupDto;
import com.jm.jamesapp.models.dto.UpdateBillGroupDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface IGroupBillService{

    @Transactional
    BillGroupModel save(SaveBillGroupDto saveBillGroupDto, UserModel userModel);

    @Transactional
    BillGroupModel update(BillGroupModel groupBill, UpdateBillGroupDto updateBillGroupDto, UserModel userModel);

    Page<BillGroupModel> findAll(Pageable pageable);

    @Nullable
    BillGroupModel findById(UUID id);

    @Nullable
    BillGroupModel findLastByUser(UserModel user);

    @Transactional
    void delete(BillGroupModel objModel);

    Page<BillGroupModel> findAllByUser(Pageable pageable, UserModel userModel);

    @Nullable
    BillGroupModel findByIdAndUser(UUID id, UserModel userModel);

    @Transactional
    void addCustomer(BillGroupModel groupBill, CustomerModel customer);

    @Transactional
    void closeAndSave(BillGroupModel billGroup);
}
