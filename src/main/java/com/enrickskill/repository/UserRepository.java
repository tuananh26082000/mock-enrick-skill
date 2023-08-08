package com.enrickskill.repository;

import java.util.Optional;

import com.enrickskill.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

  Optional<User> findByEmail(String email);

  @Override
  @NotNull
  Page<User> findAll(Pageable pageable);
}
