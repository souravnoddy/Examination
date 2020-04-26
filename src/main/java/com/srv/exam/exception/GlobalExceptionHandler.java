package com.srv.exam.exception;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public final ResponseEntity<ExceptionResponse> handleException(Exception ex, WebRequest request) {

    log.error("Exception", ex);

    if (ex instanceof BusinessException) {
      if (((BusinessException) ex).isPrintStackTrace()) {
        return handleException(((BusinessException) ex).getErrorStatus(), ex, request);
      }
      return handleException(((BusinessException) ex).getErrorStatus(), ex.getMessage(), request);
    } else {
      log.error("P3|Error Code 500|{}", StackTraceUtil.getStackTrace(ex));
      return handleException(
          HttpStatus.INTERNAL_SERVER_ERROR, StackTraceUtil.getStackTrace(ex), request);
    }
  }

  protected ResponseEntity<ExceptionResponse> handleException(
      HttpStatus status, Throwable ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        ExceptionResponse.builder()
            .httpCodeMessage(status.getReasonPhrase())
            .details(request.getDescription(false))
            .message(ex.getMessage())
            .timestamp(new Date().toString())
            .traceId(request.getHeader("TraceId"))
            .build();

    log.error("Runtime Exception Response {}", exceptionResponse);
    return new ResponseEntity<>(exceptionResponse, status);
  }

  protected ResponseEntity<ExceptionResponse> handleException(
      HttpStatus status, String errorMessage, WebRequest request) {
    ExceptionResponse exceptionResponse =
        ExceptionResponse.builder()
            .httpCodeMessage(status.getReasonPhrase())
            .details(request.getDescription(false))
            .message(errorMessage)
            .timestamp(new Date().toString())
            .traceId(request.getHeader("TraceId"))
            .build();

    log.error("Runtime Exception Response {}", exceptionResponse);
    return new ResponseEntity<>(exceptionResponse, status);
  }
}
