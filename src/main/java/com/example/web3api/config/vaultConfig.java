package com.example.web3api.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
public class vaultConfig {

    private  String login ;
    private  String password;

}
