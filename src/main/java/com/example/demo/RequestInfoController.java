package com.example.demo;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class RequestInfoController {

  private final RequestService service;

  @GetMapping("/saveHeaders")
  public Mono<RequestInfoResponse> saveAllHeaders(@RequestHeader Map<String, String> headers) {
//    String ip = headers.get("X-Forwarded-For");
//    if(invalid.contains(ip)){
//      headers.remove("X-Forwarded-For");
//    }
    return service.saveRequestHeaders(headers);
  }
}
