package com.enrickskill.controller;

import com.enrickskill.base.BaseResponse;
import com.enrickskill.repository.ExamRepository;
import com.enrickskill.response.ExamResponse;
import com.enrickskill.service.exam.ExamServiceImpl;
import com.enrickskill.service.user.UserServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/exam")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ExamController {

  @Autowired
  private ExamRepository examRepository;
  @Autowired
  private ExamServiceImpl examService;

  @GetMapping("/all")
  @PreAuthorize("hasAnyAuthority('admin:read')")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                  value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
          @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                  value = "Number of records per page.", defaultValue = "15"),
          @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string",
                  paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                  + "Default sort order is ascending. Multiple sort criteria are supported.")})
  public BaseResponse<?> getListExam(@ApiIgnore Pageable pageable) {
    return BaseResponse.ofSuccess(examService.findAll(pageable));
  }

  @PostMapping
  @PreAuthorize("hasAnyAuthority('admin:read')")
  public ResponseEntity<String> sayHello2() {
    return ResponseEntity.ok("Hello from secured endpoint");
  }

}
