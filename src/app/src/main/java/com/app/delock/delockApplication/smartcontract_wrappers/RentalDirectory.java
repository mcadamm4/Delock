package com.app.delock.delockApplication.smartcontract_wrappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class RentalDirectory extends Contract {
    private static final String BINARY = "0x60606040526000600255341561001457600080fd5b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555061051f806100636000396000f300606060405260043610610083576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680631b978d54146100885780631e0e3925146100e057806346ecfe3214610109578063799e6d391461011e57806383967b521461016b5780638da5cb5b146101c3578063af94a1d114610218575b600080fd5b341561009357600080fd5b6100de600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061027b565b005b34156100eb57600080fd5b6100f36102d9565b6040518082815260200191505060405180910390f35b341561011457600080fd5b61011c6102df565b005b341561012957600080fd5b610155600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506102e9565b6040518082815260200191505060405180910390f35b341561017657600080fd5b6101c1600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506103e0565b005b34156101ce57600080fd5b6101d661043e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561022357600080fd5b6102396004808035906020019091905050610463565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b8073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167fb0c32111a41d2d5e298c12e6644133e97db44381dc92eb511f1bb0aebcf54bd160405160405180910390a35050565b60025481565b6000600281905550565b60006001805490506002541415610313576001808181805490500191508161031191906104a2565b505b816001600260008154809291906001019190505581548110151561033357fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fb0c32111a41d2d5e298c12e6644133e97db44381dc92eb511f1bb0aebcf54bd160405160405180910390a36002549050919050565b8073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167f05ad6d5a3381a5fdf828ae6b18a674ce918fdc3d2d0bb9a3b92a354405e1193b60405160405180910390a35050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60018181548110151561047257fe5b90600052602060002090016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b8154818355818115116104c9578183600052602060002091820191016104c891906104ce565b5b505050565b6104f091905b808211156104ec5760008160009055506001016104d4565b5090565b905600a165627a7a723058201965005fceaf43fb50dd1344fe24b723698fc135ca70900df75dbcab77992eb20029";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
    }

    protected RentalDirectory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RentalDirectory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<Event_NewRentalEventResponse> getEvent_NewRentalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("event_NewRental", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<Event_NewRentalEventResponse> responses = new ArrayList<Event_NewRentalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Event_NewRentalEventResponse typedResponse = new Event_NewRentalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._rentalOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._address = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<Event_NewRentalEventResponse> event_NewRentalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("event_NewRental", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, Event_NewRentalEventResponse>() {
            @Override
            public Event_NewRentalEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                Event_NewRentalEventResponse typedResponse = new Event_NewRentalEventResponse();
                typedResponse.log = log;
                typedResponse._rentalOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._address = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<Event_ReturnEventResponse> getEvent_ReturnEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("event_Return", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<Event_ReturnEventResponse> responses = new ArrayList<Event_ReturnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Event_ReturnEventResponse typedResponse = new Event_ReturnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._rentalOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._address = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<Event_ReturnEventResponse> event_ReturnEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("event_Return", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, Event_ReturnEventResponse>() {
            @Override
            public Event_ReturnEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                Event_ReturnEventResponse typedResponse = new Event_ReturnEventResponse();
                typedResponse.log = log;
                typedResponse._rentalOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._address = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<BigInteger> numElements() {
        final Function function = new Function("numElements", 
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

    public RemoteCall<String> rentals(BigInteger param0) {
        final Function function = new Function("rentals", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<RentalDirectory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RentalDirectory.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<RentalDirectory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RentalDirectory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteCall<TransactionReceipt> addNewRental(String newRentalAddress) {
        final Function function = new Function(
                "addNewRental", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newRentalAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> clearRentals() {
        final Function function = new Function(
                "clearRentals", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> triggerRentalEvent(String _rentalOwner, String _rentalAddress) {
        final Function function = new Function(
                "triggerRentalEvent", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_rentalOwner), 
                new org.web3j.abi.datatypes.Address(_rentalAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> triggerReturnEvent(String _rentalOwner, String _rentalAddress) {
        final Function function = new Function(
                "triggerReturnEvent", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_rentalOwner), 
                new org.web3j.abi.datatypes.Address(_rentalAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RentalDirectory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RentalDirectory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static RentalDirectory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RentalDirectory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class Event_NewRentalEventResponse {
        public Log log;

        public String _rentalOwner;

        public String _address;
    }

    public static class Event_ReturnEventResponse {
        public Log log;

        public String _rentalOwner;

        public String _address;
    }
}
