package com.enrickskill.repository;

import java.util.Optional;

import com.enrickskill.entity.User;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@SuppressWarnings("ALL")
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>, CrudRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  void deleteById(Integer id);

  @Override
  @NotNull
  Page<User> findAll(Pageable pageable);
}
