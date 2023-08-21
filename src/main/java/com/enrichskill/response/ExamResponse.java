package com.enrichskill.response;

import lombok.Data;

@Data
public class ExamResponse {
    private Integer id;
    private Integer owner_exam;
    private Integer result_exam;
    private String name_exam;
}
