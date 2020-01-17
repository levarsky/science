package com.sciencecenter.repository;

import com.sciencecenter.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {

}
