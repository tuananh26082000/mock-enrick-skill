package com.enrickskill.repository;

import com.enrickskill.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer>, JpaSpecificationExecutor<Exam>, CrudRepository<Exam, Integer> {

    @Query(value = """
            select e from Exam e where e.owner_exam = :id
            """)
    Page<Exam> findAllByOwner_exam(Integer id, Pageable pageable);

    @Override
    Page<Exam> findAll(Pageable pageable);
}
