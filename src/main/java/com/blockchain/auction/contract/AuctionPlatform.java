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
import org.web3j.tuples.generated.Tuple10;
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
public class AuctionPlatform extends Contract {
    public static final String BINARY = "6080604052348015600e575f5ffd5b50610f518061001c5f395ff3fe60806040526004361061006e575f3560e01c8063571a26a01161004c578063571a26a0146100c757806361e2db55146100fc57806378bd79351461011b578063f510f6ae1461013a575f5ffd5b8063454a2ab314610072578063460154c014610087578063565f662c146100a8575b5f5ffd5b610085610080366004610bb8565b610159565b005b348015610092575f5ffd5b505f546040519081526020015b60405180910390f35b3480156100b3575f5ffd5b506100856100c2366004610bb8565b61032b565b3480156100d2575f5ffd5b506100e66100e1366004610bb8565b6104da565b60405161009f9a99989796959493929190610bfd565b348015610107575f5ffd5b50610085610116366004610d14565b610659565b348015610126575f5ffd5b506100e6610135366004610bb8565b6107e0565b348015610145575f5ffd5b50610085610154366004610bb8565b610a45565b5f5f828154811061016c5761016c610d88565b5f9182526020909120600990910201600881015490915060ff166101cf5760405162461bcd60e51b815260206004820152601560248201527441756374696f6e206973206e6f742061637469766560581b60448201526064015b60405180910390fd5b806003015481600401546101e39190610db0565b4211156102265760405162461bcd60e51b8152602060048201526011602482015270105d58dd1a5bdb881a185cc8195b991959607a1b60448201526064016101c6565b80600201543411801561023c5750806007015434115b6102765760405162461bcd60e51b815260206004820152600b60248201526a42696420746f6f206c6f7760a81b60448201526064016101c6565b60068101546001600160a01b0316156102c857600681015460078201546040516001600160a01b039092169181156108fc0291905f818181858888f193505050501580156102c6573d5f5f3e3d5ffd5b505b6006810180546001600160a01b0319163390811790915534600783018190556040805185815260208101939093528201527f558a0d5d5468d74b0db24c74eb348b42271c2ebb4c9e953ced38aaed95fa4361906060015b60405180910390a15050565b5f5f828154811061033e5761033e610d88565b5f9182526020909120600990910201600881015490915060ff166103995760405162461bcd60e51b815260206004820152601260248201527141756374696f6e206e6f742061637469766560701b60448201526064016101c6565b806003015481600401546103ad9190610db0565b42116103f35760405162461bcd60e51b815260206004820152601560248201527441756374696f6e207374696c6c206f6e676f696e6760581b60448201526064016101c6565b60088101805461ffff191661010017905560078101541561049a57600581015460078201546040516001600160a01b039092169181156108fc0291905f818181858888f1935050505015801561044b573d5f5f3e3d5ffd5b5060068101546007820154604080518581526001600160a01b0390931660208401528201527fd2aa34a4fdbbc6dff6a3e56f46e0f3ae2a31d7785ff3487aa5c95c642acea5019060600161031f565b604080518381525f60208201819052918101919091527fd2aa34a4fdbbc6dff6a3e56f46e0f3ae2a31d7785ff3487aa5c95c642acea5019060600161031f565b5f81815481106104e8575f80fd5b905f5260205f2090600902015f91509050805f01805461050790610dc9565b80601f016020809104026020016040519081016040528092919081815260200182805461053390610dc9565b801561057e5780601f106105555761010080835404028352916020019161057e565b820191905f5260205f20905b81548152906001019060200180831161056157829003601f168201915b50505050509080600101805461059390610dc9565b80601f01602080910402602001604051908101604052809291908181526020018280546105bf90610dc9565b801561060a5780601f106105e15761010080835404028352916020019161060a565b820191905f5260205f20905b8154815290600101906020018083116105ed57829003601f168201915b505050600284015460038501546004860154600587015460068801546007890154600890990154979894979396509194506001600160a01b039081169391169160ff808216916101009004168a565b604080516101408101825285815260208101859052908101839052606081018290524260808201523360a08201525f60c0820181905260e0820181905260016101008301819052610120830182905281549081018255908052815182916009027f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563019081906106e89082610e4d565b50602082015160018201906106fd9082610e4d565b5060408201516002820155606082015160038201556080820151600482015560a08201516005820180546001600160a01b039283166001600160a01b03199182161790915560c084015160068401805491909316911617905560e08201516007820155610100808301516008909201805461012090940151151590910261ff00199215159290921661ffff19909316929092171790555f547f5d551e2a2cc977fd8c530317059b4f2d9f504fb82f7dfad736f8d56679bcdfd0906107c390600190610f08565b604080519182523360208301520160405180910390a15050505050565b6060805f5f5f5f5f5f5f5f5f5f8c815481106107fe576107fe610d88565b905f5260205f209060090201604051806101400160405290815f8201805461082590610dc9565b80601f016020809104026020016040519081016040528092919081815260200182805461085190610dc9565b801561089c5780601f106108735761010080835404028352916020019161089c565b820191905f5260205f20905b81548152906001019060200180831161087f57829003601f168201915b505050505081526020016001820180546108b590610dc9565b80601f01602080910402602001604051908101604052809291908181526020018280546108e190610dc9565b801561092c5780601f106109035761010080835404028352916020019161092c565b820191905f5260205f20905b81548152906001019060200180831161090f57829003601f168201915b50505050508152602001600282015481526020016003820154815260200160048201548152602001600582015f9054906101000a90046001600160a01b03166001600160a01b03166001600160a01b03168152602001600682015f9054906101000a90046001600160a01b03166001600160a01b03166001600160a01b0316815260200160078201548152602001600882015f9054906101000a900460ff161515151581526020016008820160019054906101000a900460ff1615151515815250509050805f015181602001518260400151836060015184608001518560a001518660c001518760e001518861010001518961012001519a509a509a509a509a509a509a509a509a509a50509193959799509193959799565b805f8181548110610a5857610a58610d88565b5f9182526020909120600990910201600501546001600160a01b03163314610ab65760405162461bcd60e51b81526020600482015260116024820152702737ba1030bab1ba34b7b71037bbb732b960791b60448201526064016101c6565b5f5f8381548110610ac957610ac9610d88565b5f9182526020909120600990910201600881015490915060ff16610b225760405162461bcd60e51b815260206004820152601060248201526f416c726561647920696e61637469766560801b60448201526064016101c6565b60088101805460ff1916905560068101546001600160a01b031615610b8057600681015460078201546040516001600160a01b039092169181156108fc0291905f818181858888f19350505050158015610b7e573d5f5f3e3d5ffd5b505b6040518381527ff0968c9745ca5ed62dfb850fef20f125b146d3460dbbfeaab73e47be81ea5b619060200160405180910390a1505050565b5f60208284031215610bc8575f5ffd5b5035919050565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b61014081525f610c1161014083018d610bcf565b8281036020840152610c23818d610bcf565b604084019b909b525050606081019790975260808701959095526001600160a01b0393841660a08701529190921660c085015260e0840191909152151561010083015215156101209091015292915050565b634e487b7160e01b5f52604160045260245ffd5b5f82601f830112610c98575f5ffd5b813567ffffffffffffffff811115610cb257610cb2610c75565b604051601f8201601f19908116603f0116810167ffffffffffffffff81118282101715610ce157610ce1610c75565b604052818152838201602001851015610cf8575f5ffd5b816020850160208301375f918101602001919091529392505050565b5f5f5f5f60808587031215610d27575f5ffd5b843567ffffffffffffffff811115610d3d575f5ffd5b610d4987828801610c89565b945050602085013567ffffffffffffffff811115610d65575f5ffd5b610d7187828801610c89565b949794965050505060408301359260600135919050565b634e487b7160e01b5f52603260045260245ffd5b634e487b7160e01b5f52601160045260245ffd5b80820180821115610dc357610dc3610d9c565b92915050565b600181811c90821680610ddd57607f821691505b602082108103610dfb57634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115610e4857805f5260205f20601f840160051c81016020851015610e265750805b601f840160051c820191505b81811015610e45575f8155600101610e32565b50505b505050565b815167ffffffffffffffff811115610e6757610e67610c75565b610e7b81610e758454610dc9565b84610e01565b6020601f821160018114610ead575f8315610e965750848201515b5f19600385901b1c1916600184901b178455610e45565b5f84815260208120601f198516915b82811015610edc5787850151825560209485019460019092019101610ebc565b5084821015610ef957868401515f19600387901b60f8161c191681555b50505050600190811b01905550565b81810381811115610dc357610dc3610d9c56fea2646970667358221220e5b88caadc969016e8ace0e2d96f4b696ab4d1957c48d648788bd1770aab801464736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_AUCTIONS = "auctions";

