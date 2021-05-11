package com.example.web3api.RR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RoundRobin {
    private static final Logger LOG = LoggerFactory.getLogger(RoundRobin.class);

    public static String ipAddress = "";
    public static int count0 = 0;
    public static int count1 = 0;
    public static int count2 = 0;
    public static int count3 = 0;

    public static String getServer() {
        //  重建一個Map，避免伺服器的上下線導致的並發問題
        Map<String, Integer> serverMap = new HashMap<String, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);

        //取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);
        Random random = new Random();
        int randomPos = random.nextInt(keyList.size());
        ipAddress = keyList.get(randomPos);

        switch (randomPos) {
            case 0:
                count0++;
                LOG.info("Choose IP Address : " + ipAddress + " count :" + count0);
                break;

            case 1:
                count1++;
                LOG.info("Choose IP Address : " + ipAddress + " count :" + count1);
                break;

            case 2:
                count2++;
                LOG.info("Choose IP Address : " + ipAddress + " count :" + count2);
                break;
            case 3:
                count3++;
                LOG.info("Choose IP Address : " + ipAddress + " count :" + count3);
                break;
        }


        return keyList.get(randomPos);
    }
}

