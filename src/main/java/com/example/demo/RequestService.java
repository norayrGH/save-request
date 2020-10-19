package com.example.demo;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RequestService {

  private final RequestInfoRepository requestInfoRepository;


  public Mono<RequestInfoResponse> saveRequestHeaders(Map<String, String> headers){
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setHeaders(convertMapToString(headers));
    RequestInfo saved = requestInfoRepository.save(requestInfo);
    return Mono.just(RequestInfoResponse.builder()
        .headers(saved.getHeaders()).build());
  }


  public String convertMapToString(Map<String, String> map) {
    String mapAsString = map.keySet().stream()
        .map(key -> key + "=" + map.get(key))
        .collect(Collectors.joining(", ", "{", "}"));
    return mapAsString;
  }


}
