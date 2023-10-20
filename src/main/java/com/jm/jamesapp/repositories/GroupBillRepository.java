package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.GroupBillModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupBillRepository extends JpaRepository<GroupBillModel, UUID> {
}
