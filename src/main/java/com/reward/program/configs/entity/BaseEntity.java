package com.reward.program.configs.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder
@Getter
@MappedSuperclass
public class BaseEntity {
  @Id
  @GeneratedValue
  protected Long id;
}
