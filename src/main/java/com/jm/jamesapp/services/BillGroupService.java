package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.SaveBillGroupDto;
import com.jm.jamesapp.models.dto.UpdateBillGroupDto;
import com.jm.jamesapp.repositories.BillGroupRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.interfaces.IGroupBillClosureService;
import com.jm.jamesapp.services.interfaces.IGroupBillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BillGroupService implements IGroupBillService {

    private final BillGroupRepository groupBillRepository;
    private final IGroupBillClosureService groupBillClosureService;

    public BillGroupService(BillGroupRepository groupBillRepository, IGroupBillClosureService groupBillClosureService) {
        this.groupBillRepository = groupBillRepository;
        this.groupBillClosureService = groupBillClosureService;
    }

    @Override
    public BillGroupModel save(SaveBillGroupDto saveBillGroupDto, UserModel userModel) {
        BillGroupModel groupBill = new BillGroupModel();
        groupBill.setUser(userModel);
        groupBill.setName(saveBillGroupDto.getName());
        groupBill.setBillingFrequency(saveBillGroupDto.getBillingFrequency());
        groupBill.setDescription(saveBillGroupDto.getDescription());
        groupBill.setValue(saveBillGroupDto.getTotalPayment());
        return groupBillRepository.save(groupBill);
    }

    @Override
    public BillGroupModel update(BillGroupModel groupBill, UpdateBillGroupDto updateBillGroupDto, UserModel userModel) {
        validateUpdate(updateBillGroupDto, userModel);

        groupBillRepository.findByIdAndUser(updateBillGroupDto.getId(), userModel);
        groupBill.setUser(userModel);
        groupBill.setName(updateBillGroupDto.getName());
        groupBill.setBillingFrequency(updateBillGroupDto.getBillingFrequency());
        groupBill.setDescription(updateBillGroupDto.getDescription());
        groupBill.setValue(updateBillGroupDto.getTotalPayment());

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
    public BillGroupModel findLastByUser(UserModel user) {
        return groupBillRepository.findTop1ByUser(user).orElse(null);
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

    public void closeAndSave(BillGroupModel billGroup){
        groupBillClosureService.closeAndSave(billGroup);
    }

    private void validateUpdate(UpdateBillGroupDto groupBillDto, UserModel userModel) {
        boolean isCustomerAlreadyRegistered = findByIdAndUser(groupBillDto.getId(), userModel) != null;
        if (isCustomerAlreadyRegistered) throw new BusinessException("Você possui esse Grupo com o ID informado.");
    }
}
