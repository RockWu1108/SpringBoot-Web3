package com.example.web3api.config;

import com.example.web3api.Shop;
import com.example.web3api.properties.shopProperties;
import com.example.web3api.service.shopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import okhttp3.OkHttpClient;

@Configuration
public class  shopConfig {

    private static final Logger LOG = LoggerFactory.getLogger(shopConfig.class);

    @Value("${lottery.contract.owner-address}")
    private String ownerAddress;

    @Value("${web3j.client-address}")
    private String clientAddress;

    @Autowired
    private shopProperties config;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(clientAddress, new OkHttpClient.Builder().build()));
    }

    @Bean
    public shopService contract(Web3j web3j, @Value("${lottery.contract.address:}") String contractAddress)
            throws Exception {
        if (StringUtils.isEmpty(contractAddress)) {
            Shop shop = deployContract(web3j);
            return initShopService(shop.getContractAddress(), web3j);
        }

        return initShopService(contractAddress, web3j);
    }

    private shopService initShopService(String contractAddress, Web3j web3j) {
        return new shopService(contractAddress, web3j, config);
    }

    private Shop deployContract(Web3j web3j) throws Exception {
        LOG.info("About to deploy new contract...");
        Shop contract = Shop.deploy(web3j, txManager(web3j), config.gas()).send();
        LOG.info("Deployed new contract with address '{}'", contract.getContractAddress());
        return contract;
    }

    private TransactionManager txManager(Web3j web3j) {
        return new ClientTransactionManager(web3j, ownerAddress);
    }

}