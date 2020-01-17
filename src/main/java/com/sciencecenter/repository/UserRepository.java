package com.sciencecenter.repository;

import com.sciencecenter.model.Field;
import com.sciencecenter.model.Magazine;
import com.sciencecenter.model.Role;
import com.sciencecenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    List<User> findByRolesAndFieldsAndUsernameIsNot(Role role, Field field,String username);

    List<User> findByRolesAndMagazines(Role role, Magazine magazine);


}
