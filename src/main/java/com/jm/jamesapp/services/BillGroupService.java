package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.SaveGroupBillDto;
import com.jm.jamesapp.models.dto.UpdateGroupBillDto;
import com.jm.jamesapp.repositories.BillGroupRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.interfaces.IGroupBillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BillGroupService implements IGroupBillService {

    private final BillGroupRepository groupBillRepository;

    public BillGroupService(BillGroupRepository groupBillRepository) {
        this.groupBillRepository = groupBillRepository;
    }

    @Override
    public BillGroupModel save(SaveGroupBillDto saveGroupBillDto, UserModel userModel) {
        BillGroupModel groupBill = new BillGroupModel();
        groupBill.setUser(userModel);
        groupBill.setName(saveGroupBillDto.getName());
        groupBill.setBillingFrequency(saveGroupBillDto.getBillingFrequency());
        groupBill.setDescription(saveGroupBillDto.getDescription());
        groupBill.setValue(saveGroupBillDto.getTotalPayment());
        return groupBillRepository.save(groupBill);
    }

    @Override
    public BillGroupModel update(BillGroupModel groupBill, UpdateGroupBillDto updateGroupBillDto, UserModel userModel) {
        validateUpdate(updateGroupBillDto, userModel);

        groupBillRepository.findByIdAndUser(updateGroupBillDto.getId(), userModel);
        groupBill.setUser(userModel);
        groupBill.setName(updateGroupBillDto.getName());
        groupBill.setBillingFrequency(updateGroupBillDto.getBillingFrequency());
        groupBill.setDescription(updateGroupBillDto.getDescription());
        groupBill.setValue(updateGroupBillDto.getTotalPayment());

        return groupBillRepository.save(groupBill);
    }

    @Override
    public Page<BillGroupModel> findAll(Pageable pageable) {
        return groupBillRepository.findAll(pageable);
    }

    @Override
    public BillGroupModel findById(UUID id) {
        return groupBillRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(BillGroupModel objModel) {
        groupBillRepository.delete(objModel);
    }


    @Override
    public Page<BillGroupModel> findAllByUser(Pageable pageable, UserModel userModel) {
        return groupBillRepository.findAllByUser(pageable, userModel);
    }

    public BillGroupModel findByIdAndUser(UUID id, UserModel userModel){
        return groupBillRepository.findByIdAndUser(id, userModel).orElse(null);
    }

    @Override
    public void addCustomer(BillGroupModel groupBill, CustomerModel customer) {
        groupBill.getCustomers().add(customer);
        groupBillRepository.save(groupBill);
    }

    private void validateUpdate(UpdateGroupBillDto groupBillDto, UserModel userModel) {
        boolean isCustomerAlreadyRegistered = findByIdAndUser(groupBillDto.getId(), userModel) != null;
        if (isCustomerAlreadyRegistered) throw new BusinessException("Você possui esse Grupo com o ID informado.");
    }
}
