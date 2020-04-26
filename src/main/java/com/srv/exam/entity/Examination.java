package com.srv.exam.entity;

import com.srv.exam.constants.Subject;
import com.srv.exam.utils.JpaConverterJson;
import java.util.Date;
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
public class Examination {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private long id;

  private String examName;

  @Temporal(TemporalType.TIMESTAMP)
  private Date examStartTime;

  @Temporal(TemporalType.TIMESTAMP)
  private Date examEndTime;

  private int numberOfQuestions;

  @Enumerated(EnumType.STRING)
  private Subject subject;

  @Convert(converter = JpaConverterJson.class)
  @Lob
  private List<Question> questionList;
}
