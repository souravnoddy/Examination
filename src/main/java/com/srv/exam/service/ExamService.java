package com.srv.exam.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srv.exam.constants.Role;
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

  public Examination createExamination(ExamCreateRequest examCreateRequest) {
    Examination examination = new ObjectMapper().convertValue(examCreateRequest, Examination.class);
    List<Question> questionList = getQuestionList(examCreateRequest);
    examination.setQuestionList(questionList);

    return examRepository.save(examination);
  }

  private List<Question> getQuestionList(ExamCreateRequest examCreateRequest) {
    PageRequest pageRequest = PageRequest.of(0, examCreateRequest.getNumberOfQuestions());
    return questionRepository
        .findBySubjectAndEnabled(examCreateRequest.getSubject(), true, pageRequest)
        .getContent();
  }

  public Examination updateExamination(long id, ExamCreateRequest examCreateRequest) {
    Examination examination = examRepository.findById(id).get();
    examination.setExamName(examCreateRequest.getExamName());
    examination.setExamEndTime(examCreateRequest.getExamEndTime());
    examination.setExamStartTime(examCreateRequest.getExamStartTime());
    examination.setNumberOfQuestions(examCreateRequest.getNumberOfQuestions());
    examination.setSubject(examCreateRequest.getSubject());
    examination.setQuestionList(getQuestionList(examCreateRequest));
    return examRepository.save(examination);
  }

  public void deleteExamination(long id) {
    examRepository.deleteById(id);
  }

  public Examination getExamDetails(long id, Role role) {
    Examination examination = examRepository.findById(id).get();
    if (Role.STUDENT.equals(role)) {
      for (Question question : examination.getQuestionList()) {
        question.setCorrectAnswer(null);
      }
    }
    return examination;
  }
}
