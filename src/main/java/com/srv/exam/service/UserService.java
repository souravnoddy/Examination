package com.srv.exam.service;

import com.srv.exam.aspect.MeasureTime;
import com.srv.exam.dto.UserAdditionRequest;
import com.srv.exam.entity.UserDetail;
import com.srv.exam.exception.BusinessException;
import com.srv.exam.repository.UserDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
  @Autowired UserDetailRepository userDetailRepository;

  @MeasureTime
  public UserDetail addUser(UserAdditionRequest user) {
    try {
      UserDetail userDetail =
          userDetailRepository.save(
              UserDetail.builder()
                  .email(user.getEmail())
                  .name(user.getName())
                  .phoneNumber(user.getPhoneNumber())
                  .build());
      log.info("User addition successful with ph no {}", user.getPhoneNumber());
      return userDetail;

    } catch (Exception ex) {
      throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while Adding User");
    }
  }

  @MeasureTime
  public UserDetail updateUser(long userId, UserAdditionRequest user) {
    UserDetail userDetail = userDetailRepository.getOne(userId);
    if (userDetail == null) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, "User Not Valid");
    }
    userDetail.setPhoneNumber(user.getPhoneNumber());
    userDetail.setEmail(user.getEmail());
    userDetail.setName(user.getName());

    return userDetailRepository.save(userDetail);
  }

  @MeasureTime
  public void deleteUser(long userId) {
    UserDetail userDetail = userDetailRepository.getOne(userId);
    if (userDetail == null) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, "User Not Valid");
    }

    userDetailRepository.delete(userDetail);
  }
}
