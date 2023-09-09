package rest.client;

import models.Match;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.Callable;


public class MatchClient {
//    public static final String URL = "http://localhost:8080/chat/users";
    public static final String URL = "http://localhost:8080/matches";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) throws Exception {
        try {
            return callable.call();
        } catch (Exception e) { // server down, resource exception
            throw new Exception(e);
        }
    }

    public Match[] getAll() throws Exception {
        return execute(() -> restTemplate.getForObject(URL, Match[].class));
    }

    public Match getById(String id) throws Exception {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Match.class));
    }

    public Match create(Match user) throws Exception {
        return execute(() -> restTemplate.postForObject(URL, user, Match.class));
    }

    public void update(Match user) throws Exception {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, user.getId()), user);
            return null;
        });
    }

    public void delete(String id) throws Exception {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }

}
