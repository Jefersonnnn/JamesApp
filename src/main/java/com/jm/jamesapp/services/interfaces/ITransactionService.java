package com.jm.jamesapp.services.interfaces;


import com.jm.jamesapp.models.TransactionModel;

public interface ITransactionService extends IBaseService<TransactionModel>{

    TransactionModel register(TransactionModel transaction);

}
