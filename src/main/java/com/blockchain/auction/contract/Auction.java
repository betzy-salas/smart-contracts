package com.blockchain.auction.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.14.0.
 */
@SuppressWarnings("rawtypes")
public class Auction extends Contract {
    public static final String BINARY = "608060405234801561000f575f5ffd5b50604051806080016040528060448152602001610b0c604491395f906100359082610187565b50604051806080016040528060598152602001610b506059913960019061005c9082610187565b50662386f26fc100006002556102586003556009805460ff1916600117905542600455600580546001600160a01b031916331790556040517f2844c95bf1b4598da931d527f903501abc60fe0199c65da52d5ce818c6c5e961906100e2906020808252600e908201526d537562617374612063726561646160901b604082015260600190565b60405180910390a1610241565b634e487b7160e01b5f52604160045260245ffd5b600181811c9082168061011757607f821691505b60208210810361013557634e487b7160e01b5f52602260045260245ffd5b50919050565b601f82111561018257805f5260205f20601f840160051c810160208510156101605750805b601f840160051c820191505b8181101561017f575f815560010161016c565b50505b505050565b81516001600160401b038111156101a0576101a06100ef565b6101b4816101ae8454610103565b8461013b565b6020601f8211600181146101e6575f83156101cf5750848201515b5f19600385901b1c1916600184901b17845561017f565b5f84815260208120601f198516915b8281101561021557878501518255602094850194600190920191016101f5565b508482101561023257868401515f19600387901b60f8161c191681555b50505050600190811b01905550565b6108be8061024e5f395ff3fe6080604052600436106100d8575f3560e01c8063b49f4afd1161007c578063d4ee1d9011610057578063d4ee1d90146101f1578063d94a350514610210578063ded7222b14610233578063f1fffdcb14610247575f5ffd5b8063b49f4afd146101b5578063bc6f1f1b146101c9578063cf1f2974146101dd575f5ffd5b80631a092541116100b75780631a0925411461013f57806322f3e2d414610160578063269b9a081461018257806391f9015714610196575f5ffd5b80623495a4146100dc5780630e8670e0146101125780631998aeef14610135575b5f5ffd5b3480156100e7575f5ffd5b506007546001600160a01b03165b6040516001600160a01b0390911681526020015b60405180910390f35b34801561011d575f5ffd5b5061012760085481565b604051908152602001610109565b61013d610266565b005b34801561014a575f5ffd5b506101536103e7565b604051610109919061079a565b34801561016b575f5ffd5b5060095460ff166040519015158152602001610109565b34801561018d575f5ffd5b5061013d610476565b3480156101a1575f5ffd5b506007546100f5906001600160a01b031681565b3480156101c0575f5ffd5b50600254610127565b3480156101d4575f5ffd5b50600854610127565b3480156101e8575f5ffd5b5061013d61058d565b3480156101fc575f5ffd5b506006546100f5906001600160a01b031681565b34801561021b575f5ffd5b506102246106bd565b604051610109939291906107b3565b34801561023e575f5ffd5b5061015361075d565b348015610252575f5ffd5b506005546100f5906001600160a01b031681565b60035460045461027691906107d7565b4211801561028b575060095460ff1615156001145b1561029a5761029861058d565b565b600854341180156102ac575060025434115b15610391576007546008546040516001600160a01b039092169181156108fc0291905f818181858888f193505050501580156102ea573d5f5f3e3d5ffd5b50600780546001600160a01b03191633179055346008556040517f2844c95bf1b4598da931d527f903501abc60fe0199c65da52d5ce818c6c5e96190610387906020808252603f908201527f4e756576612070756a61206d617320616c74612c20656c20756c74696d6f207060408201527f6f73746f72207469656e652073752064696e65726f206465207675656c746100606082015260800190565b60405180910390a1565b7f2844c95bf1b4598da931d527f903501abc60fe0199c65da52d5ce818c6c5e9616040516103be906107fc565b60405180910390a160405162461bcd60e51b81526004016103de906107fc565b60405180910390fd5b60605f80546103f590610850565b80601f016020809104026020016040519081016040528092919081815260200182805461042190610850565b801561046c5780601f106104435761010080835404028352916020019161046c565b820191905f5260205f20905b81548152906001019060200180831161044f57829003601f168201915b5050505050905090565b6005546001600160a01b031633146104d05760405162461bcd60e51b815260206004820152601e60248201527f596f75206d75737420626520746865206f726967696e616c204f574e4552000060448201526064016103de565b6009805460ff191690556007546001600160a01b031615610526576007546008546040516001600160a01b039092169181156108fc0291905f818181858888f19350505050158015610524573d5f5f3e3d5ffd5b505b604080518181526017818301527f4c6120737562617374612073652068612070617261646f000000000000000000606082015233602082015290517fbb9721a0a3c06497a88ed083ae73d825db6e63e077545032e06a18fecb27d8ab9181900360800190a1565b60035460045461059d91906107d7565b42111561067c576009805460ff19169055600754600680546001600160a01b0319166001600160a01b03928316179055600554600854604051919092169180156108fc02915f818181858888f193505050501580156105fe573d5f5f3e3d5ffd5b50600754604080518181526021918101919091527f456c2067616e61646f72206465206c612073756261737461206861207369646f6060820152601d60f91b60808201526001600160a01b0390911660208201527fbb9721a0a3c06497a88ed083ae73d825db6e63e077545032e06a18fecb27d8ab9060a001610387565b60405162461bcd60e51b81526020600482015260166024820152754c61207375626173746120657374612061637469766160501b60448201526064016103de565b60605f5f5f6004546003548280546106d490610850565b80601f016020809104026020016040519081016040528092919081815260200182805461070090610850565b801561074b5780601f106107225761010080835404028352916020019161074b565b820191905f5260205f20905b81548152906001019060200180831161072e57829003601f168201915b50505050509250925092509250909192565b6060600180546103f590610850565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b602081525f6107ac602083018461076c565b9392505050565b606081525f6107c5606083018661076c565b60208301949094525060400152919050565b808201808211156107f657634e487b7160e01b5f52601160045260245ffd5b92915050565b60208082526034908201527f4c612070756a61206e6f20657320706f7369626c652c206e6f206573206c6f20604082015273737566696369656e74656d656e746520616c746160601b606082015260800190565b600181811c9082168061086457607f821691505b60208210810361088257634e487b7160e01b5f52602260045260245ffd5b5091905056fea26469706673582212200c049a0774bb2399ff8bbade8b6717da1b2be65cea18f33a5f108bd4f5527f9364736f6c634300081d0033456e20657374612073756261737461207365206f667265636520756e20636f6368652e20536520747261746120646520756e20466f726420466f637573206465202e2e2e68747470733a2f2f62616679626569667a6d367871647577676c366c776a7961626a327635717764757771676f747236686a6a3563753633326c647475367a627734612e697066732e6e667473746f726167652e6c696e6b2f";

