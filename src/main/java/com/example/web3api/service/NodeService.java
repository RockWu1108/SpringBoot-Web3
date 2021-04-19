package com.example.web3api.service;

import java.io.IOException;
import java.math.BigInteger;
import java.net.http.HttpClient;
import java.util.List;

import jdk.jfr.Frequency;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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


    public String getPeerInfo() throws  IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response =null;
        try{
            HttpPost httpPost = new HttpPost("http://211.75.194.27:22000");
            String json = "{\"jsonrpc\":\"2.0\",\"method\":\"raft_cluster\",\"params\":[],\"id\":\"1\"}";
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept","application/json");
            httpPost.setHeader("Content-type" , "application/x-www-form-urlencoded");
            response = httpClient.execute(httpPost);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        try {
            System.out.println(EntityUtils.toString(entity));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EntityUtils.toString(entity);
    }
}