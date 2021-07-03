package com.example.boilerplate.job;

import com.example.boilerplate.redis.AuthTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CronJob {
    private static final Logger logger = LoggerFactory.getLogger(CronJob.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    @Autowired
    private AuthTokenService authTokenService;

    @Scheduled(fixedDelay = 300000)
    public void syncTransactions() {
        logger.info("Delete Inactive Access Token:: Execution Time - {}",
                dateTimeFormatter.format(LocalDateTime.now()));
        authTokenService.removeInactiveTokens();
    }
}
