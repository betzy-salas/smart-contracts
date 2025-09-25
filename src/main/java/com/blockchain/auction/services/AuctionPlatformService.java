package com.blockchain.auction.services;


import com.blockchain.auction.config.EthConfig;
import com.blockchain.auction.contract.Auction;
import com.blockchain.auction.contract.AuctionPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

@Service
public class AuctionPlatformService {

    private final AuctionPlatform auctionPlatform;

    @Autowired
    public AuctionPlatformService(EthConfig config) {
        Web3j web3j = Web3j.build(new HttpService(config.getRpcUrl()));
        Credentials credentials = Credentials.create(config.getPrivateKey());

        this.auctionPlatform = AuctionPlatform.load(
                config.getContractAddress(),
                web3j,
                credentials,
                new DefaultGasProvider()
        );
    }

    public CompletableFuture<TransactionReceipt> createAuction(String description, String imageURI, long basePrice, long secondsToEnd) {
        RemoteFunctionCall<TransactionReceipt> tx = auctionPlatform.createAuction(
                description,
                imageURI,
                BigInteger.valueOf(basePrice),
                BigInteger.valueOf(secondsToEnd)
        );
        return tx.sendAsync();
    }

    public CompletableFuture<TransactionReceipt> bid(int auctionId, long amountInWei) {
        return auctionPlatform.bid(BigInteger.valueOf(auctionId), BigInteger.valueOf(amountInWei)).sendAsync();
    }

    public CompletableFuture<TransactionReceipt> checkAuctionEnd(int auctionId) {
        return auctionPlatform.checkAuctionEnd(BigInteger.valueOf(auctionId)).sendAsync();
    }

    public CompletableFuture<TransactionReceipt> stopAuction(int auctionId) {
        return auctionPlatform.stopAuction(BigInteger.valueOf(auctionId)).sendAsync();
    }

    public AuctionData getAuction(int auctionId) throws Exception {
        var response = auctionPlatform.getAuction(BigInteger.valueOf(auctionId)).send();
        return new AuctionData(
                response.component1(),
                response.component2(),
                response.component3(),
                response.component4(),
                response.component5(),
                response.component6(),
                response.component7(),
                response.component8(),
                response.component9(),
                response.component10()
        );
    }

    public BigInteger getAllAuctionsCount() throws Exception {
        return auctionPlatform.getAllAuctionsCount().send();
    }

    public record AuctionData(
            String description,
            String imageURI,
            BigInteger basePrice,
            BigInteger secondsToEnd,
            BigInteger createdTime,
            String originalOwner,
            String highestBidder,
            BigInteger highestPrice,
            Boolean active,
            Boolean ended
    ) {}
}

