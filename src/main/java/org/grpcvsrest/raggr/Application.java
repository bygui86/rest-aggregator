package org.grpcvsrest.raggr;

import org.grpcvsrest.raggr.datapump.DataPump;
import org.grpcvsrest.raggr.datasource.Datasource;
import org.grpcvsrest.raggr.repo.InMemoryContentRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("datasourceA")
    public Datasource datasourceA(RestTemplate restTemplate, @Value("${datasource.a.url}") String url) {
        return new Datasource(restTemplate, url);
    }

    @Bean("datasourceB")
    public Datasource datasourceB(RestTemplate restTemplate, @Value("${datasource.b.url}") String url) {
        return new Datasource(restTemplate, url);
    }

    @Bean("datapumpA")
    public DataPump dataPumpA(
            InMemoryContentRepo repo,
            @Qualifier("datasourceA") Datasource datasource,
            @Value("${content_type.a}") String contentType) {
        return new DataPump(repo, datasource, contentType);
    }

    @Bean("datapumpB")
    public DataPump dataPumpB(
            InMemoryContentRepo repo,
            @Qualifier("datasourceB") Datasource datasource,
            @Value("${content_type.b}") String contentType) {
        return new DataPump(repo, datasource, contentType);
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
