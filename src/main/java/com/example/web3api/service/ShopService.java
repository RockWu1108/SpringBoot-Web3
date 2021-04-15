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
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.Quorum;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;


public class ShopService {


    private final String contractAddress;
    private final Quorum quorum;
    private final shopProperties config;
    private String pwd = "12345";
    private String keystore = "C:\\Users\\6701\\Desktop\\tools\\geth\\db\\keystore\\UTC--2021-04-15T01-57-06.420205300Z--88be8da2c54ee601e9a4ce85f5bfd65b328db95a";


    public ShopService(String contractAddress, Quorum quorum, shopProperties config) {
        this.contractAddress = contractAddress;
        this.quorum = quorum;
        this.config = config;
    }

    public BigInteger getBalance() throws IOException {
        return quorum.ethGetBalance(contractAddress, DefaultBlockParameterName.LATEST).send().getBalance();
    }


    private Shop loadContract() throws IOException, CipherException {
        return Shop.load(contractAddress, quorum, getCredential(), config.gas());
    }

    private Credentials getCredential() throws IOException, CipherException {
        return  WalletUtils.loadCredentials(pwd,keystore);
    }


    private TransactionManager txManager(String accountAddress) {
        return new ClientTransactionManager(quorum, accountAddress);
    }

    public TransactionReceipt setProduct(
                             BigInteger id,
                             String name,
                             BigInteger price) throws Exception {
        Shop shop = loadContract();
        TransactionReceipt transactionReceipt = shop.setProduct(id, name, price).send();
        return  transactionReceipt;
    }

    public  HashMap<String, Object> getHistory(String ownerAddress,BigInteger id)throws Exception{

        Shop shop = loadContract();
        Tuple2<String, BigInteger> result = shop.getHistory(id).sendAsync().get();
        HashMap<String, Object> map = new HashMap<>();
        map.put("productName" , result.getValue1());
        map.put("amount" , result.getValue2());
        return  map ;
    }

    public  HashMap<String, Object> buyProduct(String testAddress, BigInteger id, BigInteger amount) throws Exception {
        Shop shop = loadContract();
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
        Shop shop = loadContract();
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