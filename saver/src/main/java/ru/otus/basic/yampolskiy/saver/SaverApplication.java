package ru.otus.basic.yampolskiy.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.basic.yampolskiy.saver.service.RandomUserService;

@SpringBootApplication
public class SaverApplication {

    private static RandomUserService userService;

    @Autowired
    public SaverApplication(RandomUserService userService) {
        SaverApplication.userService = userService;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SaverApplication.class, args);
        some();
    }

    public static void some() throws Exception {
        System.out.println(userService.requestData());
        userService.getCVS();
    }
}
