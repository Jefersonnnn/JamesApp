package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {
    List<TransactionModel> findAllByOwner(UserModel userModel);
}
