package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.billgroup.BillGroupClosureModel;
import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.dto.SaveBillGroupDto;
import com.jm.jamesapp.models.dto.UpdateBillGroupDto;
import jakarta.transaction.Transactional;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface IGroupBillClosureService {

    @Transactional
    BillGroupClosureModel closeAndSave(BillGroupModel billGroup);

    @Transactional
    BillGroupClosureModel update(BillGroupClosureModel groupBill, UpdateBillGroupDto updateBillGroupDto);

    @Transactional
    void delete(BillGroupClosureModel objModel);

    @Nullable
    BillGroupClosureModel findById(UUID id);

    @Transactional
    void addCustomer(BillGroupClosureModel groupBill, CustomerModel customer);
}
