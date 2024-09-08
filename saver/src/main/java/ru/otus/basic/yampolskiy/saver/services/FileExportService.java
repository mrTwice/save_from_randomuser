package ru.otus.basic.yampolskiy.saver.services;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.basic.yampolskiy.saver.entities.randomuser.Root;
import ru.otus.basic.yampolskiy.saver.repositories.FileRepository;

/**
 * Сервис для экспорта данных в файл.
 *
 * Этот сервис отвечает за сохранение данных в файл в различных форматах,
 * поддерживая сохранение в формате CSV и выводя предупреждения для форматов XLSX и XLS.
 */
@Service
@Data
public class FileExportService {

    /**
     * Репозиторий для работы с файлами. Используется для сохранения данных в файлы.
     * Этот компонент автоматически внедряется Spring'ом.
     */
    @Autowired
    private FileRepository fileRepository;

    /**
     * Логгер для записи сообщений о событиях, происходящих в процессе работы сервиса.
     */
    private Logger logger = LogManager.getLogger(this);


    /**
     * Сохраняет данные в файл формата CSV.
     *
     * Этот метод принимает ресурс типа {@link Resource} и сохраняет его в файл формата CSV
     * с использованием репозитория {@link FileRepository}.
     *
     * @param resource ресурс, содержащий данные, которые нужно сохранить в файл.
     */
    public void saveDataToFile(Resource resource) {
        fileRepository.saveToCSV(resource);
    }

    /**
     * Сохраняет данные в файл формата XLSX.
     *
     * Этот метод принимает объект типа {@link Root} и сохраняет его в файл формата XLSX
     * с использованием репозитория {@link FileRepository}.
     *
     * @param root объект типа {@link Root}, содержащий данные, которые нужно сохранить в файл.
     */
    public void saveDataToFile(Root root) {
        fileRepository.saveToXLSX(root);
    }
}
