package com.example.web3api.repository;

import com.example.web3api.domin.raftNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface nodeRepository extends JpaRepository<raftNode, Long> {

//    @Query(value = "SELECT r.ip , r.rpcport  from raftNode r")
//    List<String> findIp();
}
