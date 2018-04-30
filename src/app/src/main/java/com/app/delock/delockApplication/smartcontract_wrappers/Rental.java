package com.app.delock.delockApplication.smartcontract_wrappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
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
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class Rental extends Contract {
    private static final String BINARY = "0x60606040526001600355341561001457600080fd5b60405161082f38038061082f83398101604052808051820191906020018051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550826004908051906020019061009b9291906100b2565b508160028190555080600381905550505050610157565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100f357805160ff1916838001178555610121565b82800160010185558215610121579182015b82811115610120578251825591602001919060010190610105565b5b50905061012e9190610132565b5090565b61015491905b80821115610150576000816000905550600101610138565b5090565b90565b6106c9806101666000396000f3006060604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063275a11f9146100bf5780632e084adb146100e85780632e88ab0b1461010b578063419759f5146101605780634e3b62ec146101895780636d8cee56146101e657806382996d9f1461020957806383b6356b146102135780638da5cb5b1461023c578063c623674f14610291578063ceb751da1461031f578063e6c6774514610348575b600080fd5b34156100ca57600080fd5b6100d2610375565b6040518082815260200191505060405180910390f35b34156100f357600080fd5b610109600480803590602001909190505061037b565b005b341561011657600080fd5b61011e6103e0565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561016b57600080fd5b610173610406565b6040518082815260200191505060405180910390f35b341561019457600080fd5b6101e4600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061040c565b005b34156101f157600080fd5b6102076004808035906020019091905050610481565b005b6102116104e6565b005b341561021e57600080fd5b610226610516565b6040518082815260200191505060405180910390f35b341561024757600080fd5b61024f61051c565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561029c57600080fd5b6102a4610541565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102e45780820151818401526020810190506102c9565b50505050905090810190601f1680156103115780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561032a57600080fd5b6103326105df565b6040518082815260200191505060405180910390f35b341561035357600080fd5b61035b6105e5565b604051808215151515815260200191505060405180910390f35b60065481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156103d657600080fd5b8060038190555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60025481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561046757600080fd5b806004908051906020019061047d9291906105f8565b5050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156104dc57600080fd5b8060028190555050565b60001515600560009054906101000a900460ff16151514151561050857600080fd5b60003411151561051457fe5b565b60075481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60048054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105d75780601f106105ac576101008083540402835291602001916105d7565b820191906000526020600020905b8154815290600101906020018083116105ba57829003601f168201915b505050505081565b60035481565b600560009054906101000a900460ff1681565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061063957805160ff1916838001178555610667565b82800160010185558215610667579182015b8281111561066657825182559160200191906001019061064b565b5b5090506106749190610678565b5090565b61069a91905b8082111561069657600081600090555060010161067e565b5090565b905600a165627a7a72305820c4e147dcb2b5ff457c6f15ebfff0d147ffe67133c18dae9cc46bebd3ef793c340029";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
        _addresses.put("1524605103544", "0x5cae53421a9c976b66776b8006fc9933b8366f4b");
    }

    protected Rental(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Rental(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<RentItemEventResponse> getRentItemEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("rentItem", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<RentItemEventResponse> responses = new ArrayList<RentItemEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RentItemEventResponse typedResponse = new RentItemEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._renter = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RentItemEventResponse> rentItemEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("rentItem", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, RentItemEventResponse>() {
            @Override
            public RentItemEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                RentItemEventResponse typedResponse = new RentItemEventResponse();
                typedResponse.log = log;
                typedResponse._renter = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<ReturnItemEventResponse> getReturnItemEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("returnItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<ReturnItemEventResponse> responses = new ArrayList<ReturnItemEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReturnItemEventResponse typedResponse = new ReturnItemEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ReturnItemEventResponse> returnItemEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("returnItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ReturnItemEventResponse>() {
            @Override
            public ReturnItemEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                ReturnItemEventResponse typedResponse = new ReturnItemEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public List<UnlockItemEventResponse> getUnlockItemEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("unlockItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<UnlockItemEventResponse> responses = new ArrayList<UnlockItemEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UnlockItemEventResponse typedResponse = new UnlockItemEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UnlockItemEventResponse> unlockItemEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("unlockItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, UnlockItemEventResponse>() {
            @Override
            public UnlockItemEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                UnlockItemEventResponse typedResponse = new UnlockItemEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public List<LockItemEventResponse> getLockItemEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("lockItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<LockItemEventResponse> responses = new ArrayList<LockItemEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LockItemEventResponse typedResponse = new LockItemEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LockItemEventResponse> lockItemEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("lockItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LockItemEventResponse>() {
            @Override
            public LockItemEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                LockItemEventResponse typedResponse = new LockItemEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public RemoteCall<BigInteger> startDateCurrentRental() {
        final Function function = new Function("startDateCurrentRental", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> renter() {
        final Function function = new Function("renter", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> depositAmount() {
        final Function function = new Function("depositAmount", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> endDateCurrentRental() {
        final Function function = new Function("endDateCurrentRental", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> ipfsHash() {
        final Function function = new Function("ipfsHash", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> pricePerHour() {
        final Function function = new Function("pricePerHour", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> currentlyRented() {
        final Function function = new Function("currentlyRented", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public static RemoteCall<Rental> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _ipfsHash, BigInteger _depositAmount, BigInteger _pricePerHour) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_ipfsHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_depositAmount), 
                new org.web3j.abi.datatypes.generated.Uint256(_pricePerHour)));
        return deployRemoteCall(Rental.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Rental> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _ipfsHash, BigInteger _depositAmount, BigInteger _pricePerHour) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_ipfsHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_depositAmount), 
                new org.web3j.abi.datatypes.generated.Uint256(_pricePerHour)));
        return deployRemoteCall(Rental.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public RemoteCall<TransactionReceipt> setDepositAmount(BigInteger _depositAmount) {
        final Function function = new Function(
                "setDepositAmount", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_depositAmount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setPricePerHour(BigInteger _pricePerHour) {
        final Function function = new Function(
                "setPricePerHour", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_pricePerHour)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setIpfsHash(String _ipfsHash) {
        final Function function = new Function(
                "setIpfsHash", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_ipfsHash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> rent(BigInteger weiValue) {
        final Function function = new Function(
                "rent", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static Rental load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Rental(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Rental load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Rental(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class RentItemEventResponse {
        public Log log;

        public String _renter;
    }

    public static class ReturnItemEventResponse {
        public Log log;
    }

    public static class UnlockItemEventResponse {
        public Log log;
    }

    public static class LockItemEventResponse {
        public Log log;
    }
}
