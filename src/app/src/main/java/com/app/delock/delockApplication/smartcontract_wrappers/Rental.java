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
    private static final String BINARY = "0x6060604052341561000f57600080fd5b6040516105a43803806105a483398101604052808051820191906020018051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600490805190602001906100969291906100ad565b508160028190555080600381905550505050610152565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100ee57805160ff191683800117855561011c565b8280016001018555821561011c579182015b8281111561011b578251825591602001919060010190610100565b5b509050610129919061012d565b5090565b61014f91905b8082111561014b576000816000905550600101610133565b5090565b90565b610443806101616000396000f300606060405260043610610083576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680632e084adb146100885780632e88ab0b146100ab578063419759f5146101005780636d8cee56146101295780638da5cb5b1461014c578063ceb751da146101a1578063f33145ab146101ca575b600080fd5b341561009357600080fd5b6100a96004808035906020019091905050610258565b005b34156100b657600080fd5b6100be6102bd565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561010b57600080fd5b6101136102e3565b6040518082815260200191505060405180910390f35b341561013457600080fd5b61014a60048080359060200190919050506102e9565b005b341561015757600080fd5b61015f61034e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156101ac57600080fd5b6101b4610373565b6040518082815260200191505060405180910390f35b34156101d557600080fd5b6101dd610379565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561021d578082015181840152602081019050610202565b50505050905090810190601f16801561024a5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156102b357600080fd5b8060038190555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60025481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561034457600080fd5b8060028190555050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60035481565b60048054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561040f5780601f106103e45761010080835404028352916020019161040f565b820191906000526020600020905b8154815290600101906020018083116103f257829003601f168201915b5050505050815600a165627a7a723058206b32c55339e58927f2f5fc4534839592bb305e94af5b149581068fd26824bcb60029";

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
