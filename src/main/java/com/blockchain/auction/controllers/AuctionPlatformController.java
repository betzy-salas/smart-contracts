package com.blockchain.auction.controllers;

import com.blockchain.auction.services.AuctionPlatformService;
import com.blockchain.auction.services.AuctionPlatformService.AuctionData;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auction-platform")
public class AuctionPlatformController {

    private final AuctionPlatformService auctionService;

    /*@Autowired
    public AuctionPlatformController(AuctionPlatformService auctionService) {
        this.auctionService = auctionService;
    }*/

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<TransactionReceipt>> createAuction(@RequestBody CreateAuctionRequest request) {
        return auctionService.createAuction(
                        request.description(),
                        request.imageURI(),
                        request.basePrice(),
                        request.secondsToEnd()
                )
                .thenApply(ResponseEntity::ok);
    }
    /*@PostMapping("/create")
    public ResponseEntity<?> createAuction(@RequestBody CreateAuctionRequest request) {
        try {
            TransactionReceipt receipt = auctionService
                    .createAuction(
                            request.description,
                            request.imageURI,
                            request.basePrice,
                            request.secondsToEnd)
                    .get();

            return extractAuctionIdFromReceipt(receipt);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }*/

    @PostMapping("/{auctionId}/bid")
    public CompletableFuture<ResponseEntity<TransactionReceipt>> bid(@PathVariable int auctionId, @RequestParam long amountInWei) {
        return auctionService.bid(auctionId, amountInWei)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/{auctionId}/check-end")
    public CompletableFuture<ResponseEntity<TransactionReceipt>> checkAuctionEnd(@PathVariable int auctionId) {
        return auctionService.checkAuctionEnd(auctionId)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/{auctionId}/stop")
    public CompletableFuture<ResponseEntity<TransactionReceipt>> stopAuction(@PathVariable int auctionId) {
        return auctionService.stopAuction(auctionId)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionData> getAuction(@PathVariable int auctionId) throws Exception {
        return ResponseEntity.ok(auctionService.getAuction(auctionId));
    }

    @GetMapping("/count")
    public ResponseEntity<BigInteger> getAuctionsCount() throws Exception {
        return ResponseEntity.ok(auctionService.getAllAuctionsCount());
    }

    private static final Event AUCTION_CREATED_EVENT = new Event(
            "AuctionCreated",
            Arrays.asList(
                    new TypeReference<Uint256>() {},
                    new TypeReference<Address>() {}
            )
    );

    private ResponseEntity<String> extractAuctionIdFromReceipt(TransactionReceipt receipt) {
        String eventSignature = EventEncoder.encode(AUCTION_CREATED_EVENT);

        return receipt.getLogs().stream()
                .filter(log -> log.getTopics().getFirst().equals(eventSignature))
                .findFirst()
                .map(log -> {
                    List<Type> decoded = FunctionReturnDecoder.decode(
                            log.getData(), AUCTION_CREATED_EVENT.getParameters());

                    Uint256 auctionId = (Uint256) decoded.get(0);
                    Address creator = (Address) decoded.get(1);

                    return ResponseEntity.ok("Auction created with ID: " + auctionId.getValue());
                })
                .orElse(ResponseEntity.status(500).body("Auction created, but event not found"));
    }


    public record CreateAuctionRequest(String description, String imageURI, long basePrice, long secondsToEnd) {}
}
