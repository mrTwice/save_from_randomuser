package ru.otus.basic.yampolskiy.saver.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@AllArgsConstructor
@Data
@Component
public class RandomUserApiClient {

    @Autowired
    private RestClient restClient;

    public ResponseEntity<Resource> getCsv(int userLimit) {
        return restClient
                .get()
                .uri("?format=csv&results=" + userLimit)
                .retrieve()
                .toEntity(Resource.class);
    }
}
