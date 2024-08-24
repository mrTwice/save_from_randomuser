package ru.otus.basic.yampolskiy.saver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.basic.yampolskiy.saver.client.RandomUserClient;

@Service
@Data
@AllArgsConstructor
@Getter
public class RandomUserService {

    @Autowired
    private RandomUserClient userClient;

    public String requestData() throws JsonProcessingException {
        return  userClient.request();
    }

    public  void getCVS(){
        userClient.getCVS();
    }

}
