package com.srv.exam.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srv.exam.dto.ExamCreateRequest;
import com.srv.exam.entity.Examination;
import com.srv.exam.entity.Question;
import com.srv.exam.repository.ExamRepository;
import com.srv.exam.repository.QuestionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

  @Autowired ExamRepository examRepository;
  @Autowired QuestionRepository questionRepository;

  public Examination createExamination(ExamCreateRequest question) {
    Examination examination = new ObjectMapper().convertValue(question, Examination.class);
    PageRequest pageRequest = PageRequest.of(0, question.getNumberOfQuestions());
    List<Question> questionList =
        questionRepository
            .findBySubjectAndEnabled(examination.getSubject(), true, pageRequest)
            .getContent();
    examination.setQuestionList(questionList);

    return examRepository.save(examination);
  }

  public Examination updateExamination(long id, ExamCreateRequest enableFlagvalue) {
    return null;
  }

  public void deleteExamination(long id) {
    examRepository.deleteById(id);
  }
}
