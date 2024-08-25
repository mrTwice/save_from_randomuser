package ru.otus.basic.yampolskiy.saver.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Конфигурационный класс для настройки приложения Saver.
 *
 * Этот класс определяет бины, необходимые для создания и настройки RestClient,
 * используемого для выполнения HTTP-запросов.
 */
@Configuration
public class SaverApplicationConfiguration {

    /**
     * Базовый URL, который используется для всех HTTP-запросов.
     * Значение извлекается из свойств приложения.
     */
    @Value("${randomuser-api.url}")
    private String baseUrl;

    /**
     * Создает и возвращает экземпляр {@link RestClient} с настройками по умолчанию.
     *
     * @param builder объект {@link RestClient.Builder}, используемый для создания RestClient.
     * @return настроенный {@link RestClient} для выполнения HTTP-запросов.
     */
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

    /**
     * Создает и возвращает экземпляр {@link RestClientCustomizer}, который настраивает
     * {@link RestClient.Builder} с использованием пользовательских параметров, таких как
     * базовый URL и фабрика запросов.
     *
     * @return {@link RestClientCustomizer}, который настраивает {@link RestClient.Builder}.
     */
    @Bean
    public RestClientCustomizer restClientCustomizer() {
        // Возвращает функцию для настройки RestClientBuilder
        return (restClientBuilder) -> restClientBuilder
                // Использует JdkClientHttpRequestFactory для создания HTTP-запросов
                .requestFactory(new JdkClientHttpRequestFactory())
                // Устанавливает базовый URL для всех запросов
                .baseUrl(baseUrl);
    }
}
