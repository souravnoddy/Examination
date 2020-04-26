package com.srv.exam.service;

import com.srv.exam.constants.QuestionType;
import com.srv.exam.entity.Question;
import com.srv.exam.exception.BusinessException;
import com.srv.exam.repository.QuestionRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuestionService {

  @Autowired private QuestionRepository questionRepository;

  public Question addQuestion(Question question) {
    validateQuestion(question);

    question.setCreatedBy("System");
    question.setCreatedOn(new Date().toString());
    question.setDay(getDayFormat());
    question.setEnabled(true);
    return questionRepository.save(question);
  }

  private void validateQuestion(Question question) {
    if (CollectionUtils.isEmpty(question.getCorrectAnswer())) {
      throw new BusinessException(HttpStatus.FORBIDDEN, "Answers cant be empty");
    }
    if (CollectionUtils.isEmpty(question.getChoices())) {
      throw new BusinessException(HttpStatus.FORBIDDEN, "Choices cant be empty");
    }
    if (question.getQuestionType().equals(QuestionType.SINGLECHOICE)
        && question.getCorrectAnswer().size() > 1) {
      throw new BusinessException(
          HttpStatus.FORBIDDEN, "Single Choice questions cant have multiple answers");
    }
  }

  private int getDayFormat() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    return Integer.parseInt(dateFormat.format(new Date()));
  }

  public void disableQuestion(long questionId, boolean enableFlagvalue) {
    questionRepository
        .findById(questionId)
        .ifPresent(
            question -> {
              question.setEnabled(enableFlagvalue);
              questionRepository.save(question);
            });
  }
}
