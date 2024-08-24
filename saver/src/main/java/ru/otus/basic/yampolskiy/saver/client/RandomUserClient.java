package ru.otus.basic.yampolskiy.saver.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.*;

@AllArgsConstructor
@Data
@Component
public class RandomUserClient {

    @Autowired
    private RestClient restClient;

    public String request() throws JsonProcessingException {
        return restClient
                .get()
                .retrieve()
                .body(String.class);
    }

    public void getCVS() {
        ResponseEntity<Resource> response = restClient
                .get()
                .uri("?format=csv&results=10")
                .retrieve()
                .toEntity(Resource.class);
        Resource resource = response.getBody();
        try (InputStream inputStream = resource.getInputStream();
             FileOutputStream outputStream = new FileOutputStream("./1.csv")) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Файл успешно загружен и сохранен по пути: " + "./uploads");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
