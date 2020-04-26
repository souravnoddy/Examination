package com.srv.exam.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srv.exam.entity.Question;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JpaConverterJson implements AttributeConverter<List<Question>, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(List<Question> meta) {
    try {
      return objectMapper.writeValueAsString(meta);
    } catch (JsonProcessingException ex) {
      return null;
    }
  }

  @Override
  public List<Question> convertToEntityAttribute(String dbData) {
    try {
      return Arrays.asList(objectMapper.readValue(dbData, Question[].class));
    } catch (IOException ex) {
      return null;
    }
  }
}
