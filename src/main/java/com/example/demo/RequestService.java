package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RequestService {

  private final RequestInfoRepository requestInfoRepository;
  static String ipRange = "10.12.0.0/16,10.80.0.0/15,10.83.0.0/16,10.101.0.0-10.103.255.255,10.108.0.0/16,10.121.0.0/16,10.123.0.0/16,10.127.0.0/16,10.129.0.0/16,10.131.0.0/16,10.133.0.0/16,10.135.0.0-10.139.255.255,10.208.0.0/14,10.215.0.0/16,10.218.0.0/15,10.233.0.0/16,172.17.128.0-172.19.255.255,172.24.0.0/15,192.168.0.0/16";

  public Mono<RequestInfoResponse> saveRequestHeaders(Map<String, String> headers){
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setHeaders(convertMapToString(headers));
    String ip = headers.get("X-Forwarded-For");
    if(ip != null && isInRange(ip)){
      return Mono.empty();
    }
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

  private Boolean isInRange(String ipToTest) {
    String[] ranges = ipRange.split(",");

    for (String _range : ranges) {
      String range = _range.indexOf("/") > -1 ? _range.substring(0, _range.indexOf("/")) : _range; // cut off the /xx if it exists
      boolean test = test(ipToTest, range);
      if (test) {
        return true;
      }
    }

    return false;
  }

  private Boolean test(String ipToTest, String ipRange) {
    String[] ranges = ipRange.split("-"); // Do we have a range or just a single ip
    String loRange = ranges[0]; // grab the first
    String hiRange = ranges.length > 1 ? ranges[1] : null; // If there a range grab the top end, else nothing

    if (hiRange == null) { // if a single ip, just do a string compare
      return ipToTest.equals(loRange);
    } else { // if a range then make sure each part is between the min and max
      String[] partsToTest = ipToTest.split("\\."); // get each part of the ip
      String[] partsLoRange = loRange.split("\\."); // get each part of the ip
      String[] partsHiRange = hiRange.split("\\."); // get each part of the ip
      int test = 0; // this will be our litmus test. if it = 15 when we are done, then each part is good, 63 of ipv6
      for (int i = 0; i < 4; i++) { // change 4 to 6 for ipv6
        int value = Integer.parseInt(partsToTest[i]); //get the int
        int lo = Integer.parseInt(partsLoRange[i]); //get the int
        int hi = Integer.parseInt(partsHiRange[i]); //get the int
        if (value >= lo && value <= hi) { // if we have a good value, set the bit on 'test'
          test += Math.pow(2, i);
        }
      }
      if (test == 15) {
        return true;
      }
    }
    return false;
  }

}
