package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@Controller
public class IndexController {
  private final CardService cardService;

  public IndexController(CardService cardService) {
    this.cardService = cardService;
  }

  @RequestMapping("/")
  public Mono<String> index(@RequestParam(name = "uid") String uid, @RequestParam(name = "trans_id") String transId) {
    return cardService.isThereCardInfo(uid)
        .map(isThere -> {
          if (isThere) {
            return "redirect:https://pay.millikart.az/epayment/ClientHandler?trans_id="
                + transId;
          } else {
            return "index";
          }
        });
  }
}
