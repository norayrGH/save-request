package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "card_info", indexes = @Index(columnList = "uid", unique = true))
public class CardInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "card_name")
  private String cardName;

  @Column(name = "card_number")
  private String cardNumber;

  @Column(name = "valid_month")
  private String validMonth;

  @Column(name = "valid_year")
  private String validYear;

  @Column(name = "cvc2")
  private String cvc2;

  @Column(name = "uid", unique = true)
  private String uid;
}
