package ru.otus.basic.yampolskiy.saver.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.basic.yampolskiy.saver.client.RandomUserApiClient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Data
@AllArgsConstructor
@Getter
public class CsvUserExportService {

    @Autowired
    private RandomUserApiClient userClient;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");


    public void saveToCsvFile(String filename, int userLimit, String directory) {
        ResponseEntity<Resource> response = userClient.getCsv(userLimit);
        Resource rawData = response.getBody();

        if (rawData != null) {
            // Проверка и создание директории
            File dir = new File(directory);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    throw new RuntimeException("Не удалось создать директорию: " + directory);
                }
            }

            String timeStamp = dateFormatter.format(new Date());
            String filePath = directory + File.separator + filename + "_" + timeStamp + ".csv";

            try (InputStream inputStream = rawData.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8")) {

                String line;
                while ((line = reader.readLine()) != null) {
                    // Заменяем запятые на точки с запятой
                    String modifiedLine = line.replace(",", ";");
                    writer.write(modifiedLine);
                    writer.write(System.lineSeparator());
                }

                System.out.println("Файл успешно загружен и сохранен по пути: " + filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
