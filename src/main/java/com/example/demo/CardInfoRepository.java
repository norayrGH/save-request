package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {
  CardInfo getCardInfoByUid(String uid);
}
