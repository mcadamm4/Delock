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
    private static final String BINARY = "0x606060405260006007556000600860006101000a81548160ff0219169083151502179055506000600955341561003457600080fd5b604051610d46380380610d4683398101604052808051820191906020018051906020019091908051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555083600490805190602001906100c49291906100f6565b50826002819055508160038190555080600560006101000a81548160ff0219169083151502179055505050505061019b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061013757805160ff1916838001178555610165565b82800160010185558215610165579182015b82811115610164578251825591602001919060010190610149565b5b5090506101729190610176565b5090565b61019891905b8082111561019457600081600090555060010161017c565b5090565b90565b610b9c806101aa6000396000f3006060604052600436106100e6576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806311093c7e146100eb57806328651f56146101145780632e084adb1461013d5780632e88ab0b146101605780632f195680146101b5578063419759f5146101de57806348a0d754146102075780636d8cee561461023457806382466448146102575780638500d668146102805780638da5cb5b1461028a57806398cbf780146102df578063c0d047f014610304578063ceb751da1461030e578063f273589214610337578063f33145ab14610360575b600080fd5b34156100f657600080fd5b6100fe6103ee565b6040518082815260200191505060405180910390f35b341561011f57600080fd5b6101276103f4565b6040518082815260200191505060405180910390f35b341561014857600080fd5b61015e6004808035906020019091905050610421565b005b341561016b57600080fd5b610173610486565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156101c057600080fd5b6101c86104ac565b6040518082815260200191505060405180910390f35b34156101e957600080fd5b6101f16104b2565b6040518082815260200191505060405180910390f35b341561021257600080fd5b61021a6104b8565b604051808215151515815260200191505060405180910390f35b341561023f57600080fd5b61025560048080359060200190919050506104cb565b005b341561026257600080fd5b61026a610530565b6040518082815260200191505060405180910390f35b610288610686565b005b341561029557600080fd5b61029d610764565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156102ea57600080fd5b61030260048080351515906020019091905050610789565b005b61030c61089e565b005b341561031957600080fd5b610321610a5f565b6040518082815260200191505060405180910390f35b341561034257600080fd5b61034a610a65565b6040518082815260200191505060405180910390f35b341561036b57600080fd5b610373610a6b565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103b3578082015181840152602081019050610398565b50505050905090810190601f1680156103e05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60095481565b6000801515600560009054906101000a900460ff16151514151561041757600080fd5b6006544203905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561047c57600080fd5b8060038190555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60075481565b60025481565b600560009054906101000a900460ff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561052657600080fd5b8060028190555050565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561058e57600080fd5b60001515600560009054906101000a900460ff1615151415156105b057600080fd5b6105b86103f4565b610e106003548115156105c757fe5b0402600981905550600954600254101561062657600254600954036007819055507f013a247f0c2a4d3bc89eb570defb306a211aafe2aac5751697018434ca6f44b36007546040518082815260200191505060405180910390a1610683565b6001600860006101000a81548160ff02191690831515021790555060006007819055507f013a247f0c2a4d3bc89eb570defb306a211aafe2aac5751697018434ca6f44b36007546040518082815260200191505060405180910390a15b90565b60011515600560009054906101000a900460ff1615151415156106a857600080fd5b600254341415156106b557fe5b33600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550426006819055506000600560006101000a81548160ff0219169083151502179055507f26258b43eb956b3e6dd4862303c4618a254af910141490de251447c559bc10cd600560009054906101000a900460ff16604051808215151515815260200191505060405180910390a1565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156107e457600080fd5b80600560006101000a81548160ff0219169083151502179055506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f2f3acd22493fe45d405c9d2f7a8e38d77bbf71ceaaa522610eff5cdf2a33e21281604051808215151515815260200191505060405180910390a150565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156108fa57600080fd5b60001515600560009054906101000a900460ff16151514151561091c57600080fd5b600754341015151561092a57fe5b600860009054906101000a900460ff16156109a857600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc600954600254039081150290604051600060405180830381858888f1935050505015156109a757600080fd5b5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6009549081150290604051600060405180830381858888f193505050501515610a0b57600080fd5b610a13610b09565b7f4f1d9293f528eadf996e32a08000fadf9714360bdb24f9fc42e2b5daa12f6d16600560009054906101000a900460ff16604051808215151515815260200191505060405180910390a1565b60035481565b60065481565b60048054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610b015780601f10610ad657610100808354040283529160200191610b01565b820191906000526020600020905b815481529060010190602001808311610ae457829003601f168201915b505050505081565b60006009819055506001600560006101000a81548160ff0219169083151502179055506000600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505600a165627a7a72305820f86b85309db370f03f3e5709760df05cd9ed625c1d9fb98dcba8fcfcd4373e820029";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
    }

    protected Rental(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Rental(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<Event_OwnerSetAvailableEventResponse> getEvent_OwnerSetAvailableEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("event_OwnerSetAvailable", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<Event_OwnerSetAvailableEventResponse> responses = new ArrayList<Event_OwnerSetAvailableEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Event_OwnerSetAvailableEventResponse typedResponse = new Event_OwnerSetAvailableEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._available = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<Event_OwnerSetAvailableEventResponse> event_OwnerSetAvailableEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("event_OwnerSetAvailable", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, Event_OwnerSetAvailableEventResponse>() {
            @Override
            public Event_OwnerSetAvailableEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                Event_OwnerSetAvailableEventResponse typedResponse = new Event_OwnerSetAvailableEventResponse();
                typedResponse.log = log;
                typedResponse._available = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<Event_rentItemEventResponse> getEvent_rentItemEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("event_rentItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<Event_rentItemEventResponse> responses = new ArrayList<Event_rentItemEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Event_rentItemEventResponse typedResponse = new Event_rentItemEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._available = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<Event_rentItemEventResponse> event_rentItemEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("event_rentItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, Event_rentItemEventResponse>() {
            @Override
            public Event_rentItemEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                Event_rentItemEventResponse typedResponse = new Event_rentItemEventResponse();
                typedResponse.log = log;
                typedResponse._available = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<Event_CostCalculationEventResponse> getEvent_CostCalculationEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("event_CostCalculation", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<Event_CostCalculationEventResponse> responses = new ArrayList<Event_CostCalculationEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Event_CostCalculationEventResponse typedResponse = new Event_CostCalculationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._totalCostOfRental = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<Event_CostCalculationEventResponse> event_CostCalculationEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("event_CostCalculation", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, Event_CostCalculationEventResponse>() {
            @Override
            public Event_CostCalculationEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                Event_CostCalculationEventResponse typedResponse = new Event_CostCalculationEventResponse();
                typedResponse.log = log;
                typedResponse._totalCostOfRental = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<Event_returnItemEventResponse> getEvent_returnItemEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("event_returnItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<Event_returnItemEventResponse> responses = new ArrayList<Event_returnItemEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Event_returnItemEventResponse typedResponse = new Event_returnItemEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._available = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<Event_returnItemEventResponse> event_returnItemEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("event_returnItem", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, Event_returnItemEventResponse>() {
            @Override
            public Event_returnItemEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                Event_returnItemEventResponse typedResponse = new Event_returnItemEventResponse();
                typedResponse.log = log;
                typedResponse._available = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<BigInteger> total_CostOfRental() {
        final Function function = new Function("total_CostOfRental", 
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

    public RemoteCall<BigInteger> amountDue() {
        final Function function = new Function("amountDue", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteCall<BigInteger> rental_StartTime() {
        final Function function = new Function("rental_StartTime", 
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

    public static RemoteCall<Rental> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _ipfsHash, BigInteger _depositAmount, BigInteger _pricePerHour, Boolean _available) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_ipfsHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_depositAmount), 
                new org.web3j.abi.datatypes.generated.Uint256(_pricePerHour), 
                new org.web3j.abi.datatypes.Bool(_available)));
        return deployRemoteCall(Rental.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Rental> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _ipfsHash, BigInteger _depositAmount, BigInteger _pricePerHour, Boolean _available) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_ipfsHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_depositAmount), 
                new org.web3j.abi.datatypes.generated.Uint256(_pricePerHour), 
                new org.web3j.abi.datatypes.Bool(_available)));
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

    public RemoteCall<TransactionReceipt> ownerSetAvailable(Boolean _available) {
        final Function function = new Function(
                "ownerSetAvailable", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_available)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> rentItem(BigInteger weiValue) {
        final Function function = new Function(
                "rentItem", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> calcElapsedTime() {
        final Function function = new Function("calcElapsedTime", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> calcTotalCostOfRental() {
        final Function function = new Function(
                "calcTotalCostOfRental", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> returnItem(BigInteger weiValue) {
        final Function function = new Function(
                "returnItem", 
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

    public static class Event_OwnerSetAvailableEventResponse {
        public Log log;

        public Boolean _available;
    }

    public static class Event_rentItemEventResponse {
        public Log log;

        public Boolean _available;
    }

    public static class Event_CostCalculationEventResponse {
        public Log log;

        public BigInteger _totalCostOfRental;
    }

    public static class Event_returnItemEventResponse {
        public Log log;

        public Boolean _available;
    }
}
