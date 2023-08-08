package com.enrickskill.repository;

import java.util.Optional;

import com.enrickskill.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>, CrudRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  void deleteById(Integer id);

  @Override
  @NotNull
  Page<User> findAll(Pageable pageable);
}
