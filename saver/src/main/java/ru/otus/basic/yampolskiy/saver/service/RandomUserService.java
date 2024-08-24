package ru.otus.basic.yampolskiy.saver.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.basic.yampolskiy.saver.client.RandomUserApiClient;

@Service
@Data
@AllArgsConstructor
@Getter
public class RandomUserService {

    @Autowired
    private RandomUserApiClient userClient;

}
