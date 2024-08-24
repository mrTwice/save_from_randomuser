package ru.otus.basic.yampolskiy.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.basic.yampolskiy.saver.entities.FileFormat;
import ru.otus.basic.yampolskiy.saver.service.CsvUserExportService;
import ru.otus.basic.yampolskiy.saver.service.RandomUserService;

@SpringBootApplication
public class SaverApplication implements CommandLineRunner {

    private static RandomUserService userService;
    private static CsvUserExportService exportService;

    @Value("${file.name}")
    private String filename;

    @Value("${person.count}")
    private int userLimit;

    @Value("${save.directory}")
    private String directory;

    @Autowired
    public SaverApplication(RandomUserService userService, CsvUserExportService exportService) {
        SaverApplication.userService = userService;
        SaverApplication.exportService = exportService;
    }

    public static void saveDataToFile(FileFormat fileFormat, String filename, int userLimit, String directory) {
        switch (fileFormat) {
            case CSV -> exportService.saveToCsvFile(filename, userLimit, directory);
            case XLSX -> System.out.println("Сохранение в формате .xlsx не поддерживается в текущей версии приложения");
            case XLS -> System.out.println("Сохранение в формате .xls не поддерживается в текущей версии приложения");
            default -> throw new IllegalArgumentException("Неподдерживаемый формат файла: " + fileFormat);
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SaverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        saveDataToFile(FileFormat.CSV, filename, userLimit, directory);
    }
}
