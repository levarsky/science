package com.sciencecenter.repository;

import com.sciencecenter.model.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineRepository extends JpaRepository<Magazine,Long> {

    boolean existsByISSN(String issn);
    boolean existsByISSNAndId (String issn,Long id);
    boolean existsByName(String name);

    Magazine findByISSN(String issn);
}
