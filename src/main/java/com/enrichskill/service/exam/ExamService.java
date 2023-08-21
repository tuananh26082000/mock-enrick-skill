package com.enrichskill.service.exam;

import com.enrichskill.request.exam.CreateExamRequest;
import com.enrichskill.request.exam.UpdateExamRequest;
import com.enrichskill.response.ExamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface ExamService {
    ExamResponse save (CreateExamRequest request);
    ExamResponse findById(Integer id);

    ExamResponse update(UpdateExamRequest request);

    void delete(Integer id);

    Page<ExamResponse> findAll(Pageable pageable);
    Page<ExamResponse> findAllByIdUser(String email, Pageable pageable);

    void insertExamsByFile(MultipartFile file);
    ByteArrayInputStream exportCSVByID(Integer id);
    ByteArrayInputStream exportCSVByEmail(String email);
}
