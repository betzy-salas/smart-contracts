package com.blockchain.auction.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "eth")
@Data
public class EthConfig {
    private String rpcUrl;
    private String privateKey;
    private String contractAddress;
    private long chainId;
}

