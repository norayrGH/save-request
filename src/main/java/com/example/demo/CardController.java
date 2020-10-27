package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
public class CardController {

  private final CardService cardService;

  public CardController(CardService cardService) {
    this.cardService = cardService;
  }

  @PostMapping(value = "/saveCard", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Mono<RedirectView> saveAllHeaders(ServerWebExchange exchange) {
    Mono<MultiValueMap<String, String>> data = exchange.getFormData();
    return data
        .map((d) -> {
          cardService.saveCardInfo(d).subscribe();
          return new RedirectView("https://pay.millikart.az/epayment/ClientHandler?trans_id="
              + d.getFirst("trans_id"));
        });
  }
}
