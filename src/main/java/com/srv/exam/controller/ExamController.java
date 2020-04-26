package com.srv.exam.controller;

import com.srv.exam.dto.ExamCreateRequest;
import com.srv.exam.service.ExamService;
import com.srv.exam.validation.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("v1/examination")
@Slf4j
public class ExamController {

  @Autowired private ExamService examService;
  @Autowired private ValidationUtils validationUtils;

  @PostMapping
  public ResponseEntity<?> createExamnination(
      @RequestBody ExamCreateRequest examCreateRequest,
      @RequestHeader(value = "TraceId", required = false) String traceId) {
    log.info("Question add request - {}", examCreateRequest);

    return ResponseEntity.ok(examService.createExamination(examCreateRequest));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<?> updateExamination(
      @PathVariable long id,
      @RequestBody ExamCreateRequest examCreateRequest,
      @RequestHeader(value = "TraceId", required = false) String traceId) {
    log.info("Exam Updation request - id - {}", id);
    examService.updateExamination(id, examCreateRequest);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteExamination(
      @PathVariable long id, @RequestHeader(value = "TraceId", required = false) String traceId) {
    log.info("Exam Deletion request - id - {}", id);
    examService.deleteExamination(id);
    return ResponseEntity.ok().build();
  }
}
