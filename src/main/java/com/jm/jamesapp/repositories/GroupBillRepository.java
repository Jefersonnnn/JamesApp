package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupBillRepository extends JpaRepository<GroupBillModel, UUID> {

    List<GroupBillModel> findAllByOwner(UserModel userModel);
}
