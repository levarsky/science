package com.sciencecenter.service;

import com.sciencecenter.model.PaymentType;
import com.sciencecenter.repository.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    public List<PaymentType> getAllTypes(){
        return paymentTypeRepository.findAll();
    }

}
