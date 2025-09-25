// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.16;

contract AuctionPlatform {

    struct Auction {
        string description;
        string imageURI;
        uint basePrice;
        uint secondsToEnd;
        uint createdTime;
        address payable originalOwner;
        address payable highestBidder;
        uint highestPrice;
        bool active;
        bool ended;
    }

    Auction[] public auctions;

    event AuctionCreated(uint auctionId, address creator);
    event NewBid(uint auctionId, address bidder, uint amount);
    event AuctionEnded(uint auctionId, address winner, uint amount);
    event AuctionStopped(uint auctionId);

    modifier onlyOwner(uint auctionId) {
        require(msg.sender == auctions[auctionId].originalOwner, "Not auction owner");
        _;
    }

    function createAuction(
        string memory description,
        string memory imageURI,
        uint basePrice,
        uint secondsToEnd
    ) public {
        Auction memory newAuction = Auction({
            description: description,
            imageURI: imageURI,
            basePrice: basePrice,
            secondsToEnd: secondsToEnd,
            createdTime: block.timestamp,
            originalOwner: payable(msg.sender),
            highestBidder: payable(address(0)),
            highestPrice: 0,
            active: true,
            ended: false
        });

        auctions.push(newAuction);
        emit AuctionCreated(auctions.length - 1, msg.sender);
    }

    function bid(uint auctionId) public payable {
        Auction storage a = auctions[auctionId];
        require(a.active, "Auction is not active");
        require(block.timestamp <= a.createdTime + a.secondsToEnd, "Auction has ended");
        require(msg.value > a.basePrice && msg.value > a.highestPrice, "Bid too low");

        if (a.highestBidder != address(0)) {
            a.highestBidder.transfer(a.highestPrice);
        }

        a.highestBidder = payable(msg.sender);
        a.highestPrice = msg.value;

        emit NewBid(auctionId, msg.sender, msg.value);
    }

    function checkAuctionEnd(uint auctionId) public {
        Auction storage a = auctions[auctionId];
        require(a.active, "Auction not active");
        require(block.timestamp > a.createdTime + a.secondsToEnd, "Auction still ongoing");

        a.active = false;
        a.ended = true;

        if (a.highestPrice > 0) {
            a.originalOwner.transfer(a.highestPrice);
            emit AuctionEnded(auctionId, a.highestBidder, a.highestPrice);
        } else {
            emit AuctionEnded(auctionId, address(0), 0);
        }
    }

    function stopAuction(uint auctionId) public onlyOwner(auctionId) {
        Auction storage a = auctions[auctionId];
        require(a.active, "Already inactive");

        a.active = false;

        if (a.highestBidder != address(0)) {
            a.highestBidder.transfer(a.highestPrice);
        }

        emit AuctionStopped(auctionId);
    }

    function getAuction(uint auctionId) public view returns (
        string memory, string memory, uint, uint, uint, address, address, uint, bool, bool
    ) {
        Auction memory a = auctions[auctionId];
        return (
            a.description,
            a.imageURI,
            a.basePrice,
            a.secondsToEnd,
            a.createdTime,
            a.originalOwner,
            a.highestBidder,
            a.highestPrice,
            a.active,
            a.ended
        );
    }

    function getAllAuctionsCount() public view returns (uint) {
        return auctions.length;
    }
}