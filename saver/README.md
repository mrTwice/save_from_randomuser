# Saver

## Описание
Saver — это консольное Spring Boot приложение, предназначенное для генерации и сохранения данных пользователей, получаемых из внешнего API [randomuser.me](https://randomuser.me). Приложение может сохранять данные в различных форматах файлов, таких как CSV или XLSX, и предоставляет возможность настраивать количество генерируемых пользователей, путь для сохранения файла и его формат.

## Основные технологии и зависимости
- **Spring Boot**: Приложение построено на основе Spring Boot 3.3.3.
   - `spring-boot-starter-web`: Включает необходимые библиотеки для создания веб-приложений.
   - `spring-boot-devtools`: Предоставляет инструменты для ускорения разработки.
   - `spring-boot-starter-test`: Обеспечивает тестирование компонентов Spring с использованием различных тестовых фреймворков.

- **Apache POI**: Используется для работы с Excel-файлами в формате OOXML (`*.xlsx`), что позволяет сохранять данные пользователей в Excel.
   - `poi-ooxml`: Зависимость, необходимая для работы с Excel-файлами.

- **Lombok**: Облегчает написание Java-кода за счет автоматической генерации методов, таких как геттеры, сеттеры и конструкторы.

## Java версия
Проект использует Java версии 22.

## Система сборки
Приложение собрано с использованием Maven.

## Дефолтная конфигурация

Приложение имеет настройки по умолчанию, установленные в properties.yml, если соответствующие параметры не будут переданы через командную строку или непосредственно в файле
```yml
# Конфигурация сервера
server:
  port: 8084 # Порт, на котором запускается сервер

spring:
  application:
    name: saver # Имя приложения в контексте Spring

# Конфигурация для API случайных пользователей
randomuser-api:
  url: "https://api.randomuser.me" # URL для доступа к API случайных пользователей

# Конфигурация файла, в который будут сохраняться данные
file:
  name: "users"  # Имя файла
  format: "CSV"  # Формат файла (по умолчанию CSV)

# Конфигурация количества пользователей, которые будут сгенерированы
person:
  count: 50  # Количество пользователей

# Конфигурация директории для сохранения файлов
save:
  directory: "./uploads" # Путь к директории для сохранения файлов
```

## Описание основных классов и их методов

Назначение и функционал классов описаны с использованием javadoc

## Сборка

```bash
mvn clean package
```

## Запуск и настройка приложения

1. Приложение можно запустить из среды разработки или через командную строку с указанием параметров:
    - `--file.name` для указания имени выходного файла.
    - `--person.count` для указания количества пользователей для загрузки.
    - `--save.directory` для указания директории сохранения файлов
    - `--file.format=XLSX` для указания формата сохранения файла (по-умолчанию csv)
2. Если параметры не переданы, приложение выполнится с настройками установленными в properties.yml.
3. После выполнения задачи приложение автоматически остановится и освободит ресурсы

## Пример запуска

```bash
java -jar saver-0.0.1.jar --file.name=MyUserData --person.count=100 --save.directory=./MyTest --file.format=XLSX
```

## Возможные улучшения

- `Написание тестов` позволит повысить качество разработки и уменьшить количество ошибок в будущем, что сократит время и затраты на дальнейшую разработку
- `Расиширить текущий функционал` текущая реализация структуры, позволит легко добавить новый функционал, например сохранение полученных пользователей в базу данных
- `Добавление интерфейсов` позволит развязать текущую жесткую связь между различными слоями приложения, тем самым даст возможность легко менять реализацию различных слоев
- `Глобальный обработчки исключений` позволит обрабатывать все исключения возникающие в приложении в едином месте
- `Можно добавить аспеты ` которые позволят подробно логгировать выполнение отдельных методов или всех методов всех классов в определенном пакете