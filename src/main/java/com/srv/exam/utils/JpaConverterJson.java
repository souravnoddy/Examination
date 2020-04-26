package com.srv.exam.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JpaConverterJson implements AttributeConverter<Object, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(Object meta) {
    try {
      return objectMapper.writeValueAsString(meta);
    } catch (JsonProcessingException ex) {
      return null;
    }
  }

  @Override
  public Object convertToEntityAttribute(String dbData) {
    try {
      return objectMapper.readValue(dbData, Object.class);
    } catch (IOException ex) {
      return null;
    }
  }
}
