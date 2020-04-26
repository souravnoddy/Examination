package com.srv.exam.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.srv.exam.constants.QuestionType;
import com.srv.exam.constants.Subject;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private long id;

  @Column(columnDefinition = "TEXT")
  private String questionDescription;

  @Enumerated(EnumType.STRING)
  private QuestionType questionType;

  @ElementCollection(targetClass = String.class)
  private List<String> choices;

  @ElementCollection(targetClass = String.class)
  private List<String> correctAnswer;

  @Enumerated(EnumType.STRING)
  private Subject subject;

  private String createdOn;

  private String createdBy;

  private int day;

  private boolean enabled;
}
