package com.example.web3api.config;

import com.example.web3api.Shop;
import com.example.web3api.properties.shopProperties;
import com.example.web3api.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import okhttp3.OkHttpClient;

import java.io.IOException;

@Configuration
public class  shopConfig {

    private static final Logger LOG = LoggerFactory.getLogger(shopConfig.class);

    @Value("${lottery.contract.owner-address}")
    private String ownerAddress;

    @Value("${web3j.client-address}")
    private String clientAddress;

    private String pwd = "12345";
    private String keystore = "C:\\Users\\6701\\Desktop\\tools\\geth\\db\\keystore\\UTC--2021-04-15T01-57-06.420205300Z--88be8da2c54ee601e9a4ce85f5bfd65b328db95a";

    @Autowired
    private shopProperties config;

    @Bean
    public Quorum Quorum() {
        return Quorum.build(new HttpService(clientAddress, new OkHttpClient.Builder().build()));
    }

    @Bean
    public ShopService contract(Quorum quorum, @Value("${lottery.contract.address:}") String contractAddress)
            throws Exception {
        if (StringUtils.isEmpty(contractAddress)) {
            Shop shop = deployContract(quorum);
            return initShopService(shop.getContractAddress(), quorum);
        }

        return initShopService(contractAddress, quorum);
    }

    private ShopService initShopService(String contractAddress, Quorum quorum) {

        return new ShopService(contractAddress, quorum, config);
    }

    private Shop deployContract(Quorum quorum) throws Exception {
        LOG.info("About to deploy new contract...");
        Shop contract = Shop.deploy(quorum, getCredential(), config.gas()).send();
        LOG.info("Deployed new contract with address '{}'", contract.getContractAddress());
        return contract;
    }

    private TransactionManager txManager(Quorum quorum) {
        return new ClientTransactionManager(quorum, ownerAddress);
    }

    private Credentials getCredential() throws IOException, CipherException {
        return  WalletUtils.loadCredentials(pwd,keystore);
    }
}