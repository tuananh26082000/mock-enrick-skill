package com.enrickskill.service.exam;

import com.enrickskill.entity.Exam;
import com.enrickskill.mapper.ExamMapper;
import com.enrickskill.repository.ExamRepository;
import com.enrickskill.request.exam.CreateExamRequest;
import com.enrickskill.response.ExamResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final ExamMapper examMapper;

    @Override
    public ExamResponse save(CreateExamRequest request) {
        Exam exam = examMapper.to(request);
        return examMapper.to(examRepository.saveAndFlush(exam));
    }

    @Override
    public ExamResponse findById(Integer id) {
        return examRepository.findExamUser(id);
    }

    @Override
    public ExamResponse update(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Page<ExamResponse> findAll(Pageable pageable) {
        Page<Exam> exams = examRepository.findAll(pageable);
        return exams.map(examMapper::to);
    }
}
