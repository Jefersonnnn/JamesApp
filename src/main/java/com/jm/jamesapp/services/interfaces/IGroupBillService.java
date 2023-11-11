package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;

import java.util.List;

public interface IGroupBillService extends IBaseService<GroupBillModel>{
    List<GroupBillModel> findAllByOwner(UserModel userModel);
}
