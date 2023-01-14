package guru.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class RestClientExamples {

    public static String API =  "https://api.predic8.de:443/shop/";

    @Test
    public void getCategories(){
        String apiUrl = API + "categories/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response: " + jsonNode.toString());
    }

    @Test
    public void getCustomers() {
        String apiUrl = API + "customers/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response:  " + jsonNode.toString());
    }

    @Test
    public void createCustomer() {
        String apiUrl = API + "customers/";
        RestTemplate restTemplate =  new RestTemplate();
        Map<String,Object> objectMap =  new HashMap<>();
        objectMap.put("firstname", "Kamil");
        objectMap.put("lastname","Nowak");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl,objectMap, JsonNode.class);
        System.out.println("Response: " + jsonNode.toString());
    }
    @Test
    public void updateCustomer(){
        String apiUrl = API + "customers/";
        RestTemplate restTemplate =  new RestTemplate();
        Map<String,Object> objectMap =  new HashMap<>();
        objectMap.put("firstname", "Łukasz");
        objectMap.put("lastname","Zając");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl,objectMap, JsonNode.class);
        System.out.println("Response: " + jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        objectMap.put("firstname","Łukaszupdate");
        objectMap.put("lastname","Zającupdate");

        restTemplate.put(apiUrl + id,objectMap);

        JsonNode updatedCustomer = restTemplate.getForObject(apiUrl + id, JsonNode.class);
        System.out.println("Response: " + updatedCustomer.toString());
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteCustomer(){
        String apiUrl = API + "customers/";
        RestTemplate restTemplate =  new RestTemplate();
        Map<String,Object> objectMap =  new HashMap<>();
        objectMap.put("firstname", "Ada");
        objectMap.put("lastname","Słowik");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl,objectMap, JsonNode.class);
        System.out.println("Response: " + jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        restTemplate.delete(apiUrl+id);

        JsonNode shouldGo404 = restTemplate.getForObject(apiUrl + id, JsonNode.class);
    }
}
