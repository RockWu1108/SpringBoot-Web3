package com.example.web3api;

import com.example.web3api.config.vaultConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.example.web3api.config.vaultConfig;



@SpringBootApplication
public class Web3apiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Web3apiApplication.class, args);
    }

}
