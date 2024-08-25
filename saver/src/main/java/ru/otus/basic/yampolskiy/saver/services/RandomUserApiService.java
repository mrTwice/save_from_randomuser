package ru.otus.basic.yampolskiy.saver.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.basic.yampolskiy.saver.entities.randomuser.Root;
import ru.otus.basic.yampolskiy.saver.repositories.RandomUserApiRepository;

/**
 * Сервис для взаимодействия с внешним API RandomUser.
 *
 * Этот сервис предоставляет методы для получения данных в формате CSV
 * через репозиторий {@link RandomUserApiRepository}.
 */
@Service
@Data
public class RandomUserApiService {

    /**
     * Репозиторий для выполнения запросов к внешнему API RandomUser.
     * Этот компонент автоматически внедряется Spring'ом.
     */
    @Autowired
    private RandomUserApiRepository randomUserApiRepository;

    /**
     * Возвращает данные в формате CSV, полученные через API RandomUser.
     *
     * @return объект {@link ResponseEntity} с ресурсом {@link Resource}, содержащим данные CSV.
     */
    public ResponseEntity<Resource> getCSV() {
        return randomUserApiRepository.getCsvData();
    }

    /**
     * Возвращает данные одного случайного пользователя.
     *
     * Этот метод вызывает метод {@link RandomUserApiRepository#getSingleRandomUser()}
     * для выполнения запроса к API и получения данных пользователя.
     *
     * @return объект {@link ResponseEntity} с телом ответа, содержащим данные типа {@link Root}.
     */
    public ResponseEntity<Root> getSingleUser() {
        return randomUserApiRepository.getSingleRandomUser();
    }

    public ResponseEntity<Root> getUsers() {
        return randomUserApiRepository.getRandomUsers();
    }
}
