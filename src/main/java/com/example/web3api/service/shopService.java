package com.example.web3api.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.example.web3api.properties.shopProperties;
import com.example.web3api.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

public class shopService {

    private final String contractAddress;
    private final Web3j web3j;
    private final shopProperties config;


    public shopService(String contractAddress, Web3j web3j, shopProperties config) {
        this.contractAddress = contractAddress;
        this.web3j = web3j;
        this.config = config;
    }

    public BigInteger getBalance() throws IOException {
        return web3j.ethGetBalance(contractAddress, DefaultBlockParameterName.LATEST).send().getBalance();
    }


    private Shop loadContract(String accountAddress) {
        return Shop.load(contractAddress, web3j, txManager(accountAddress), config.gas());
    }

    private TransactionManager txManager(String accountAddress) {
        return new ClientTransactionManager(web3j, accountAddress);
    }

    public TransactionReceipt setProduct(String ownerAddress,
                             BigInteger id,
                             String name,
                             BigInteger price) throws Exception {
        Shop shop = loadContract(ownerAddress);
        TransactionReceipt transactionReceipt = shop.setProduct(id, name, price).send();
        return  transactionReceipt;
    }

    public  HashMap<String, Object> getHistory(String ownerAddress,BigInteger id)throws Exception{

        Shop shop = loadContract(ownerAddress);
        Tuple2<String, BigInteger> result = shop.getHistory(id).sendAsync().get();
        HashMap<String, Object> map = new HashMap<>();
        map.put("productName" , result.getValue1());
        map.put("amount" , result.getValue2());
        return  map ;
    }

    public  HashMap<String, Object> buyProduct(String testAddress, BigInteger id, BigInteger amount) throws Exception {
        Shop shop = loadContract(testAddress);
        HashMap<String, Object> map = new HashMap<>();

        if(!shop.isRegister(testAddress).send()){
            map.put("errorMessage" , "You didnt register");
        }
        else{
            BigInteger price = shop.product(id).send().getValue2();

            TransactionReceipt transactionReceipt = shop.buyProduct(id , amount , price.multiply(amount)).send();
            System.out.println("Price :" + price);
            System.out.println("TransactionReceipt:" + transactionReceipt.getTransactionHash());
            map.put("transactionReceipt" , transactionReceipt);
        }
        return map;
    }

    public  HashMap<String, Object> register(String testAddress) throws Exception {
        Shop shop = loadContract(testAddress);
        HashMap<String, Object> map = new HashMap<>();

        if(shop.isRegister(testAddress).send().booleanValue()){
            map.put("errorMessage" , "You have register");
        }
        else{
            Boolean result = shop.register().send().isStatusOK();

            map.put("result" , result);
        }
        return map;
    }

}