    public static final String FUNC_BID = "bid";

    public static final String FUNC_CHECKAUCTIONEND = "checkAuctionEnd";

    public static final String FUNC_CREATEAUCTION = "createAuction";

    public static final String FUNC_GETALLAUCTIONSCOUNT = "getAllAuctionsCount";

    public static final String FUNC_GETAUCTION = "getAuction";

    public static final String FUNC_STOPAUCTION = "stopAuction";

    public static final Event AUCTIONCREATED_EVENT = new Event("AuctionCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event AUCTIONENDED_EVENT = new Event("AuctionEnded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONSTOPPED_EVENT = new Event("AuctionStopped", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event NEWBID_EVENT = new Event("NewBid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected AuctionPlatform(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AuctionPlatform(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AuctionPlatform(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AuctionPlatform(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<AuctionCreatedEventResponse> getAuctionCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(AUCTIONCREATED_EVENT, transactionReceipt);
        ArrayList<AuctionCreatedEventResponse> responses = new ArrayList<AuctionCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.creator = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AuctionCreatedEventResponse getAuctionCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(AUCTIONCREATED_EVENT, log);
        AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.creator = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAuctionCreatedEventFromLog(log));
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONCREATED_EVENT));
        return auctionCreatedEventFlowable(filter);
    }

    public static List<AuctionEndedEventResponse> getAuctionEndedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(AUCTIONENDED_EVENT, transactionReceipt);
        ArrayList<AuctionEndedEventResponse> responses = new ArrayList<AuctionEndedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.winner = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AuctionEndedEventResponse getAuctionEndedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(AUCTIONENDED_EVENT, log);
        AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
        typedResponse.log = log;
        typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.winner = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAuctionEndedEventFromLog(log));
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONENDED_EVENT));
        return auctionEndedEventFlowable(filter);
    }

    public static List<AuctionStoppedEventResponse> getAuctionStoppedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(AUCTIONSTOPPED_EVENT, transactionReceipt);
        ArrayList<AuctionStoppedEventResponse> responses = new ArrayList<AuctionStoppedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionStoppedEventResponse typedResponse = new AuctionStoppedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AuctionStoppedEventResponse getAuctionStoppedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(AUCTIONSTOPPED_EVENT, log);
        AuctionStoppedEventResponse typedResponse = new AuctionStoppedEventResponse();
        typedResponse.log = log;
        typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<AuctionStoppedEventResponse> auctionStoppedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAuctionStoppedEventFromLog(log));
    }

    public Flowable<AuctionStoppedEventResponse> auctionStoppedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONSTOPPED_EVENT));
        return auctionStoppedEventFlowable(filter);
    }

    public static List<NewBidEventResponse> getNewBidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWBID_EVENT, transactionReceipt);
        ArrayList<NewBidEventResponse> responses = new ArrayList<NewBidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewBidEventResponse typedResponse = new NewBidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewBidEventResponse getNewBidEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWBID_EVENT, log);
        NewBidEventResponse typedResponse = new NewBidEventResponse();
        typedResponse.log = log;
        typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<NewBidEventResponse> newBidEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewBidEventFromLog(log));
    }

    public Flowable<NewBidEventResponse> newBidEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWBID_EVENT));
        return newBidEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean>> auctions(
            BigInteger param0) {
        final Function function = new Function(FUNC_AUCTIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean>>(function,
                new Callable<Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean>>() {
                    @Override
                    public Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (Boolean) results.get(8).getValue(), 
                                (Boolean) results.get(9).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> bid(BigInteger auctionId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(auctionId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> checkAuctionEnd(BigInteger auctionId) {
        final Function function = new Function(
                FUNC_CHECKAUCTIONEND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(auctionId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createAuction(String description, String imageURI,
            BigInteger basePrice, BigInteger secondsToEnd) {
        final Function function = new Function(
                FUNC_CREATEAUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(description), 
                new org.web3j.abi.datatypes.Utf8String(imageURI), 
                new org.web3j.abi.datatypes.generated.Uint256(basePrice), 
                new org.web3j.abi.datatypes.generated.Uint256(secondsToEnd)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getAllAuctionsCount() {
        final Function function = new Function(FUNC_GETALLAUCTIONSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean>> getAuction(
            BigInteger auctionId) {
        final Function function = new Function(FUNC_GETAUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(auctionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean>>(function,
                new Callable<Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean>>() {
                    @Override
                    public Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<String, String, BigInteger, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (Boolean) results.get(8).getValue(), 
                                (Boolean) results.get(9).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> stopAuction(BigInteger auctionId) {
        final Function function = new Function(
                FUNC_STOPAUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(auctionId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static AuctionPlatform load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionPlatform(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AuctionPlatform load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionPlatform(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AuctionPlatform load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new AuctionPlatform(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AuctionPlatform load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AuctionPlatform(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AuctionPlatform> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuctionPlatform.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<AuctionPlatform> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuctionPlatform.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<AuctionPlatform> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuctionPlatform.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<AuctionPlatform> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuctionPlatform.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

    public static class AuctionCreatedEventResponse extends BaseEventResponse {
        public BigInteger auctionId;

        public String creator;
    }

    public static class AuctionEndedEventResponse extends BaseEventResponse {
        public BigInteger auctionId;

        public String winner;

        public BigInteger amount;
    }

    public static class AuctionStoppedEventResponse extends BaseEventResponse {
        public BigInteger auctionId;
    }

    public static class NewBidEventResponse extends BaseEventResponse {
        public BigInteger auctionId;

        public String bidder;

        public BigInteger amount;
    }
}
