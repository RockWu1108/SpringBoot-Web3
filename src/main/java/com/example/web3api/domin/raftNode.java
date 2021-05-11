package com.example.web3api.domin;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iplist")
public class raftNode {

    @Id
    private Integer id;
    private String name;
    private String ip;
    private Integer rpcport;
    private Integer qmport;

    public raftNode() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getRpcport() {
        return rpcport;
    }

    public void setRpcport(Integer rpcport) {
        this.rpcport = rpcport;
    }

    public Integer getQmport() {
        return qmport;
    }

    public void setQmport(Integer qmport) {
        this.qmport = qmport;
    }
}
