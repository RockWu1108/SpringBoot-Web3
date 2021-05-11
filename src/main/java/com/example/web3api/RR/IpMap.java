package com.example.web3api.RR;

import java.util.HashMap;

public class IpMap {
    // 待路由的Ip列表，Key代表Ip，Value代表該Ip的權重
    public static HashMap<String, Integer> serverWeightMap = new HashMap<String, Integer>();

    static {
        serverWeightMap.put("http://211.75.194.27:22200", 1);
        serverWeightMap.put("http://211.75.194.27:22300", 1);
        serverWeightMap.put("http://211.75.194.27:22400", 1);
        serverWeightMap.put("http://211.75.194.27:22500", 1);


//         //權重為4
//         serverWeightMap.put("192.168.1.102", 4);
//         serverWeightMap.put("192.168.1.103", 1);
//         serverWeightMap.put("192.168.1.104", 1);
//         // 權重為3
//         serverWeightMap.put("192.168.1.105", 3);
//         serverWeightMap.put("192.168.1.106", 1);
//         // 權重為2
//         serverWeightMap.put("192.168.1.107", 2);
//         serverWeightMap.put("192.168.1.108", 1);
//         serverWeightMap.put("192.168.1.109", 1);
//         serverWeightMap.put("192.168.1.110", 1);
    }
}

