package com.enrickskill.service.exam;

import com.enrickskill.entity.Exam;
import com.enrickskill.request.exam.CreateExamRequest;
import com.enrickskill.request.user.CreateUserRequest;
import com.enrickskill.response.ExamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamService {
    ExamResponse save (CreateExamRequest request);
    ExamResponse findById(Integer id);

    ExamResponse update(Integer id);

    void delete(Integer id);

    Page<ExamResponse> findAll(Pageable pageable);
}
