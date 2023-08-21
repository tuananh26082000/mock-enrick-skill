package com.enrichskill.mapper;

import com.enrichskill.entity.Exam;
import com.enrichskill.request.exam.CreateExamRequest;
import com.enrichskill.request.exam.UpdateExamRequest;
import com.enrichskill.response.ExamResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper implements Mapper<Exam>{
    public Exam to(CreateExamRequest request) {
        Exam exam = new Exam();
        BeanUtils.copyProperties(request, exam);
        return exam;
    }

    public Exam to(UpdateExamRequest request) {
        Exam exam = new Exam();
        BeanUtils.copyProperties(request, exam);
        return exam;
    }

    public ExamResponse to(Exam exam) {
        ExamResponse examResponse = new ExamResponse();
        BeanUtils.copyProperties(exam, examResponse);
        return examResponse;
    }
}
