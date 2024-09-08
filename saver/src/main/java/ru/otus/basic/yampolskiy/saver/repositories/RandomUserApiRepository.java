package ru.otus.basic.yampolskiy.saver.repositories;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import ru.otus.basic.yampolskiy.saver.entities.randomuser.Root;

/**
 * Репозиторий для взаимодействия с внешним API RandomUser.
 *
 * Этот класс предоставляет методы для выполнения запросов к API RandomUser и получения данных в формате CSV.
 */
@Repository
@Data
public class RandomUserApiRepository {

    /**
     * Клиент для выполнения HTTP-запросов к внешнему API.
     * Этот компонент автоматически внедряется Spring'ом.
     */
    @Autowired
    private RestClient restClient;

    /**
     * Количество пользователей, запрашиваемых у API RandomUser.
     * Значение задается из конфигурации приложения.
     */
    @Value("${person.count}")
    private int userLimit;

    /**
     * Выполняет запрос к API RandomUser и возвращает данные в формате CSV.
     *
     * @return объект {@link ResponseEntity} с ресурсом {@link Resource}, содержащим данные в формате CSV.
     */
    public ResponseEntity<Resource> getCsvData() {
        return restClient
                .get()
                .uri("?format=csv&results=" + userLimit)
                .retrieve()
                .toEntity(Resource.class);
    }

    /**
     * Выполняет запрос для получения одного случайного пользователя.
     *
     * Этот метод использует {@link RestClient} для выполнения HTTP GET-запроса
     * и возвращает данные в виде объекта {@link Root}.
     *
     * @return объект {@link ResponseEntity} с телом ответа, содержащим данные типа {@link Root}.
     */
    public ResponseEntity<Root> getSingleRandomUser() {
        return restClient
                .get()
                .retrieve()
                .toEntity(Root.class);
    }

    /**
     * Выполняет запрос для получения списка случайных пользователей.
     *
     * Этот метод использует {@link RestClient} для выполнения HTTP GET-запроса к API,
     * запрашивая количество пользователей, определенное переменной {@code userLimit}.
     * Возвращает данные в виде объекта {@link Root}, содержащего список пользователей.
     *
     * @return объект {@link ResponseEntity} с телом ответа, содержащим данные типа {@link Root}.
     */
    public ResponseEntity<Root> getRandomUsers() {
        return restClient
                .get()
                .uri("?results=" + userLimit)
                .retrieve()
                .toEntity(Root.class);
    }

}
