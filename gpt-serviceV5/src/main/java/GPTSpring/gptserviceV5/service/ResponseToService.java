package GPTSpring.gptserviceV5.service;

import org.apache.tomcat.util.json.ParseException;

import java.util.Map;

public interface ResponseToService {
    String  responseData(Map<String, Object> response);
    Map<String, Object> stringToJson(String newResult);

}
