package com.srv.exam.entity;

import com.srv.exam.constants.Subject;
import java.util.Date;
import java.util.Map;
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
public class ResultSheet {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private long id;

  private String userId;

  private Subject subject;

  private String examDate;

  private Date loginTime;

  private Date logoutTime;

  @ElementCollection(targetClass = String.class)
  private Map<Long, String> questionIdAnswerMap;

  private Double percentageScored;
}
