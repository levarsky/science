package com.sciencecenter.repository;

import com.sciencecenter.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field,Long> {

    Optional<Field> findById(Long id);
    Optional<Field> findByName (String name);
    boolean existsByName (String name);

}
