package com.example.boilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.util.TimeZone;
import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackageClasses = {
        BoilerplateApplication.class,
        Jsr310JpaConverters.class
})
public class BoilerplateApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BoilerplateApplication.class, args);
    }

}