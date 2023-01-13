package guru.springframework.springrestclientexamples.services;

import guru.springframework.api.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final String api_url;
    private RestTemplate restTemplate;

    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String api_url) {
        this.restTemplate = restTemplate;
        this.api_url = api_url;
    }

    @Override
    public List<User> getUsers(Integer limit) {
        UriComponentsBuilder uriComponentsBuilder =  UriComponentsBuilder
                .fromUriString(api_url)
                .queryParam("_limit", limit);
        List<User> userData = restTemplate.getForObject(uriComponentsBuilder.toUriString(), List.class);;
        return userData;
    }
}
