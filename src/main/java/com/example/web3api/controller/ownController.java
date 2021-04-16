package com.example.web3api.controller;


import com.example.web3api.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.*;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.quorum.Quorum;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

@RestController
public class ownController {
    @Value("${lottery.contract.owner-address}")
    private String ownerAddress;

    @Autowired
    private Quorum quorum;

    @Autowired
    private ShopService shopService;

    @GetMapping("/owner")
    public String getAddress() {
        return ownerAddress;
    }

    @GetMapping("/owner/balance")
    public BigInteger getBalance() throws IOException {
        EthGetBalance wei = quorum.ethGetBalance(ownerAddress, DefaultBlockParameterName.LATEST).send();
        return wei.getBalance();
    }

    @GetMapping("/owner/getPrivateKey")
    public String getPrivateKey() throws IOException {
        String dataFile = "C:\\Users\\6701\\Desktop\\tools\\geth\\db\\keystore\\UTC--2021-04-15T01-57-06.420205300Z--88be8da2c54ee601e9a4ce85f5bfd65b328db95a";
        String pwd = "12345";
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(pwd ,dataFile);

        } catch (CipherException e) {
            e.printStackTrace();
        }
        return credentials.getEcKeyPair().getPrivateKey().toString(16);
    }

    @GetMapping("/owner/sendTransaction")
    public String sendTransaction() throws IOException {

        try {
            String dataFile = "C:\\Users\\6701\\Desktop\\tools\\geth\\db\\keystore\\UTC--2021-04-15T01-57-06.420205300Z--88be8da2c54ee601e9a4ce85f5bfd65b328db95a";
            String pwd = "12345";
            Credentials credentials = null;

            String toEOA = "0xd13d50b4edf71dbdef4598451602c56e862287eb";
            BigInteger eth = Convert.toWei("2", Convert.Unit.ETHER).toBigInteger();
            EthGetTransactionCount ethGetTransactionCount = quorum.ethGetTransactionCount(
                    ownerAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
            credentials = WalletUtils.loadCredentials(pwd, dataFile);
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            BigInteger gasPrice = new BigInteger("" + 1);
            BigInteger gasLimit = new BigInteger("" + 30000);
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, toEOA, eth);
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            EthSendTransaction ethSendTransaction = quorum.ethSendRawTransaction(hexValue).sendAsync().get();
            String hash = ethSendTransaction.getTransactionHash();
            return hash;
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    @GetMapping("/owner/geTransactionCount")
    public BigInteger geTransactionCount() throws IOException {
        EthGetTransactionCount ethGetTransactionCount = quorum.ethGetTransactionCount(ownerAddress,DefaultBlockParameterName.LATEST).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        return nonce;
    }


}