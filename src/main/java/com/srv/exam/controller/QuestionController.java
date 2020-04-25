package com.srv.exam.controller;

import com.srv.exam.entity.Question;
import com.srv.exam.service.QuestionService;
import com.srv.exam.validation.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("v1/examination/question")
@Slf4j
public class QuestionController {

  @Autowired private QuestionService questionService;
  @Autowired private ValidationUtils validationUtils;

  @PostMapping
  public ResponseEntity<?> createQuestion(
      @RequestBody Question question,
      @RequestHeader(value = "TraceId", required = false) String traceId) {
    log.info("Question add request - {}", question);

    return ResponseEntity.ok(questionService.addQuestion(question));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<?> disableQuestion(
      @PathVariable long id,
      @RequestParam(value = "enableFlagValue",required = false,defaultValue = "false") boolean enableFlagvalue,
      @RequestHeader(value = "TraceId", required = false) String traceId) {
    log.info("Question disable request - id - {}", id);
    questionService.disableQuestion(id,enableFlagvalue);
    return ResponseEntity.ok().build();
  }
}
