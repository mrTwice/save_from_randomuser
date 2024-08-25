package ru.otus.basic.yampolskiy.saver.repositories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.basic.yampolskiy.saver.entities.randomuser.Root;
import ru.otus.basic.yampolskiy.saver.entities.randomuser.User;
import ru.otus.basic.yampolskiy.saver.exceptions.CreateDirectoryException;
import ru.otus.basic.yampolskiy.saver.exceptions.SaveFileException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Репозиторий для сохранения файлов на диск.
 *
 * Этот класс отвечает за сохранение данных в формате CSV в указанную директорию.
 */
@Repository
public class FileRepository {

    /**
     * Директория, в которую сохраняются файлы. Значение задается из конфигурации приложения.
     */
    @Value("${save.directory}")
    private String directory;

    /**
     * Имя файла, используемое при сохранении. Значение задается из конфигурации приложения.
     */
    @Value("${file.name}")
    private String filename;

    /**
     * Логгер для записи сообщений о событиях, происходящих в процессе работы репозитория.
     */
    private Logger logger = LogManager.getLogger(this);

    /**
     * Форматтер для добавления временной метки к имени файла.
     */
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");

    /**
     * Заголовки таблицы для книги Exel
     */
    private final String[] COLUMNS = {
            "Gender", "Title", "First Name", "Last Name",
            "Street Number", "Street Name", "City", "State",
            "Country", "Postcode", "Latitude", "Longitude",
            "Timezone Offset", "Timezone Description", "Email",
            "UUID", "Username", "Password", "Salt", "MD5",
            "SHA1", "SHA256", "DOB Date", "DOB Age",
            "Registered Date", "Registered Age", "Phone", "Cell",
            "ID Name", "ID Value", "Picture Large", "Picture Medium",
            "Picture Thumbnail", "Nationality"
    };

    /**
     * Сохраняет данные из ресурса в файл формата CSV.
     *
     * @param resource ресурс, содержащий данные для сохранения.
     * @throws SaveFileException если произошла ошибка при сохранении файла.
     */
    public void saveToCSV(Resource resource) {
        if (resource != null) {
            try {
                String filePath = generateFilePath("csv");

                try (InputStream inputStream = resource.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                     Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8")) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Заменяем запятые на точки с запятой для Microsoft Excel
                        String modifiedLine = line.replace(",", ";");
                        writer.write(modifiedLine);
                        writer.write(System.lineSeparator());
                    }

                    logger.info("Файл успешно загружен и сохранен по пути: {}", filePath);
                } catch (IOException e) {
                    logger.error("Ошибка сохранения файла", e);
                    throw new SaveFileException("Ошибка сохранения файла");
                }
            } catch (CreateDirectoryException e) {
                logger.error("Ошибка создания директории", e);
                throw e;
            }
        }
    }

    /**
     * Сохраняет данные из объекта {@link Root} в файл формата XLSX.
     *
     * @param root объект типа {@link Root}, содержащий данные для сохранения.
     */
    public void saveToXLSX(Root root) {
        String filePath = generateFilePath("xlsx");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < COLUMNS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(COLUMNS[i]);
        }

        // Заполнение данных
        List<User> users = root.getResults();
        int rowNum = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(user.getGender());
            row.createCell(1).setCellValue(user.getName().getTitle());
            row.createCell(2).setCellValue(user.getName().getFirst());
            row.createCell(3).setCellValue(user.getName().getLast());
            row.createCell(4).setCellValue(user.getLocation().getStreet().getNumber());
            row.createCell(5).setCellValue(user.getLocation().getStreet().getName());
            row.createCell(6).setCellValue(user.getLocation().getCity());
            row.createCell(7).setCellValue(user.getLocation().getState());
            row.createCell(8).setCellValue(user.getLocation().getCountry());
            row.createCell(9).setCellValue(user.getLocation().getPostcode());
            row.createCell(10).setCellValue(user.getLocation().getCoordinates().getLatitude());
            row.createCell(11).setCellValue(user.getLocation().getCoordinates().getLongitude());
            row.createCell(12).setCellValue(user.getLocation().getTimezone().getOffset());
            row.createCell(13).setCellValue(user.getLocation().getTimezone().getDescription());
            row.createCell(14).setCellValue(user.getEmail());
            row.createCell(15).setCellValue(user.getLogin().getUuid());
            row.createCell(16).setCellValue(user.getLogin().getUsername());
            row.createCell(17).setCellValue(user.getLogin().getPassword());
            row.createCell(18).setCellValue(user.getLogin().getSalt());
            row.createCell(19).setCellValue(user.getLogin().getMd5());
            row.createCell(20).setCellValue(user.getLogin().getSha1());
            row.createCell(21).setCellValue(user.getLogin().getSha256());
            row.createCell(22).setCellValue(user.getDob().getDate());
            row.createCell(23).setCellValue(user.getDob().getAge());
            row.createCell(24).setCellValue(user.getRegistered().getDate());
            row.createCell(25).setCellValue(user.getRegistered().getAge());
            row.createCell(26).setCellValue(user.getPhone());
            row.createCell(27).setCellValue(user.getCell());
            row.createCell(28).setCellValue(user.getId().getName());
            row.createCell(29).setCellValue(user.getId().getValue());
            row.createCell(30).setCellValue(user.getPicture().getLarge());
            row.createCell(31).setCellValue(user.getPicture().getMedium());
            row.createCell(32).setCellValue(user.getPicture().getThumbnail());
            row.createCell(33).setCellValue(user.getNat());
        }

        // Автоматическое расширение столбцов
        for (int i = 0; i < COLUMNS.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Сохранение файла
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            logger.info("Файл успешно загружен и сохранен по пути: {}", filePath);
        } catch (IOException e) {
            logger.error("Ошибка сохранения файла", e);
        }

        // Закрытие workbook
        try {
            workbook.close();
            logger.info("Книга {} успешно закрыта.", workbook);
        } catch (IOException e) {
            logger.error("Ошибка закрытия книги", e);
        }
    }


    /**
     * Генерирует путь для файла с добавлением временной метки.
     *
     * @param extension расширение файла (например, "csv" или "xlsx").
     * @return сгенерированный путь к файлу.
     * @throws CreateDirectoryException если не удалось создать директорию для сохранения файла.
     */
    public String generateFilePath(String extension) throws CreateDirectoryException {
        // Проверка и создание директории
        File dir = new File(directory);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new CreateDirectoryException("Не удалось создать директорию: " + directory);
            }
        }

        // Генерация имени файла с таймстампом
        String timeStamp = dateFormatter.format(new Date());
        return directory + File.separator + filename + "_" + timeStamp + "." + extension;
    }
}

