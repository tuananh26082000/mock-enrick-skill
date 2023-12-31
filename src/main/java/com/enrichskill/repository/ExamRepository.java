package com.enrichskill.repository;

import com.enrichskill.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@SuppressWarnings("ALL")
@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer>, JpaSpecificationExecutor<Exam>, CrudRepository<Exam, Integer> {

    @Query(value = """
            select e from Exam e where e.owner_exam = :id
            """)
    Page<Exam> findAllByOwner_exam(Integer id, Pageable pageable);
    @Query(value = """
            select e from Exam e where e.owner_exam = :id
            """)
    List<Exam> findAllById(Integer id);

    @Override
    Page<Exam> findAll(Pageable pageable);
}
