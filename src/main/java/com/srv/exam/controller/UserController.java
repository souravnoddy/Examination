package com.srv.exam.controller;

import com.srv.exam.dto.UserAdditionRequest;
import com.srv.exam.exception.BusinessException;
import com.srv.exam.service.UserService;
import com.srv.exam.validation.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("v1/examination/user")
@Slf4j
public class UserController {

  @Autowired private UserService userservice;
  @Autowired private ValidationUtils validationUtils;

  @PostMapping
  public ResponseEntity<?> createUser(
      @RequestBody UserAdditionRequest user,
      @RequestHeader(value = "TraceId", required = false) String traceId) {
    log.info("User add request - {}", user);
    String validationErrorMessage = validationUtils.getValidationErrorMessage(user);

    if (StringUtils.hasText(validationErrorMessage)) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, validationErrorMessage);
    }
    return ResponseEntity.ok(userservice.addUser(user));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<?> updateUser(
      @RequestBody UserAdditionRequest user,
      @PathVariable long id,
      @RequestHeader(value = "TraceId", required = false) String traceId) {
    log.info("User Update request id- {} - {}", id, user);
    String validationErrorMessage = validationUtils.getValidationErrorMessage(user);

    if (StringUtils.hasText(validationErrorMessage)) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, validationErrorMessage);
    }
    return ResponseEntity.ok(userservice.updateUser(id, user));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteUser(
      @PathVariable("id") long id,
      @RequestHeader(value = "TraceId", required = false) String traceId) {
    log.info("User add request - id - {}", id);
    userservice.deleteUser(id);
    return ResponseEntity.ok().build();
  }
}
