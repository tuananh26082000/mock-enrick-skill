package com.enrickskill.repository;

import com.enrickskill.entity.Exam;
import com.enrickskill.response.ExamResponse;
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
      select e from Exam e inner join User u\s
      on e.owner_exam = u.id\s
      where u.id = :id \s
      """)
    ExamResponse findExamUser(Integer id);

    @Override
    Page<Exam> findAll(Pageable pageable);
}
