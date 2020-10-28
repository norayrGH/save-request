package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

@Service
public class CardService {
  private final CardInfoRepository cardInfoRepository;

  public CardService(CardInfoRepository cardInfoRepository) {
    this.cardInfoRepository = cardInfoRepository;
  }

  public Mono<CardInfo> saveCardInfo(
      MultiValueMap<String, String> data){
    CardInfo cardInfo = new CardInfo();
    cardInfo.setCardName(data.getFirst("cardname"));
    cardInfo.setCardNumber(data.getFirst("cardnr"));
    cardInfo.setValidMonth(data.getFirst("validMONTH"));
    cardInfo.setValidYear(data.getFirst("validYEAR"));
    cardInfo.setCvc2(data.getFirst("cvc2"));
    cardInfo.setUid(data.getFirst("uid"));

    CardInfo saved = cardInfoRepository.save(cardInfo);
    return Mono.just(saved);
  }

  public Mono<Boolean> isThereCardInfo(String uid) {
    return Mono.just(cardInfoRepository.getCardInfoByUid(uid) != null);
  }
}
