package com.jm.jamesapp.services;

import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.repositories.CustomerRepository;
import com.jm.jamesapp.repositories.GroupBillRepository;
import com.jm.jamesapp.services.interfaces.IGroupBillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GroupBillService implements IGroupBillService {

    final GroupBillRepository groupBillRepository;

    public GroupBillService(GroupBillRepository groupBillRepository) {
        this.groupBillRepository = groupBillRepository;
    }

    @Override
    public GroupBillModel save(GroupBillModel objModel) {
        return groupBillRepository.save(objModel);
    }

    @Override
    public Page<GroupBillModel> findAll(Pageable pageable) {
        return groupBillRepository.findAll(pageable);
    }

    @Override
    public Optional<GroupBillModel> findById(UUID id) {
        return groupBillRepository.findById(id);
    }

    @Override
    public void delete(GroupBillModel objModel) {
        groupBillRepository.delete(objModel);
    }


}
