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
    private static final String BINARY = "0x6060604052341561000f57600080fd5b60405161068138038061068183398101604052808051820191906020018051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600490805190602001906100969291906100ad565b508160028190555080600381905550505050610152565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100ee57805160ff191683800117855561011c565b8280016001018555821561011c579182015b8281111561011b578251825591602001919060010190610100565b5b509050610129919061012d565b5090565b61014f91905b8082111561014b576000816000905550600101610133565b5090565b90565b610520806101616000396000f3006060604052600436106100a4576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680632e084adb146100a95780632e88ab0b146100cc578063419759f51461012157806348a0d7541461014a5780636d8cee561461017757806382996d9f1461019a5780638da5cb5b146101a4578063ceb751da146101f9578063e2df20d114610222578063f33145ab14610247575b600080fd5b34156100b457600080fd5b6100ca60048080359060200190919050506102d5565b005b34156100d757600080fd5b6100df61033a565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561012c57600080fd5b610134610360565b6040518082815260200191505060405180910390f35b341561015557600080fd5b61015d610366565b604051808215151515815260200191505060405180910390f35b341561018257600080fd5b6101986004808035906020019091905050610379565b005b6101a26103de565b005b34156101af57600080fd5b6101b761040e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561020457600080fd5b61020c610433565b6040518082815260200191505060405180910390f35b341561022d57600080fd5b61024560048080351515906020019091905050610439565b005b341561025257600080fd5b61025a610456565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561029a57808201518184015260208101905061027f565b50505050905090810190601f1680156102c75780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561033057600080fd5b8060038190555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60025481565b600560009054906101000a900460ff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156103d457600080fd5b8060028190555050565b60011515600560009054906101000a900460ff16151514151561040057600080fd5b60003411151561040c57fe5b565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60035481565b80600560006101000a81548160ff02191690831515021790555050565b60048054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104ec5780601f106104c1576101008083540402835291602001916104ec565b820191906000526020600020905b8154815290600101906020018083116104cf57829003601f168201915b5050505050815600a165627a7a7230582049ba042d60484a3dc0197aa389f0cfb2ce4daf3b1f502f6b2f9a00af04ca01d70029";

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

    public RemoteCall<Boolean> available() {
        final Function function = new Function("available", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> pricePerHour() {
        final Function function = new Function("pricePerHour", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> ipfsHashes() {
        final Function function = new Function("ipfsHashes", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<TransactionReceipt> setAvailable(Boolean _available) {
        final Function function = new Function(
                "setAvailable", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_available)), 
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
