package com.sciencecenter.repository;

import com.sciencecenter.model.Magazine;
import com.sciencecenter.model.User;
import com.sciencecenter.model.UserMagazine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMagazineRepository extends JpaRepository<UserMagazine,Long> {

    List<UserMagazine> findByMagazineAndRole(Magazine magazine, String role);

    Optional<UserMagazine> findByUserIdAndMagazineId(Long userId, Long magazineId);

    void deleteByMagazineIdAndRole (Long id,String role);

    Optional<UserMagazine> findByMagazineIdAndRole(Long magazineId,String role);

    boolean existsByMagazineIdAndUserIdAndRole(Long magazineId,Long userId,String role);
}
