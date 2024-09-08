package ru.otus.basic.yampolskiy.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.basic.yampolskiy.saver.controllers.SaveController;
import ru.otus.basic.yampolskiy.saver.entities.FileFormat;


/**
 * Главный класс приложения SaverApplication.
 *
 * Этот класс запускает Spring Boot приложение и вызывает метод для получения и сохранения данных
 * после старта приложения.
 */
@SpringBootApplication
public class SaverApplication implements CommandLineRunner {

    /**
     * Контроллер для управления процессом получения данных и их сохранения.
     *
     * Этот компонент автоматически внедряется через конструктор.
     */
    private static SaveController saveController;

    /**
     * Контекст приложения.
     *
     */
    private final ApplicationContext context;

    /**
     * Формат сохранения данных. Значение задается из конфигурации приложения.
     */
    @Value("${file.format}")
    private String fileFormat;

    /**
     * Конструктор, который инициализирует {@link SaveController}.
     *
     * @param saveController экземпляр {@link SaveController}, автоматически внедряемый Spring'ом.
     */
    @Autowired
    public SaverApplication(SaveController saveController, ApplicationContext context) {
        SaverApplication.saveController = saveController;
        this.context = context;
    }

    /**
     * Главный метод для запуска Spring Boot приложения.
     *
     * @param args аргументы командной строки.
     * @throws Exception если возникает ошибка при запуске приложения.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SaverApplication.class, args);
    }

    /**
     * Метод, который выполняется после запуска приложения.
     *
     * Этот метод вызывает {@link SaveController#getDataAndSave(FileFormat)} для получения и сохранения данных.
     *
     * @param args аргументы командной строки.
     * @throws Exception если возникает ошибка при выполнении метода.
     */
    @Override
    public void run(String... args) throws Exception {
        FileFormat currentFormat = FileFormat.valueOf(fileFormat.toUpperCase());
        saveController.getDataAndSave(currentFormat);
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
}