    private static String librariesLinkedBinary;

    public static final String FUNC_BID = "bid";

    public static final String FUNC_CHECKIFAUCTIONENDED = "checkIfAuctionEnded";

    public static final String FUNC_GETAUCTIONINFO = "getAuctionInfo";

    public static final String FUNC_GETBASEPRICE = "getBasePrice";

    public static final String FUNC_GETDESCRIPTION = "getDescription";

    public static final String FUNC_GETHIGHESTBIDDER = "getHighestBidder";

    public static final String FUNC_GETHIGHESTPRICE = "getHighestPrice";

    public static final String FUNC_GETIMAGEURI = "getImageURI";

    public static final String FUNC_HIGHESTBIDDER = "highestBidder";

    public static final String FUNC_HIGHESTPRICE = "highestPrice";

    public static final String FUNC_ISACTIVE = "isActive";

    public static final String FUNC_NEWOWNER = "newOwner";

    public static final String FUNC_ORIGINALOWNER = "originalOwner";

    public static final String FUNC_STOPAUCTION = "stopAuction";

    public static final Event RESULT_EVENT = new Event("Result", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event STATUS_EVENT = new Event("Status", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Auction(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Auction(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Auction(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Auction(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ResultEventResponse> getResultEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RESULT_EVENT, transactionReceipt);
        ArrayList<ResultEventResponse> responses = new ArrayList<ResultEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ResultEventResponse typedResponse = new ResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._message = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.winner = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ResultEventResponse getResultEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(RESULT_EVENT, log);
        ResultEventResponse typedResponse = new ResultEventResponse();
        typedResponse.log = log;
        typedResponse._message = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.winner = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ResultEventResponse> resultEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getResultEventFromLog(log));
    }

    public Flowable<ResultEventResponse> resultEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RESULT_EVENT));
        return resultEventFlowable(filter);
    }

    public static List<StatusEventResponse> getStatusEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(STATUS_EVENT, transactionReceipt);
        ArrayList<StatusEventResponse> responses = new ArrayList<StatusEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StatusEventResponse typedResponse = new StatusEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._message = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static StatusEventResponse getStatusEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(STATUS_EVENT, log);
        StatusEventResponse typedResponse = new StatusEventResponse();
        typedResponse.log = log;
        typedResponse._message = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<StatusEventResponse> statusEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getStatusEventFromLog(log));
    }

    public Flowable<StatusEventResponse> statusEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STATUS_EVENT));
        return statusEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> bid(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> checkIfAuctionEnded() {
        final Function function = new Function(
                FUNC_CHECKIFAUCTIONENDED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>> getAuctionInfo() {
        final Function function = new Function(FUNC_GETAUCTIONINFO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getBasePrice() {
        final Function function = new Function(FUNC_GETBASEPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getDescription() {
        final Function function = new Function(FUNC_GETDESCRIPTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getHighestBidder() {
        final Function function = new Function(FUNC_GETHIGHESTBIDDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getHighestPrice() {
        final Function function = new Function(FUNC_GETHIGHESTPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getImageURI() {
        final Function function = new Function(FUNC_GETIMAGEURI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> highestBidder() {
        final Function function = new Function(FUNC_HIGHESTBIDDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> highestPrice() {
        final Function function = new Function(FUNC_HIGHESTPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isActive() {
        final Function function = new Function(FUNC_ISACTIVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> newOwner() {
        final Function function = new Function(FUNC_NEWOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> originalOwner() {
        final Function function = new Function(FUNC_ORIGINALOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> stopAuction() {
        final Function function = new Function(
                FUNC_STOPAUCTION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Auction load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Auction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Auction load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Auction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Auction load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Auction(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Auction load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Auction(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Auction> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Auction.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<Auction> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Auction.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Auction> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Auction.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Auction> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Auction.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ResultEventResponse extends BaseEventResponse {
        public String _message;

        public String winner;
    }

    public static class StatusEventResponse extends BaseEventResponse {
        public String _message;
    }
}
