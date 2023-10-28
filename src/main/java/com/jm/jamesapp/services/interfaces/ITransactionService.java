package com.jm.jamesapp.services.interfaces;


import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;

import java.util.List;

public interface ITransactionService extends IBaseService<TransactionModel>{

    TransactionModel register(TransactionModel transaction);

    List<TransactionModel> findAllByOwner(UserModel userModel);

}
