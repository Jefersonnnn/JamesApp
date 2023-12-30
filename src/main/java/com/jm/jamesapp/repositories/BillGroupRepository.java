package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.user.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BillGroupRepository extends JpaRepository<BillGroupModel, UUID> {
    Page<BillGroupModel> findAllByUser(Pageable pageable, UserModel userModel);
    Optional<BillGroupModel> findByIdAndUser(UUID id, UserModel userModel);
}
