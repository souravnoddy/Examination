package com.srv.exam.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {
  private HttpStatus errorStatus;
  private String errorMessage;
  private boolean printStackTrace;

  public BusinessException(HttpStatus errorStatus, String errorMessage) {
    super(errorMessage);
    this.errorMessage = errorMessage;
    this.errorStatus = errorStatus;
    this.printStackTrace = true;
  }
}
