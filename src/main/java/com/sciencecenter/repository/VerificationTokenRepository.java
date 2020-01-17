package com.sciencecenter.repository;

import com.sciencecenter.model.User;
import com.sciencecenter.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByVerificationToken(String token);

    VerificationToken findByUser(User user);

}
