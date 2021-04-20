package com.example.web3api.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
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


    public Map<String, Object> getPeerInfo() throws  IOException {
        Map<String, Object> jsonMap=null;
        try {
            String queryUrl = "http://211.75.194.27:22000";
            String json = "{\"jsonrpc\":\"2.0\",\"method\":\"raft_cluster\",\"params\":[],\"id\":\"1\"}";
            URL url = new URL(queryUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            ObjectMapper mapper = new ObjectMapper();
            jsonMap = mapper.readValue(in, Map.class);
            in.close();
        }catch (Exception e){e.printStackTrace();}
        return  jsonMap ;
    }

}