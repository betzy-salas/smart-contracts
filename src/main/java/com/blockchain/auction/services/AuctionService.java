package com.blockchain.auction.services;

import com.blockchain.auction.config.EthConfig;
import com.blockchain.auction.contract.Auction;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

@Service
public class AuctionService {

    private final Auction contract;

    public AuctionService(EthConfig config) {
        Web3j web3j = Web3j.build(new HttpService(config.getRpcUrl()));
        Credentials credentials = Credentials.create(config.getPrivateKey());

        this.contract = Auction.load(
                config.getContractAddress(),
                web3j,
                credentials,
                new DefaultGasProvider()
        );
    }

    public String getDescription() throws Exception {

        String contractAddress = contract.getContractAddress();
        System.out.println("Dirección del contrato: " + contractAddress);

        System.out.println("Contrato válido: " + contract.isValid());

        boolean isValid = contract.isValid();
        System.out.println("¿Contrato válido?: " + isValid);

        try {
            String descripcion = contract.getDescription().send();
            System.out.println("Descripción: " + descripcion);
            return descripcion;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "nulo";
    }

    public String getImageURI() throws Exception {
        return contract.getImageURI().send();
    }

    public Boolean isActive() throws Exception {
        return contract.isActive().send();
    }

    public String bid(BigInteger valueInWei) throws Exception {
        return contract.bid(BigInteger.ZERO).send().getTransactionHash();
    }

    public void stopAuction() throws Exception {
        contract.stopAuction().send();
    }
}

