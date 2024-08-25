package ru.otus.basic.yampolskiy.saver.controllers;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.basic.yampolskiy.saver.entities.FileFormat;
import ru.otus.basic.yampolskiy.saver.entities.randomuser.Root;
import ru.otus.basic.yampolskiy.saver.exceptions.EmptyDataException;
import ru.otus.basic.yampolskiy.saver.exceptions.UnsupportedFormatException;
import ru.otus.basic.yampolskiy.saver.services.FileExportService;
import ru.otus.basic.yampolskiy.saver.services.RandomUserApiService;

/**
 * Контроллер для получения данных от внешнего API и сохранения их в файл.
 *
 * Этот класс взаимодействует с сервисами {@link RandomUserApiService} и {@link FileExportService}
 * для получения данных и их сохранения в файле.
 */
@Component
@Data
public class SaveController {

    /**
     * Сервис для взаимодействия с внешним API RandomUser и получения данных в формате CSV.
     * Этот компонент автоматически внедряется Spring'ом.
     */
    @Autowired
    private RandomUserApiService randomUserApiService;

    /**
     * Сервис для сохранения данных в файл. Этот компонент автоматически внедряется Spring'ом.
     */
    @Autowired
    private FileExportService fileExportService;

    /**
     * Логгер для записи сообщений о событиях, происходящих в процессе работы контроллера.
     */
    private Logger logger = LogManager.getLogger(this);

    /**
     * Получает данные в формате CSV с использованием {@link RandomUserApiService}
     * и сохраняет их в файл с помощью {@link FileExportService}.
     *
     * @throws EmptyDataException если полученные данные пусты.
     */
    public void getDataAndSave(FileFormat fileFormat) {
        switch (fileFormat) {
            case CSV -> {
                Resource resource = getData(Resource.class);
                fileExportService.saveDataToFile(resource);
            }
            case XLSX -> {
                Root root = getData(Root.class);
                fileExportService.saveDataToFile(root);
            }
            case XLS -> logger.warn("Сохранение в формате .xls не поддерживается в текущей версии приложения");
            default -> throw new UnsupportedFormatException("Неподдерживаемый формат файла: " + fileFormat);
        }
    }

    /**
     * Получает данные в зависимости от указанного типа класса.
     *
     * Этот метод поддерживает получение данных в виде {@link Resource} или {@link Root}.
     * В зависимости от переданного типа класса, выполняется соответствующий запрос
     * к API через сервис {@link RandomUserApiService}.
     *
     * @param <T>   тип данных, который должен быть возвращен.
     * @param clazz класс, указывающий тип данных, который нужно получить (например, {@link Resource} или {@link Root}).
     * @return объект типа {@link T}, содержащий данные, полученные от API.
     * @throws EmptyDataException если данные, полученные от API, пусты.
     * @throws IllegalArgumentException если передан неподдерживаемый тип класса.
     */
    public <T> T getData(Class<T> clazz) throws EmptyDataException {
        ResponseEntity<T> response;

        if (clazz == Resource.class) {
            response = (ResponseEntity<T>) randomUserApiService.getCSV();
        } else if (clazz == Root.class) {
            response = (ResponseEntity<T>) randomUserApiService.getUsers();
        } else {
            throw new IllegalArgumentException("Unsupported class type: " + clazz.getName());
        }

        T body = response.getBody();
        if (body != null) {
            return body;
        } else {
            throw new EmptyDataException("Получены пустые данные");
        }
    }
}
