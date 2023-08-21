package com.enrichskill.service.exam;

import com.enrichskill.base.BusinessCode;
import com.enrichskill.base.BusinessException;
import com.enrichskill.base.CSVUtil;
import com.enrichskill.entity.Exam;
import com.enrichskill.entity.User;
import com.enrichskill.mapper.ExamMapper;
import com.enrichskill.mapper.MappingCSV;
import com.enrichskill.repository.ExamRepository;
import com.enrichskill.repository.UserRepository;
import com.enrichskill.request.exam.CreateExamRequest;
import com.enrichskill.request.exam.UpdateExamRequest;
import com.enrichskill.response.ExamResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final ExamMapper examMapper;
    private final MappingCSV mappingUtil;

    @Override
    public ExamResponse save(CreateExamRequest request) {
        Exam exam = examMapper.to(request);
        return examMapper.to(examRepository.saveAndFlush(exam));
    }

    @Override
    public ExamResponse findById(Integer id) {
        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new BusinessException(BusinessCode.NOT_FOUND_USER)
        );
        return examMapper.to(exam);
    }

    @Override
    public ExamResponse update(UpdateExamRequest request) {
        Exam exam = examMapper.to(request);
        return examMapper.to(examRepository.saveAndFlush(exam));
    }

    @Override
    public void delete(Integer id) {
        examRepository.deleteById(id);
    }

    @Override
    public Page<ExamResponse> findAll(Pageable pageable) {
        Page<Exam> exams = examRepository.findAll(pageable);
        return exams.map(examMapper::to);
    }

    @Override
    public Page<ExamResponse> findAllByIdUser(String email, Pageable pageable) {
        Optional<User> user = userRepository.findByEmail(email);
        Page<Exam> exams = examRepository.findAllByOwner_exam(user.orElseThrow().getId(), pageable);
        return exams.map(examMapper::to);
    }

    @Override
    public void insertExamsByFile(MultipartFile file) {
        try {
            List<CreateExamRequest> examDtoList = CSVUtil.csvToList(file.getInputStream(), CreateExamRequest.class);
            List<Exam> exams = mappingUtil.mapList(examDtoList, Exam.class);
            examRepository.saveAll(exams);

        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

    }

    @Override
    public ByteArrayInputStream exportCSVByID(Integer id) {
        List<Exam> exams = examRepository.findAllById(id);
        return CSVUtil.exportCSV(exams);
    }

    @Override
    public ByteArrayInputStream exportCSVByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<Exam> exams = examRepository.findAllById(user.orElseThrow().getId());
        return CSVUtil.exportCSV(exams);
    }
}
