package com.enrickskill.mapper;

import com.enrickskill.entity.Exam;
import com.enrickskill.request.exam.CreateExamRequest;
import com.enrickskill.response.ExamResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper implements Mapper<Exam>{
    public Exam to(CreateExamRequest request) {
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
