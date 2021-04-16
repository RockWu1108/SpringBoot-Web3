package com.example.web3api.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

import com.example.web3api.properties.shopProperties;
import com.example.web3api.Shop;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.Quorum;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.Callback;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;


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


    private Shop loadContract(String privateKey) throws IOException, CipherException {
        FastRawTransactionManager fastRawTxMgr =new FastRawTransactionManager(quorum, getCredential(privateKey), new PollingTransactionReceiptProcessor(quorum, 20, 20));
        return Shop.load(contractAddress, quorum,fastRawTxMgr, config.gas());
    }


    private Credentials getCredential(String privateKey) throws IOException, CipherException {
        Credentials credentials = Credentials.create(privateKey);
        return  credentials;
    }


    private TransactionManager txManager(String accountAddress) {
        return new ClientTransactionManager(quorum, accountAddress);
    }

    public TransactionReceipt setProduct(
                             String privateKey,
                             BigInteger id,
                             String name,
                             BigInteger price) throws Exception {

        //等待交易被挖掘
        Shop shop = loadContract(privateKey);


        TransactionReceipt transactionReceipt = shop.setProduct(id, name, price).send();
//        do{
//
//            EthGetTransactionReceipt ethGetTransactionReceiptResp = quorum.ethGetTransactionReceipt(result.getTransactionHash()).send();
//            transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
//            Thread.sleep(1500); // 3秒後重試
//        }while (!transactionReceipt.isPresent() && nonce.compareTo(quorum.ethGetTransactionCount(getCredential().getAddress(),DefaultBlockParameterName.LATEST).send().getTransactionCount())==-1);
        return  transactionReceipt;
    }

    public  HashMap<String, Object> getHistory(String privateKey,BigInteger id)throws Exception{

        Shop shop = loadContract(privateKey);
        Tuple2<String, BigInteger> result = shop.getHistory(id).send();
        HashMap<String, Object> map = new HashMap<>();
        map.put("productName" , result.getValue1());
        map.put("amount" , result.getValue2());
        return  map ;
    }

    public  HashMap<String, Object> buyProduct(String privateKey, BigInteger id, BigInteger amount) throws Exception {
        Shop shop = loadContract(privateKey);
        HashMap<String, Object> map = new HashMap<>();

        if(!shop.isRegister(getCredential(privateKey).getAddress()).send()){
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

    public  HashMap<String, Object> register(String privateKey) throws Exception {
        Shop shop = loadContract(privateKey);
        HashMap<String, Object> map = new HashMap<>();

        if(shop.isRegister(getCredential(privateKey).getAddress()).send().booleanValue()){
            map.put("errorMessage" , "You have register");
        }
        else{
            Boolean result = shop.register().send().isStatusOK();

            map.put("result" , result);
        }
        return map;
    }
}
class MyCallback implements Callback{

    @Override
    public void accept(TransactionReceipt transactionReceipt) {
        System.out.println(transactionReceipt.getTransactionHash());
    }

    @Override
    public void exception(Exception e) {
        System.out.println(e);
    }
}