package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.user.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupBillRepository extends JpaRepository<GroupBillModel, UUID> {
    Page<GroupBillModel> findAllByUser(Pageable pageable, UserModel userModel);
    Optional<GroupBillModel> findByIdAndUser(UUID id, UserModel userModel);
}
