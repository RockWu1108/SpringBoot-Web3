package com.example.web3api.controller;


import com.example.web3api.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.methods.response.raft.RaftCluster;

import java.io.IOException;
import java.math.BigInteger;

@RestController
public class nodeController {

    private final NodeService nodeService ;

    @Autowired
    private Quorum quorum;

    @Autowired
    public nodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }


    @GetMapping("/node/getNetListening")
    public boolean getNetListening() throws IOException {
        return  nodeService.getNetListening();
    }

    @GetMapping("/node/getNetPeerCount")
    public BigInteger getNetPeerCount() throws IOException {
        return  nodeService.getNetPeerCount();
    }

    @GetMapping("/node/getNodeCount")
    public BigInteger getNodeCount() throws IOException {
        return  nodeService.getNodeCount();
    }

//    @GetMapping("/node/getPeerInfo")
//    public String getPeerInfo() throws  IOException {
//        return  nodeService.getPeerInfo();
//    }




}
