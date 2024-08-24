package ru.otus.basic.yampolskiy.saver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class SaverApplicationConfiguration {

    @Value("${randomuser-api.url}")
    private String baseUrl;

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

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
