package com.example.web3api.service;

import java.io.IOException;
import java.math.BigInteger;

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

    public BigInteger getNodeCount() throws  IOException{
        return  quorum.ethBlockNumber().send().getBlockNumber();
    }


}