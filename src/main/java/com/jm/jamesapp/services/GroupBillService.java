package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.GroupBillModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveGroupBillDto;
import com.jm.jamesapp.models.dto.UpdateGroupBillDto;
import com.jm.jamesapp.repositories.GroupBillRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.IGroupBillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupBillService implements IGroupBillService {

    private final GroupBillRepository groupBillRepository;

    public GroupBillService(GroupBillRepository groupBillRepository) {
        this.groupBillRepository = groupBillRepository;
    }

    @Override
    public GroupBillModel save(SaveGroupBillDto saveGroupBillDto, UserModel userModel) {
        GroupBillModel groupBill = new GroupBillModel();
        groupBill.setUser(userModel);
        groupBill.setName(saveGroupBillDto.getName());
        groupBill.setBillingFrequency(saveGroupBillDto.getBillingFrequency());
        groupBill.setDescription(saveGroupBillDto.getDescription());
        groupBill.setTotalPayment(saveGroupBillDto.getTotalPayment());
        return groupBillRepository.save(groupBill);
    }

    @Override
    public GroupBillModel update(GroupBillModel groupBill, UpdateGroupBillDto updateGroupBillDto, UserModel userModel) {
        validateUpdate(updateGroupBillDto, userModel);

        groupBillRepository.findByIdAndUser(updateGroupBillDto.getId(), userModel);
        groupBill.setUser(userModel);
        groupBill.setName(updateGroupBillDto.getName());
        groupBill.setBillingFrequency(updateGroupBillDto.getBillingFrequency());
        groupBill.setDescription(updateGroupBillDto.getDescription());
        groupBill.setTotalPayment(updateGroupBillDto.getTotalPayment());

        return groupBillRepository.save(groupBill);
    }

    @Override
    public Page<GroupBillModel> findAll(Pageable pageable) {
        return groupBillRepository.findAll(pageable);
    }

    @Override
    public GroupBillModel findById(UUID id) {
        return groupBillRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(GroupBillModel objModel) {
        groupBillRepository.delete(objModel);
    }


    @Override
    public Page<GroupBillModel> findAllByUser(Pageable pageable, UserModel userModel) {
        return groupBillRepository.findAllByUser(pageable, userModel);
    }

    public GroupBillModel findByIdAndUser(UUID id, UserModel userModel){
        return groupBillRepository.findByIdAndUser(id, userModel).orElse(null);
    }

    @Override
    public void addCustomer(GroupBillModel groupBill, CustomerModel customer) {
        groupBill.getCustomers().add(customer);
        groupBillRepository.save(groupBill);
    }

    private void validateUpdate(UpdateGroupBillDto groupBillDto, UserModel userModel) {
        boolean isCustomerAlreadyRegistered = findByIdAndUser(groupBillDto.getId(), userModel) != null;
        if (isCustomerAlreadyRegistered) throw new BusinessException("Você possui esse Grupo com o ID informado.");
    }
}
