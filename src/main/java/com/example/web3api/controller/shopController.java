package com.example.web3api.controller;

import com.example.web3api.RR.RoundRobin;
import com.example.web3api.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.Quorum;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
public class shopController {

    @Value("${lottery.contract.owner-address}")
    private String ownerAddress;

    @Value("${lottery.contract.test-address}")
    private String testAddress;

    private final ShopService shopService;

    @Autowired
    private Quorum quorum;

    @Autowired
    public shopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shop/balance")
    public BigInteger getLotteryBalance() throws IOException {
        return shopService.getBalance();
    }

    @PostMapping("/shop/setProduct")
    public TransactionReceipt setProduct(@RequestParam BigInteger id,
                                         @RequestParam String privateKey,
                                         @RequestParam String name,
                                         @RequestParam BigInteger price) throws Exception {
        return shopService.setProduct( privateKey,id, name, price);
    }

    @PostMapping("/shop/getHistory")
    public Map<String, Object> getHistory(@RequestParam BigInteger id,
                                          @RequestParam String privateKey) throws Exception {

        return shopService.getHistory(privateKey, id);
    }
    @PostMapping("/shop/buyProduct")
    public  HashMap<String, Object> buyProduct(
            @RequestParam BigInteger id,
            @RequestParam String privateKey,
            @RequestParam BigInteger amount)throws Exception{
        return shopService.buyProduct(privateKey , id , amount);
    }

    @PostMapping("/shop/register")
    public HashMap<String, Object>register(@RequestParam String privateKey)throws Exception{
        return shopService.register(privateKey);
    }

//    @GetMapping("/shop/getIp")
//    public String getIp()throws Exception{
//        return RoundRobin.getServer();
//    }





}
