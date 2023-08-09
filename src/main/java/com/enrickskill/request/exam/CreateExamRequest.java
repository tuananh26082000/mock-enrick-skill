package com.enrickskill.request.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateExamRequest {
    private String name_exam;
    private Integer result_exam;
    private Integer owner_exam;
}
