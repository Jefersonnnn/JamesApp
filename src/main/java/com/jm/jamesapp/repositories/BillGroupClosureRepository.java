package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.billgroup.BillGroupClosureModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillGroupClosureRepository extends JpaRepository<BillGroupClosureModel, UUID> {

}
