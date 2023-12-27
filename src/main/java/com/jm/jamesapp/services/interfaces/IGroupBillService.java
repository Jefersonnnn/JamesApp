package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

public interface IGroupBillService{

    @Transactional
    GroupBillModel save(GroupBillModel objModel);

    @Transactional
    GroupBillModel update(GroupBillModel objModel);

    Page<GroupBillModel> findAll(Pageable pageable);

    @Nullable
    GroupBillModel findById(UUID id);

    @Transactional
    void delete(GroupBillModel objModel);

    Page<GroupBillModel> findAllByUser(Pageable pageable, UserModel userModel);
}
