package com.example.web3api.properties;

import java.math.BigInteger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.web3j.tx.gas.StaticGasProvider;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "lottery.contract")
@Getter
@Setter
public class shopProperties {
    private BigInteger gasPrice;
    private BigInteger gasLimit;

    public StaticGasProvider gas() {

        return new StaticGasProvider(gasPrice, gasLimit);
    }
}