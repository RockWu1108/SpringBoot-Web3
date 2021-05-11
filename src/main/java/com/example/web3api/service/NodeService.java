package com.example.web3api.service;

import com.example.web3api.domin.raftNode;
import com.example.web3api.repository.nodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.web3j.quorum.Quorum;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Service
public class NodeService {

    private final Quorum quorum;

    @Autowired
    private nodeRepository nodeRepository;

    public NodeService(Quorum quorum) {
        this.quorum = quorum;
    }

    public Boolean getNetListening() throws IOException {

        return quorum.netListening().send().isListening();
    }

    public BigInteger getNetPeerCount() throws IOException {
        return quorum.netPeerCount().send().getQuantity();
    }

    public BigInteger getNodeBlockNumber() throws IOException {
        return quorum.ethBlockNumber().send().getBlockNumber();

    }

    public List<String> getNodeAccount() throws IOException {
        return quorum.ethAccounts().send().getAccounts();

    }

    public String getPeerInfo() throws IOException {
        String result = null;
        RestTemplate restTemplate = new RestTemplate();
        List<raftNode> nodeInfo = nodeRepository.findAll();
        String queryUrl = "";
        try {
            for (int i = 0; i < nodeInfo.size(); i++) {
                queryUrl = "http://" + nodeInfo.get(i).getIp() + ":" + nodeInfo.get(i).getQmport() + "/qm/getNodeList";
                result = restTemplate.getForObject(queryUrl, String.class);
                if (!result.equals("")) {
                    break;
                }

            }
        } catch (Exception e) {

        }


        return result;
    }

    public Boolean handleErrorMessage(String account, Boolean status, String email, String description) throws IOException {
        
        return true;
    }


//    public String getPeerInfo() throws IOException {
//        String result = null;
//        String data = "";
//        try {
//            String queryUrl = "http://211.75.194.27:22204/qm/getNodeList";
//            //Getting the response code
////            result = restTemplate.getForObject(queryUrl, String.class);
//            InputStream is = new URL(queryUrl).openStream();
//            try {
//                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8")); //避免中文亂碼問題
//                StringBuilder sb = new StringBuilder();
//                int cp;
//                while ((cp = rd.read()) != -1) {
//                    sb.append((char) cp);
//                }
//                data = sb.toString();
//                JSONArray jsonArray = new JSONArray(data);
//                raftNode node = new raftNode();
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Iterator<?> keys = jsonObject.keys();
//                    while (keys.hasNext()) {
//                        String key = (String) keys.next();
//                        System.out.println("key :" + key + " value :" + jsonObject.getString(key));
//
//                        switch (key) {
//                            case "nodeName":
//                                node.setNodeName(jsonObject.getString(key));
//                                break;
//                            case "enode":
//                                node.setEnode(jsonObject.getString(key));
//                                break;
//                            case "ip":
//                                node.setIp(jsonObject.getString(key));
//                                break;
//                            case "active":
//                                node.setActive(Boolean.parseBoolean(jsonObject.getString(key)));
//                                break;
//                            case "id":
//                                node.setId(Long.parseLong(jsonObject.getString(key)));
//                                break;
//
//                        }
//                    }
//                    nodeRepository.save(node);
//                    System.out.println("");
//                }
//            } finally {
//                is.close();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return data;
//
//    }


}