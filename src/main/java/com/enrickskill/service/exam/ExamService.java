package com.enrickskill.service.exam;

import com.enrickskill.request.exam.CreateExamRequest;
import com.enrickskill.request.exam.UpdateExamRequest;
import com.enrickskill.response.ExamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamService {
    ExamResponse save (CreateExamRequest request);
    ExamResponse findById(Integer id);

    ExamResponse update(UpdateExamRequest request);

    void delete(Integer id);

    Page<ExamResponse> findAll(Pageable pageable);
}