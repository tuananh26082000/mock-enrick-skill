package com.enrickskill.controller;

import com.enrickskill.base.BaseResponse;
import com.enrickskill.base.BusinessCode;
import com.enrickskill.base.BusinessException;
import com.enrickskill.base.CSVUtil;
import com.enrickskill.entity.Exam;
import com.enrickskill.entity.User;
import com.enrickskill.mapper.ExamMapper;
import com.enrickskill.repository.ExamRepository;
import com.enrickskill.repository.UserRepository;
import com.enrickskill.request.exam.CreateExamRequest;
import com.enrickskill.request.exam.UpdateExamRequest;
import com.enrickskill.response.ExamResponse;
import com.enrickskill.response.UserResponse;
import com.enrickskill.service.exam.ExamServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/exam")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequiredArgsConstructor
public class ExamController {
  private final ExamServiceImpl examService;

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

  @GetMapping(value = "/info")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                  value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
          @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                  value = "Number of records per page.", defaultValue = "15"),
          @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string",
                  paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                  + "Default sort order is ascending. Multiple sort criteria are supported.")})
  @PreAuthorize("hasAnyAuthority('admin:read','user:read')")
  public BaseResponse<?> getInfoExam(@ApiIgnore Pageable pageable, Authentication authentication) {
      return BaseResponse.ofSuccess(examService.findAllByIdUser(authentication.getName(), pageable));
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAnyAuthority('admin:read')")
  public BaseResponse<ExamResponse> findExamById(@PathVariable Integer id) {
    return BaseResponse.ofSuccess(examService.findById(id));
  }

  @PostMapping
  @PreAuthorize("hasAnyAuthority('admin:create')")
  public BaseResponse<ExamResponse> createExam(@RequestBody CreateExamRequest request){
    return BaseResponse.ofSuccess(examService.save(request));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('admin:update')")
  public ResponseEntity<ExamResponse> updateExam(@PathVariable Integer id,
                                               @RequestBody UpdateExamRequest request){
    Optional<ExamResponse> exam = Optional.ofNullable(examService.findById(id));
    return exam.map(user1 -> {
      request.setId(user1.getId());
      return new ResponseEntity<>(examService.update(request), HttpStatus.OK);
    }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('admin:delete')")
  public ResponseEntity<Void> deleteExam(@PathVariable Integer id){
    try {
      examService.delete(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/upload/csv")
  @PreAuthorize("hasAnyAuthority('admin:create')")
  public BaseResponse<?> uploadCSV(@RequestParam("file") MultipartFile file) {
    BusinessException businessException = new BusinessException(BusinessCode.INTERNAL_SERVER);
    if (CSVUtil.isCSVFormat(file)) {
      try {
        examService.insertExamsByFile(file);

        BaseResponse.ofSuccess(
                "Uploaded the file successfully: " + file.getOriginalFilename()
        );
      } catch (Exception e) {
        BaseResponse.ofSuccess(
                "Uploaded the file successfully: " + file.getOriginalFilename()
        );
      }
    }
    return BaseResponse.ofFailed(businessException.getErrorResponse());
  }

  @GetMapping("/download/csv")
  @PreAuthorize("hasAnyAuthority('admin:read')")
  public BaseResponse<Resource> getCSV() {
    InputStreamResource file = new InputStreamResource(examService.exportCSV());
    return BaseResponse.ofSuccess(file);
  }

}
