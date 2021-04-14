package com.example.web3api.controller;


import com.example.web3api.service.shopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@RestController
public class ownController {
    @Value("${lottery.contract.owner-address}")
    private String ownerAddress;

    @Autowired
    private Web3j web3j;

    @Autowired
    private shopService shopService;

    @GetMapping("/owner")
    public String getAddress() {
        return ownerAddress;
    }

    @GetMapping("/owner/balance")
    public BigInteger getBalance() throws IOException {
        EthGetBalance wei = web3j.ethGetBalance(ownerAddress, DefaultBlockParameterName.LATEST).send();
        return wei.getBalance();
    }

}
