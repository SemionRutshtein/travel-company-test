package com.trip.app;

import com.google.common.collect.ImmutableList;
import com.trip.app.service.CsvSuggestionConverter;
import com.trip.app.service.CsvSuggestionWriter;
import com.trip.app.service.GoEuroApiClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

    @Autowired
    private CsvSuggestionWriter csvSuggestionWriter;
    @Autowired
    private GoEuroApiClient goEuroApiClient;
    @Autowired
    private CsvSuggestionConverter csvSuggestionConverter;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
    }



    public static void main(String[] args) {
        new SpringApplicationBuilder(AppApplication.class)
                .bannerMode(Banner.Mode.LOG)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        String cityName = args[0].trim();
        String fileName = cityName + ".csv";

        // With using StringBuilder that's look like that
        // String fileName = new StringBuilder(cityName).append(".csv").toString();

        csvSuggestionWriter.write(fileName, goEuroApiClient.findSuggestionsByCity(cityName).stream()
                .map(csvSuggestionConverter::toCsvSuggestionDto)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf)));

    }
}
