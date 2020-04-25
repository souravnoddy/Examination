package com.srv.exam.entity;

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
public class UserDetail {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private long id;

  private String name;

  @Column(unique = true)
  private String phoneNumber;

  private String email;
}
