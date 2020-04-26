package com.srv.exam.dto;

import com.srv.exam.constants.Subject;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamCreateRequest {
  @NotEmpty(message = "Field Name can not be Empty")
  private String examName;

  @NotNull(message = "Field Name can not be Empty")
  private Date examStartTime;

  @NotNull(message = "Field Name can not be Empty")
  private Date examEndTime;

  private int numberOfQuestions;

  private Subject subject;
}
