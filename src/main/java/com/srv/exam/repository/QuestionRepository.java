package com.srv.exam.repository;

import com.srv.exam.constants.Subject;
import com.srv.exam.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
  Slice<Question> findBySubjectAndEnabled(Subject subject, boolean b, Pageable pageable);
}
