package com.example.web3api.controller;


import com.example.web3api.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.quorum.Quorum;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

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

    @GetMapping("/node/getNodeBlockNumber")
    public BigInteger getNodeBlockNumber() throws IOException {
        return  nodeService.getNodeBlockNumber();
    }

//    @GetMapping("/node/getPeerInfo")
//    public String getPeerInfo() throws  IOException {
////        return  nodeService.getPeerInfo();
////    }

       @GetMapping("/node/getNodeAccount")
    public List<String> getAccount() throws  IOException {
        return  nodeService.getNodeAccount();
    }




}
