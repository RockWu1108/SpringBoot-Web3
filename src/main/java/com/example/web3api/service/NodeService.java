package com.example.web3api.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;
import org.web3j.quorum.Quorum;

@Service
public class NodeService {


    private final Quorum quorum;

    public NodeService(Quorum quorum) {
        this.quorum = quorum;
    }

    public Boolean getNetListening() throws IOException {

        return quorum.netListening().send().isListening();
    }

    public  BigInteger getNetPeerCount() throws IOException{
        return quorum.netPeerCount().send().getQuantity();
    }

    public BigInteger getNodeBlockNumber() throws  IOException{
        return  quorum.ethBlockNumber().send().getBlockNumber();
    }

    public List<String> getNodeAccount() throws  IOException{
        return  quorum.ethAccounts().send().getAccounts();
    }


}