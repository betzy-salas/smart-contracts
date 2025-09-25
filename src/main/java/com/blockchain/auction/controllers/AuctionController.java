package com.blockchain.auction.controllers;

import com.blockchain.auction.services.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    /*@GetMapping("/description")
    public String getDescription() throws Exception {
        return auctionService.getDescription();
    }*/
    @GetMapping("/description")
    public ResponseEntity<String> getDescription() {
        try {
            String description = auctionService.getDescription();
            return ResponseEntity.ok(description);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/image")
    public String getImage() throws Exception {
        return auctionService.getImageURI();
    }

    @GetMapping("/active")
    public boolean isActive() throws Exception {
        return auctionService.isActive();
    }

    @PostMapping("/bid")
    public String bid(@RequestParam BigInteger value) throws Exception {
        return auctionService.bid(value);
    }

    @PostMapping("/stop")
    public void stopAuction() throws Exception {
        auctionService.stopAuction();
    }
}

